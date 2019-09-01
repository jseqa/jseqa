package jseqa.fastq;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import jseqa.util.Interval;

public class IntervalFastqReaderTest {

    URL sampleUrl = null;

    @Before
    public void init() {
        // sample.fq is from HOMER website
        sampleUrl = ClassLoader.getSystemResource("sample.fq");
    }

    @Test
    public void testSample() throws FileNotFoundException {
        IntervalFastqReader fq = new IntervalFastqReader(Paths.get(sampleUrl.getPath()), 
                                                         new Interval(1,2));
        int num = 0;
        for (QualityEntry entry : fq) {
            assertEquals(50, entry.sequence().length());
            assertEquals(50, entry.qualityString().length());
            double[] quals = entry.qualities();
            assertEquals(50, quals.length);
            assertEquals("GATTTGTATGAAAGTATACAACTAAAACTGCAGGTGGATCAGAGTAAGTC", entry.sequence());
            num++;
        }
        assertEquals(1, num);
    }

    @Test(expected = FileNotFoundException.class)
    public void testReadFastaEntryError() throws IOException {
        IntervalFastqReader fq = new IntervalFastqReader(Paths.get("nonexistant"), new Interval(0, 9));
        fq = new IntervalFastqReader(Paths.get("nonexistant").toFile(), new Interval(0, 9));
    }
}
