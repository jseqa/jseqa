package jseqa.alignment.substitution;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DnaSubstitutionMatrixTest {

//	@Test
//	public void test() {
//		char a = 'A';
//		char b = 'C';
//		
//		int val = (a << 7) + b;
////		val += b;
//		
//		System.out.println(val + "\t" + Integer.toBinaryString(val));
//		
////		int v = (int) 1L;
//		
//		int convA = ( 7 & a );
//		int convC = ( 7 & b);
//		System.out.println(convA + "\t" + Integer.toBinaryString(convA));
//		System.out.println(convC + "\t" + Integer.toBinaryString(convC));
//		
//	}
	
	
	@Test
	public void testScore() {
		DnaSubstitutionMatrix dnaSub = new DnaSubstitutionMatrix(-10, -1);
		assertEquals(5, dnaSub.score('A', 'A'));
		assertEquals(5, dnaSub.score('C', 'C'));
		assertEquals(-4, dnaSub.score('T', 'A'));
		assertEquals(-4, dnaSub.score('A', 'G'));
		assertEquals(5, dnaSub.score('G', 'G'));
	}

}
