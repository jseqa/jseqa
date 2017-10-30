package jseqa.sequence;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AaSequenceTest {
	
	String myoglobAa = "MGLSDGEWQLVLNVWGKVEADIPGHGQEVLIRLFKGHPETLEKFDKFKHLKSEDEMKASEDLKKHGATVLTALGGILKKKGHHEAEIKPLAQSHATKHKIPVKYLEFISECIIQVLQSKHPGDFGADAQGAMNKALELFRKDMASNYKELGFQG";
	AaSequence myo = null;
	
	@Before
	public void init() {
		myo = new AaSequence(myoglobAa);
	}
	
	@Test
	public void testAaSequence() {
		
	}

	@Test
	public void testContainsStop() {
		AaSequence seq1 = new AaSequence(new NtSequence("TTTTGA").translateFrame(0).toString());
		assertTrue(seq1.containsStop());
		assertFalse(myo.containsStop());
	}

	@Test
	public void testMolecularWeight() {
//		assertEquals(17183.72499999, myo.molecularWeight(), 0.0000001);
	}

}
