package jseqa.sequence;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NtSequenceTest {
	
	NtSequence seq1;

	String myoglobNt = "ATGGGGCTCAGCGACGGGGAATGGCAGTTGGTGCTGAACGTCTGGGGGAAGGTGGAGGCTGACATCCCAGGCCATGGGCAGGAAGTCCTCATCAGGCTCTTTAAGGGTCACCCAGAGACTCTGGAGAAGTTTGACAAGTTCAAGCACCTGAAGTCAGAGGACGAGATGAAGGCGTCTGAGGACTTAAAGAAGCATGGTGCCACCGTGCTCACCGCCCTGGGTGGCATCCTTAAGAAGAAGGGGCATCATGAGGCAGAGATTAAGCCCCTGGCACAGTCGCATGCCACCAAGCACAAGATCCCCGTGAAGTACCTGGAGTTCATCTCGGAATGCATCATCCAGGTTCTGCAGAGCAAGCATCCCGGGGACTTTGGTGCTGATGCCCAGGGGGCCATGAACAAGGCCCTGGAGCTGTTCCGGAAGGACATGGCCTCCAACTACAAGGAGCTGGGCTTCCAGGGC";
	String myoglobAa = "MGLSDGEWQLVLNVWGKVEADIPGHGQEVLIRLFKGHPETLEKFDKFKHLKSEDEMKASEDLKKHGATVLTALGGILKKKGHHEAEIKPLAQSHATKHKIPVKYLEFISECIIQVLQSKHPGDFGADAQGAMNKALELFRKDMASNYKELGFQG";

	@Before
	public void init() {
		seq1 = new NtSequence("ACGTTT");
	}

	@Test
	public void testTranslateFrame() {
		assertEquals("TF", seq1.translateFrame(0));
		
		NtSequence myoNt = new NtSequence(myoglobNt);
		assertEquals(myoglobAa, myoNt.translateFrame(0));
		
		AaSequence myoAa = myoNt.toAaSequence();
		assertEquals(myoglobAa, myoAa.seq());
	}

	@Test
	public void testThreeFrameTranslate() {
//		fail("Not yet implemented");
	}

	@Test
	public void testReverseComplement() {
		assertEquals("AAACGT", seq1.reverseComplement().seq());
	}

}
