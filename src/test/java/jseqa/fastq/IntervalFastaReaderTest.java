package jseqa.fastq;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import jseqa.util.Interval;

public class IntervalFastaReaderTest {

    URL sampleUrl = null;

    @Before
    public void init() {
        sampleUrl = ClassLoader.getSystemResource("sample.fa");
    }

    @Test
    public void test() throws FileNotFoundException {
        IntervalFastaReader fa = new IntervalFastaReader(Paths.get(sampleUrl.getPath()), 
                                                         new Interval(1,2));
        int num = 0;
        for (Entry entry : fa) {
            assertEquals(50, entry.sequence().length());
            assertEquals("GATTTGTATGAAAGTATACAACTAAAACTGCAGGTGGATCAGAGTAAGTC", entry.sequence());
            num++;
        }
        assertEquals(1, num);
    }

}
