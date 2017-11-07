package jseqa.alignmentfree;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.Test;

public class DistributionStatisticsTest {

	@Test
	public void testRandPearson() {
		int numIter = 1000;
		int len = 100;
		Random rand = new Random(0);
		double[] vect = new double[numIter];
		for(int i = 0; i < numIter; i++) {
			double[] a = IntStream.range(0,len).mapToDouble(j -> rand.nextDouble()).toArray();
			double[] b = IntStream.range(0,len).mapToDouble(j -> rand.nextDouble()).toArray();
			
//			vect[i] = DistanceMetrics.cosine(a, b);
			vect[i] = DistributionStatistics.pearsonCor(a, b);
		}
		double mean = DistributionStatistics.mean( vect );
		double std = DistributionStatistics.std( vect );
		assertTrue(Math.abs(mean) < 0.0001);
	}
}
