package jseqa.fastq;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import jseqa.util.Interval;

/**
 * Reader of a subset of FASTA entries, as defined by the {@link #Interval} used to construct. 
 * Reads only the entries in the interval as determined by the index of each entry.
 *
 */
public class IntervalFastaReader extends FastaReader {
    
    private Interval intval;
    
    private int currEntryIndex;

    IntervalFastaReader(File seqFile, Interval intval) throws FileNotFoundException {
        this(seqFile.toPath(), intval);
    }

    IntervalFastaReader(Path seqFile, Interval intval) throws FileNotFoundException {
        super(seqFile);
        this.intval = intval;
        this.currEntryIndex = 0;
    }
    
    public Interval interval() {
        return this.intval;
    }

    @Override
    public Entry nextEntry() throws IOException {
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
