package jseqa.fastq;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
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
	public void test() throws FileNotFoundException {
		FastaReader fa = new FastaReader(Paths.get(sampleUrl.getPath()));
		int num = 0;
		for(Entry entry : fa) {
			System.out.println(entry.id() + "\t" + entry.sequence());
			assertEquals(50, entry.sequence().length());
			num++;
		}
		assertEquals(3, num);
	}

}
