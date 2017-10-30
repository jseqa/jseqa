package jseqa.fastq;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;

public class FastqReaderTest {

	URL sampleUrl = null;
	
	@Before
	public void init() {
		// sample.fq is from HOMER website
		sampleUrl = ClassLoader.getSystemResource("sample.fq");
	}
	
	@Test
	public void testSample() throws FileNotFoundException {
		FastqReader fq = new FastqReader(Paths.get(sampleUrl.getPath()), FastqQualityEncoding.ILLUMINA13);
		int num = 0;
		for(QualityEntry entry : fq) {
			assertEquals(50, entry.sequence().length());
			assertEquals(50, entry.qualityString().length());
			double[] quals = entry.qualities();
			assertEquals(50, quals.length);
			
			assertEquals(40.0, quals[1], 0.000001);
			assertEquals(40.0, quals[2], 0.000001);
			assertEquals(40.0, quals[3], 0.000001);
			num++;
		}
		assertEquals(3, num);
	}

	@Test
	 public void testNumEntries() {
		assertEquals(3, FastqReader.numEntries(Paths.get(sampleUrl.getPath())));
	}
	
}
