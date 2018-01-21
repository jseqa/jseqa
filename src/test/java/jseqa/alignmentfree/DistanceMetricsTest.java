package jseqa.alignmentfree;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.Test;

public class DistanceMetricsTest {

	private double[] a = {1.0, 2.0};
	private double[] b = {0.0, 2.0};
	
	private static double DELTA = 0.0000001;
	
	@Test
	public void testPNormDist() {
		assertEquals(DistanceMetrics.euclideanDist(a, b), DistanceMetrics.lpNormDist(a, b, 2),  DELTA);
		
		assertEquals(0.0, DistanceMetrics.lpNormDist(a, a, 2),  DELTA);
		assertEquals(0.0, DistanceMetrics.lpNormDist(b, b, 2),  DELTA);
	}
	
	@Test
	public void testEuclideanDist() {
		double[] lclA = {2.0, -1.0};
		double[] lclB = {-2.0, 2.0};
		assertEquals(5.0, DistanceMetrics.euclideanDist(lclA, lclB), DELTA);
	}

	@Test
	public void testPearsonDist() {
		assertEquals(0.0, DistanceMetrics.pearsonDistance(a, a), DELTA);
		assertEquals(0.0, DistanceMetrics.pearsonDistance(b, b), DELTA);
	}
	
	@Test
	public void testCosineDist() {
		assertEquals(0.0, DistanceMetrics.cosineDistance(a, a), DELTA);
		assertEquals(0.0, DistanceMetrics.cosineDistance(b, b), DELTA);
	}
	
	@Test
	public void testManhattanDist() {
		assertEquals(1.0, DistanceMetrics.manhattanDist(a, b), DELTA);
		double[] c = {0.0, 0.0};
		assertEquals(2.0, DistanceMetrics.manhattanDist(c, b), DELTA);
		assertEquals(3.0, DistanceMetrics.manhattanDist(c, a), DELTA);
	}
	
	@Test
	public void testChebyshevDist() {
		assertEquals(1.0, DistanceMetrics.chebyshevDist(a, b), DELTA);
		double[] c = {0.0, 0.0};
		assertEquals(2.0, DistanceMetrics.chebyshevDist(c, b), DELTA);
		assertEquals(2.0, DistanceMetrics.chebyshevDist(c, a), DELTA);
	}
	
}
