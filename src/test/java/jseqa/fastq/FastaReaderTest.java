package jseqa.fastq;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class FastaReaderTest {

	URL sampleUrl = null;
	
	@Before
	public void init() {
		sampleUrl = ClassLoader.getSystemResource("sample.fa");
	}
	
	@Test
	public void test() throws IOException {
		try(FastaReader fa = new FastaReader(Paths.get(sampleUrl.getPath()))) {
			int num = 0;
			for(Entry entry : fa) {
				assertEquals(50, entry.sequence().length());
				num++;
			}
			assertEquals(3, num);
		}
	}
	
	@Test
	 public void testNumEntries() throws FileNotFoundException {
		assertEquals(3, FastaReader.numEntries(Paths.get(sampleUrl.getPath())));
	}
	
	@Test(expected = FileNotFoundException.class)
	public void testNumEntriesFail() throws FileNotFoundException {
		assertEquals(-1, FastaReader.numEntries(Paths.get("blahblahblah.fa")));
	}
}
