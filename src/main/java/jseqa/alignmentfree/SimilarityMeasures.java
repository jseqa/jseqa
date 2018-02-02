package jseqa.alignmentfree;

import java.util.stream.IntStream;

/**
 * Collection of methods for computing a variety of similarity scores.
 *
 */
public class SimilarityMeasures {

    /**
     * Computes the dot product between two vectors.
     * @param a {@code double[]} vector of values.
     * @param b {@code double[]} vector of values.
     * @return dot product of {@code a} and {@code b}.
     */
    public static double dotProduct(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("inputs must be the same length");
        }
        return IntStream.range(0, a.length)
                .mapToDouble(i -> a[i] * b[i])
                .sum();
    }

    /**
     * Computes the magnitude of the input vector.
     * @param vals {@code double[]} vector of values.
     * @return magnitude of the input vector.
     */
    public static double magnitude(double[] vals) {
        double dotprod = SimilarityMeasures.dotProduct(vals, vals);
        return Math.sqrt(dotprod);
    }

    /**
     * Computes the cosine similarity measure.
     * @param a {@code double[]} vector of values.
     * @param b {@code double[]} vector of values.
     * @return
     */
    public static double cosine(double[] a, double[] b) {
        double num = SimilarityMeasures.dotProduct(a, b);
        double aMag = SimilarityMeasures.magnitude(a);
        double bMag = SimilarityMeasures.magnitude(b);
        return (num / (aMag * bMag));
    }

    /**
     * Log base 2.
     * @param val double value.
     * @return log2 of input.
     */
    public static double log2(double val) {
        return Math.log(val) / Math.log(2.0);
    }

    /**
     * Computes <a href="https://en.wikipedia.org/wiki/Entropy_(information_theory)">
     * Shannon entropy</a> of discrete probability distribution {@code p}.
     * Assumes {@code p} is a discrete probability distributions.
     * @param p {@code double[]} discrete probability distribution.
     * @return entropy of input array.
     */
    public static double entropy(double[] p) {
        return -IntStream.range(0, p.length)
                .mapToDouble(i -> p[i] * SimilarityMeasures.log2(p[i]))
                .sum();
    }

    /**
     * Computes the <a href="https://en.wikipedia.org/wiki/Kullback%E2%80%93Leibler_divergence">
     * Kullback-Leibler divergence</a>, defined as: -SUM_i p_i * log(q_i/p_i). 
     * Assumes {@code p} and {@code q} are discrete probability distributions.
     * @param p {@code double[]} vector of values.
     * @param q {@code double[]} vector of values.
     * @return KL divergence.
     */
    public static double kullbackLeibler(double[] p, double[] q) {
        return -IntStream.range(0, p.length)
                .mapToDouble(i -> p[i] * SimilarityMeasures.log2(q[i] / p[i]))
                .sum();
    }
}
