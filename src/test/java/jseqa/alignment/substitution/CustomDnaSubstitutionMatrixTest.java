package jseqa.alignment.substitution;

import static org.junit.Assert.*;

import org.junit.Test;

public class CustomDnaSubstitutionMatrixTest {

	private char[] nucs = {'A', 'C', 'G', 'T'};
	
	@Test
	public void test() {
		CustomDnaSubstitutionMatrix cmat = new CustomDnaSubstitutionMatrix(1,-10, 0, 0);
		for(int i = 0; i < nucs.length; i++) {
			assertEquals(1, cmat.score(nucs[i], nucs[i]));
		}
		for(int i = 0; i < nucs.length; i++) {
			for(int j = 0; j < nucs.length; j++) {
				if(i != j) {
					assertEquals(-10, cmat.score(nucs[i], nucs[j]));
				}
			}
		}
	}

}
