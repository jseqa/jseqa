package jseqa.sequence;

import static org.junit.Assert.*;

import org.junit.Test;

public class CodonTableTest {

	@Test
	public void testTranslateCodon() {
		assertEquals('S', CodonTable.translateCodon("TCA"));
		assertEquals('*', CodonTable.translateCodon("TAA"));
		assertEquals('F', CodonTable.translateCodon("TTT"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalCodons() {
		assertEquals(null, CodonTable.translateCodon("ABC"));
		assertEquals(null, CodonTable.translateCodon("ttt"));
	}

}
