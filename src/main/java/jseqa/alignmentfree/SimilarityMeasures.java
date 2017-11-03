package jseqa.alignmentfree;

import java.util.stream.IntStream;

public class SimilarityMeasures {
	
	/**
	 * Computes the dot product between two vectors.
	 * @param a
	 * @param b
	 * @return
	 */
	public static double dotProduct(double[] a, double[] b) {
		if(a.length != b.length) {
			throw new IllegalArgumentException("inputs must be the same length");
		}
		return IntStream.range(0, a.length)
						.mapToDouble(i -> a[i] * b[i])
						.sum();
	}
	
	/**
	 * Computes the magnitude of the input vector.
	 * @param vals
	 * @return
	 */
	public static double magnitude(double[] vals) {
		double dotprod = SimilarityMeasures.dotProduct(vals, vals);
		return Math.sqrt( dotprod );
	}
	
	/**
	 * Computes the cosine similarity measure.
	 * @param a
	 * @param b
	 * @return
	 */
	public static double cosine(double[] a, double[] b) {
		double num = SimilarityMeasures.dotProduct(a, b);
		double aMag = SimilarityMeasures.magnitude(a);
		double bMag = SimilarityMeasures.magnitude(b);
		return (num / (aMag * bMag));
	}
	
	public static double log2(double val) {
		return Math.log(val) / Math.log(2.0);
	}
	
	/**
	 * Computes Shannon entropy of discrete probability distribution {@code p}. 
	 * Assumes {@code p} is a discrete probability distributions.
	 * @param p {@code double[]} discrete probability distribution
	 * @return
	 */
	public static double entropy(double[] p) {
		return -IntStream.range(0, p.length)
				.mapToDouble(i -> p[i] * SimilarityMeasures.log2(p[i]))
				.sum();
	}
	
	/**
	 * Computes the Kullback-Leibler divergence, defined as: -SUM_i p_i * log(q_i/p_i). 
	 * Assumes {@code p} and {@code q} are discrete probability distributions.
	 * @param a
	 * @param b
	 * @return
	 */
	public static double kullbackLeibler(double[] p, double[] q) {
		return -IntStream.range(0, p.length)
				.mapToDouble(i -> p[i] * Math.log(q[i] / p[i]))
				.sum();
	}
}
