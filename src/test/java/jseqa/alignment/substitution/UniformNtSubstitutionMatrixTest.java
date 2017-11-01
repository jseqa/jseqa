package jseqa.alignment.substitution;

import static org.junit.Assert.*;

import org.junit.Test;

public class UniformNtSubstitutionMatrixTest {

	@Test
	public void testScore() {
		UniformNtSubstitutionMatrix unif = new UniformNtSubstitutionMatrix();
		assertEquals(1, unif.score('A', 'A'));
		assertEquals(1, unif.score('C', 'C'));
		
		assertEquals(-1, unif.score('C', 'A'));
		assertEquals(-1, unif.score('A', 'C'));
		assertEquals(-1, unif.score('C', 'G'));
		assertEquals(-1, unif.score('C', 'T'));
		assertEquals(-1, unif.score('T', 'C'));
	}

}
