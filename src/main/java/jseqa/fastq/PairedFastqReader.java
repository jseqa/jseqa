package jseqa.fastq;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * Reads two paired FASTQ files together in synchrony. Assumes the same number of entries in each,
 * i.e., orphaned reads must be removed prior to use.
 *
 */
public class PairedFastqReader implements AutoCloseable, Iterable<PairedEntry> {

    private FastqReader r1Reader;

    private FastqReader r2Reader;

    public PairedFastqReader(Path r1fq, Path r2fq) throws FileNotFoundException {
        this.r1Reader = new FastqReader(r1fq);
        this.r2Reader = new FastqReader(r2fq);
    }

    /**
     * Gets the next pair of entries, one {@code QualityEntry} from each file.
     * @return  {@code PairedEntry} of the entry pair.
     * @throws IOException
     */
    public PairedEntry nextEntry() throws IOException {
        QualityEntry e1 = this.r1Reader.nextEntry();
        QualityEntry e2 = this.r2Reader.nextEntry();
        if (e1 == null || e2 == null) {
            return null;
        }
        return new PairedEntry(e1, e2);
    }

    @Override
    public void close() throws IOException {
        this.r1Reader.close();
        this.r2Reader.close();
    }

    @Override
    public Iterator<PairedEntry> iterator() {
        return new PairedFastqIterator();
    }

    private class PairedFastqIterator implements Iterator<PairedEntry> {
        private PairedEntry currEntry;
        private PairedEntry nextEntry;

        PairedFastqIterator() {
            this.currEntry = null;
            this.nextEntry = null;
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
        public PairedEntry next() {
            this.currEntry = this.nextEntry;
            this.nextEntry = null;
            return this.currEntry;
        }
    }

}
