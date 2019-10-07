package jseqa.sequence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
	    NtSequence myoNt = new NtSequence(myoglobNt);
	    String[] threeFrames = myoNt.threeFrameTranslate();
	    assertEquals(myoglobAa, threeFrames[0]);
	    assertFalse(threeFrames[1].equals(myoglobAa));
	    assertFalse(threeFrames[2].equals(myoglobAa));
	    assertEquals("WGSATGNGSWC*TSGGRWRLTSQAMGRKSSSGSLRVTQRLWRSLTSSST*SQRTR*RRLRT*RSMVPPCSPPWVASLRRRGIMRQRLSPWHSRMPPSTRSP*STWSSSRNASSRFCRASIPGTLVLMPRGP*TRPWSCSGRTWPPTTRSWASR", threeFrames[1]);
	    assertEquals("GAQRRGMAVGAERLGEGGG*HPRPWAGSPHQAL*GSPRDSGEV*QVQAPEVRGRDEGV*GLKEAWCHRAHRPGWHP*EEGAS*GRD*APGTVACHQAQDPREVPGVHLGMHHPGSAEQASRGLWC*CPGGHEQGPGAVPEGHGLQLQGAGLPG", threeFrames[2]);
	}

	@Test
	public void testReverseComplement() {
		assertEquals("AAACGT", seq1.reverseComplement().seq());
	}

	@Test
	public void testMeltingTemperature() {
	    assertEquals(18.0, new NtSequence("TGCTCA").meltingTemp(), 0.00000001);
	    assertEquals(24.0, new NtSequence("AAACGTTAG").meltingTemp(), 0.00000001);
	    assertEquals(74.0, Math.round(new NtSequence("CAGTGAGCCCATACTTGCTCTTTTTGTCTTCTTCAGACTGCGCCATGGGGCTC").meltingTemp()), 0.00001);
	}
}
