package jseqa.fastq;

import jseqa.util.Interval;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Reads only those entries in the interval of entries provided, as determined by their index.
 *
 */
public class IntervalPairedFastqReader extends PairedFastqReader {

    private Interval intval;

    private int currEntryIndex;

    IntervalPairedFastqReader(Path seq1File, Path seq2File, Interval intval)
            throws FileNotFoundException {
        super(seq1File, seq2File);
        this.intval = intval;
        this.currEntryIndex = 0;
    }

    public Interval interval() {
        return this.intval;
    }

    @Override
    public PairedEntry nextEntry() throws IOException {
        if (this.intval.contains(this.currEntryIndex)) {
            this.currEntryIndex++;
            return super.nextEntry();
        } else if (this.currEntryIndex < this.intval.start()) {
            while (!this.intval.contains(this.currEntryIndex)) {
                super.nextEntry();
                this.currEntryIndex++;
            }
            return this.nextEntry();
        } else {
            return null;
        }
    }

}
