package jseqa.fastq;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Reader of FASTQ files, iterates over entries, handles reading in gzipped FASTQ files.
 * <br>
 * Once created, can use for-each syntax to iterate over entries:
 * <br>
 * <pre>{@code
 *  for(Entry ent : fqReader) {
 *      ...
 *  }
 * }</pre>
 *
 */
public class FastqReader extends AbstractSequenceReader implements Iterable<QualityEntry> {

    private static final Logger log = LogManager.getLogger(FastqReader.class);

    /** Decoder to use for converting from quality string to integers. */
    protected FastqQualityEncoding encoding;

    public FastqReader(File seqFile) throws FileNotFoundException {
        this(seqFile.toPath(), QualityEntry.DEFAULT_ENCODING);
    }

    public FastqReader(Path seqFile) throws FileNotFoundException {
        this(seqFile, QualityEntry.DEFAULT_ENCODING);
    }

    public FastqReader(File seqFile, FastqQualityEncoding enc) throws FileNotFoundException {
        this(seqFile.toPath(), enc);
    }

    public FastqReader(Path seqFile, FastqQualityEncoding enc) throws FileNotFoundException {
        super(seqFile);
        this.encoding = enc;
    }

    /**
     * Counts the number of entries in FASTQ file.
     * @param fqFile {@code Path} FASTQ file to count entries
     * @return number of identified entries
     * @throws FileNotFoundException thrown when {@code fqFile} is not found.
     */
    public static int numEntries(Path fqFile) throws FileNotFoundException {
        try (FastqReader fq = new FastqReader(fqFile)) {
            int numEntries = 0;
            for (QualityEntry entry : fq) {
                numEntries++;
            }
            return numEntries;
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Reads next entry from the FASTQ file, makes assumption that entry is always 4 lines. 
     * This holds true for Illumina but is not strictly correct from the format specification.
     * @return {@link QualityEntry} for next entry in the FASTQ file.
     * @throws IOException 
     */
    public QualityEntry nextEntry() throws IOException, IllegalArgumentException {
        String header = this.buff.readLine();
        String seq = this.buff.readLine();
        String plus = this.buff.readLine();
        String quals = this.buff.readLine();
        if (header == null
            || seq == null
            || quals == null) {
            return null;
        }

        if (seq.length() != quals.length()) {
            log.error("Sequence length != qual length:\t" + seq.length() + " != " + quals.length());
            throw new IllegalArgumentException(); 
        } else if (header.charAt(0) != '@') {
            log.error("Entry id does not start with @:\t" + header);
            throw new IllegalArgumentException();
        }
        return new QualityEntry(header.substring(1), seq, quals, this.encoding);
    }

    @Override
    public Iterator<QualityEntry> iterator() {
        return new FastqIterator();
    }

    private class FastqIterator implements Iterator<QualityEntry> {
        private QualityEntry currEntry;
        private QualityEntry nextEntry;

        FastqIterator() {
            this.currEntry = null;
            this.nextEntry = null;
            try {
                open();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                log.error("Iterator unable to open");
                System.exit(1);
            }
        }

        @Override
        public boolean hasNext() {
            try {
                this.nextEntry = nextEntry();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return this.nextEntry != null;
        }

        @Override
        public QualityEntry next() {
            this.currEntry = this.nextEntry;
            this.nextEntry = null;
            return this.currEntry;
        }

    }
}
