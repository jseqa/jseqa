package jseqa.fastq;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import jseqa.util.Interval;

/**
 * Reads only those entries in the interval of entries provided, as determined by their index.
 *
 */
public class IntervalFastqReader extends FastqReader {

    private Interval intval;
    
    private int currEntryIndex;

    IntervalFastqReader(File seqFile, Interval intval) throws FileNotFoundException {
        this(seqFile, QualityEntry.DEFAULT_ENCODING, intval);
    }

    IntervalFastqReader(Path seqFile, Interval intval) throws FileNotFoundException {
        this(seqFile, QualityEntry.DEFAULT_ENCODING, intval);
    }

    IntervalFastqReader(File seqFile, FastqQualityEncoding enc, Interval intval) 
            throws FileNotFoundException {
        this(seqFile.toPath(), enc, intval);
    }

    IntervalFastqReader(Path seqFile, FastqQualityEncoding enc, Interval intval) 
            throws FileNotFoundException {
        super(seqFile, enc);
        this.intval = intval;
        this.currEntryIndex = 0;
    }

    @Override
    public QualityEntry nextEntry() throws IOException {
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
