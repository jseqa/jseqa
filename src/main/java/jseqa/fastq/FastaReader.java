package jseqa.fastq;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Reader of FASTA files, iterates over entries.
 * <br>
 * Once created, can use for-each syntax to iterate over entries:
 * <br>
 * <pre>{@code
 *  for(Entry ent : fqReader) {
 *      ...
 *  }
 * }
 * </pre
 *
 */
public class FastaReader  extends AbstractSequenceReader implements Iterable<Entry> {

    private static final Logger log = LogManager.getLogger(FastaReader.class);

    private String nextId;

    private static final Pattern ENTRY_ID_RE = Pattern.compile("^>(.+)");

    private static final Pattern ENTRY_ID_XTRACT = Pattern.compile("^>([^\\s\\/\\#]+)");

    /**
     * Constructor opening FASTA file for reading.
     * @param seqFile file to read.
     * @throws FileNotFoundException
     */
    public FastaReader(File seqFile) throws FileNotFoundException {
        this(seqFile.toPath());
    }

    /**
     * Constructor opening FASTA file for reading.
     * @param seqFile file to read.
     * @throws FileNotFoundException
     */
    public FastaReader(Path seqFile) throws FileNotFoundException {
        super(seqFile);
        this.nextId = null;
    }

    /**
     * Counts the number of entries in FASTQ file.
     * @param fqFile {@code Path} FASTQ file to count entries
     * @return number of identified entries
     * @throws FileNotFoundException 
     */
    public static int numEntries(Path fqFile) throws FileNotFoundException {
        try (FastaReader fq = new FastaReader(fqFile)) {
            int numEntries = 0;
            for (Entry entry : fq) {
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
     * Reads next entry from the FASTA file.
     * @return {@code Entry} next entry from file.
     * @throws IOException 
     */
    public Entry nextEntry() throws IOException {
        String line = null;
        String id = this.nextId;
        String seq = "";
        while ((line = this.buff.readLine()) != null) {
            if (line.matches(ENTRY_ID_RE.pattern())) {
                if (id == null) {
                    id = this.extractId(line);
                } else {
                    Entry currEntry = new Entry(id, seq);
                    this.nextId = this.extractId(line);
                    return currEntry;
                }
            } else {
                seq += line;
            }
        }
        if (!seq.isEmpty()) {
            return this.nextId == null ? new Entry(id, seq) : new Entry(this.nextId, seq);
        } else {
            return null;
        }
    }

    private String extractId(String line) {
        Matcher match = ENTRY_ID_XTRACT.matcher(line);
        if (match.find()) {
            return match.group(1);
        } else {
            return null;
        }
    }

    @Override
    public Iterator<Entry> iterator() {
        return new FastaIterator();
    }

    private class FastaIterator implements Iterator<Entry> {
        private Entry currEntry;
        private Entry nextEntry;

        FastaIterator() {
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
        public Entry next() {
            this.currEntry = this.nextEntry;
            this.nextEntry = null;
            return this.currEntry;
        }

    }
}
