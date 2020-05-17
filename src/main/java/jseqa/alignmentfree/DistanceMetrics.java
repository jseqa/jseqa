package jseqa.alignmentfree;

import java.util.stream.IntStream;

/**
 * A collection of utility methods of different distances.
 *
 */
public class DistanceMetrics {

    /**
     * Computes the L<sub>p</sub>-norm distance between {@code a} and {@code b};
     * also known as <a href="https://en.wikipedia.org/wiki/Minkowski_distance">
     * Minkowski distance</a>.
     * <br>
     *  Computed as: \( (\sum_i |a_i - b_i|^p)^\frac{1}{p} \)
     * @param a {@code double[]} array.
     * @param b {@code double[]} array.
     * @param p order of Minkowski distance to compute.
     * @return L<sub>p</sub>-distance between a and b.
     */
    public static double lpNormDist(double[] a, double[] b, int p) {
        double lp = (double)p;
        double sum = IntStream.range(0, a.length)
                .mapToDouble(i -> Math.pow(Math.abs(a[i] - b[i]), lp))
                .sum();
        return Math.pow(sum, 1.0 / lp);
    }

    /**
     * Computes the L<sub>&infin;</sub>-norm distance between {@code a} and {@code b};
     * also known as <a href="https://en.wikipedia.org/wiki/Chebyshev_distance">
     * Chebyshev distance</a>.
     * @param a {@code double[]} array.
     * @param b {@code double[]} array.
     * @return L<sub>&infin;</sub>-distance between a and b.
     */
    public static double linfNormDist(double[] a, double[] b) {
        return IntStream.range(0, a.length)
                .mapToDouble(i -> Math.abs(a[i] - b[i]))
                .max()
                .getAsDouble();
    }

    /**
     * Computes Chebyshev distance, same as {@link #linfNormDist(double[], double[])}.
     * @param a {@code double[]} array.
     * @param b {@code double[]} array.
     * @return Chebyshev distance between a and b.
     */
    public static double chebyshevDist(double[] a, double[] b) {
        return DistanceMetrics.linfNormDist(a, b);
    }

    /**
     * Computes the Manhattan distance (i.e., L1-distance).
     * <br>
     *  Computed as:  \( (\sum_i |a_i - b_i|) \)
     * @param a {@code double[]} array.
     * @param b {@code double[]} array.
     * @return Manhattan distance between a and b.
     */
    public static double manhattanDist(double[] a, double[] b) {
        return DistanceMetrics.lpNormDist(a, b, 1);
    }

    /**
     * Computes the Euclidean distance between {@code a} and {@code b} (i.e., L2-distance).
     * <br>
     *  Computed as:  \( (\sum_i |a_i - b_i|^2)^\frac{1}{2} \)
     * @param a {@code double[]} array.
     * @param b {@code double[]} array.
     * @return Euclidean distance between a and b.
     */
    public static double euclideanDist(double[] a, double[] b) {
        double sum = 0.0;
        int len = a.length;
        for (int i = 0; i < len; i++) {
            double diff = (a[i] - b[i]);
            sum += Math.pow(diff, 2.0);
        }
        return Math.sqrt(sum);
    }

    /**
     * Pearson correlation distance, defined as: 1.0 - pearson(a,b).
     * See {@link DistributionStatistics#pearsonCor(double[], double[])}.
     * @param a {@code double[]} array.
     * @param b {@code double[]} array.
     * @return Pearson correlation distance between a and b.
     */
    public static double pearsonDistance(double[] a, double[] b) {
        return 1.0 - DistributionStatistics.pearsonCor(a, b);
    }

    /**
     * The cosine distance, defined as: 1.0 - cos(a,b). See {@link SimilarityMeasures#cosine(double[], double[])}.
     * @param a {@code double[]} array.
     * @param b {@code double[]} array.
     * @return Cosine distance beteween a and b.
     */
    public static double cosineDistance(double[] a, double[] b) {
        return 1.0 - SimilarityMeasures.cosine(a, b);
    }

}
