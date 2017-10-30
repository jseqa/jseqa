package jseqa.fastq;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
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
		IntervalFastqReader fq = new IntervalFastqReader(Paths.get(sampleUrl.getPath()), new Interval(1,2));
		int num = 0;
		for(QualityEntry entry : fq) {
			assertEquals(50, entry.sequence().length());
			assertEquals(50, entry.qualityString().length());
			double[] quals = entry.qualities();
			assertEquals(50, quals.length);
			assertEquals("GATTTGTATGAAAGTATACAACTAAAACTGCAGGTGGATCAGAGTAAGTC", entry.sequence());
			num++;
		}
		assertEquals(1, num);
	}

}
