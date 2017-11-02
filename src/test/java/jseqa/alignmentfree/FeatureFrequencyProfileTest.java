package jseqa.alignmentfree;

import static org.junit.Assert.*;

import org.junit.Test;

public class FeatureFrequencyProfileTest {

	FeatureFrequencyProfile ffp = new FeatureFrequencyProfile(4);
	
	@Test
	public void testComputeFFP() {
		
	}

	@Test
	public void testEncodeKmer() {
		assertEquals(0, ffp.encodeKmer("AAAA"));
		assertEquals(1, ffp.encodeKmer("AAAC"));
		assertEquals(64, ffp.encodeKmer("CAAA"));
		assertEquals(255, ffp.encodeKmer("TTTT"));
	}

}
