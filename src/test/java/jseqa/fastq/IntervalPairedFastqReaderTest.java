package jseqa.fastq;

import jseqa.util.Interval;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class IntervalPairedFastqReaderTest {

    URL sampleUrl1 = null;
    URL sampleUrl2 = null;

    @Before
    public void init() {
        // sample.fq is from HOMER website
        sampleUrl1 = ClassLoader.getSystemResource("sample.fq");
        sampleUrl2 = ClassLoader.getSystemResource("sample.fq");
    }

    @Test
    public void testSasmple() throws FileNotFoundException {
        IntervalPairedFastqReader fq = new IntervalPairedFastqReader(Paths.get(sampleUrl1.getPath()),
                Paths.get(sampleUrl2.getPath()),
                new Interval(1,2));
        int num = 0;
        for (PairedEntry entry : fq) {
            assertEquals(50, entry.firstEntry().sequence().length());
            assertEquals(50, entry.firstEntry().qualityString().length());
            double[] quals = entry.firstEntry().qualities();
            assertEquals(50, quals.length);
            assertEquals("GATTTGTATGAAAGTATACAACTAAAACTGCAGGTGGATCAGAGTAAGTC", entry.firstEntry().sequence());
            assertEquals("GATTTGTATGAAAGTATACAACTAAAACTGCAGGTGGATCAGAGTAAGTC", entry.secondEntry().sequence());
            num++;
        }
        assertEquals(1, num);
    }

}
