package jseqa.alignmentfree;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.junit.Test;

public class FeatureFrequencyProfileTest {

	FeatureFrequencyProfile ffp = new FeatureFrequencyProfile(4);
	
	@Test
	public void testComputeFFP() {
		FeatureFrequencyProfile ffp = new FeatureFrequencyProfile(2);
		ffp.compareSeqs("AAAAAAAA", "AAAAAAAA");
		System.out.println(Arrays.toString(ffp.getFfpA()) + "\n" + Arrays.toString(ffp.getFfpB()));
		assertEquals(0.0, ffp.euclideanDist(), 0.00000001);
	}

	@Test
	public void testEncodeKmer() {
		assertEquals(0, ffp.encodeKmer("AAAA"));
		assertEquals(1, ffp.encodeKmer("AAAC"));
		assertEquals(64, ffp.encodeKmer("CAAA"));
		assertEquals(255, ffp.encodeKmer("TTTT"));
	}
	
}
