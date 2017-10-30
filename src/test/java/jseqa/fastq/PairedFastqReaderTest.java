package jseqa.fastq;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import jseqa.sequence.NtSequence;

public class PairedFastqReaderTest {


	URL sample_r1 = null;
	URL sample_r2 = null;
	
	@Before
	public void init() {
		sample_r1 = ClassLoader.getSystemResource("test_pair_R1.fq");
		sample_r2 = ClassLoader.getSystemResource("test_pair_R2.fq");
	}
	
	@Test
	public void testTinySample() throws FileNotFoundException {
		PairedFastqReader fq = new PairedFastqReader(Paths.get(sample_r1.getPath()), Paths.get(sample_r2.getPath()));
		int num = 0;
		for(PairedEntry pe : fq) {
			QualityEntry e1 = pe.firstEntry();
			QualityEntry e2 = pe.secondEntry();
			NtSequence e2Seq = new NtSequence(e2.sequence());
			assertEquals(e1.sequence(), e2Seq.reverseComplement().seq());
			num++;
		}
		assertEquals(3, num);
	}
}
