package jseqa.alignmentfree;

import java.util.stream.IntStream;

/**
 * Collection of methods for computing statistics of a distribution. 
 *
 */
public class DistributionStatistics {

    /**
     * Computes the mean of a distribution of values.
     * @param vals {@code double[]} array of values.
     * @return double mean value of inputs.
     */
    public static double mean(double[] vals) {
        double n = (double)vals.length;
        return IntStream.range(0, vals.length)
                .mapToDouble(i -> vals[i] / n)
                .sum();
    }

    /**
     * Computes the variance of a distribution of values.
     * @param vals {@code double[]} array of values.
     * @return double variance of inputs.
     */
    public static double var(double[] vals) {
        double mean = DistributionStatistics.mean(vals);
        double sum = IntStream.range(0,vals.length)
                  .mapToDouble(i -> {
                      double diff = vals[i] - mean;
                      return diff * diff;
                  })
                  .sum();
        return (sum / (double)vals.length);
    }

    /**
     * Comptues the standard deviation of a distribution of values.
     * @param vals {@code double[]} array of values.
     * @return double standard deviation of inputs
     */
    public static double std(double[] vals) {
        return Math.sqrt(DistributionStatistics.var(vals));
    }

    /**
     * Pearson correlation coefficient, ranges from [-1.0,1.0].
     * <br><br>
     *  Computed as:
     *  <br>
     *  \(
     *  \rho(x,y) = \frac{\sum_i (x_i - \bar{x})(y_i-\bar{y})}{\sqrt{\sum_i (x_i - \bar{x})^2} \sqrt{\sum_i (y_i - \bar{y})^2} }
     *  \)
     * @param a {@code double[]} array 
     * @param b {@code double[]} array
     * @return Pearson correlation coefficient, \(\rho(a,b)\).
     */
    public static double pearsonCor(double[] a, double[] b) {
        double cor = 0.0;
        int len = a.length;
        double aMean = DistributionStatistics.mean(a);
        double bMean = DistributionStatistics.mean(b);
        double num = 0.0;
        double aMag = 0.0;
        double bMag = 0.0;
        for (int i = 0; i < len; i++) {
            num += ((a[i] - aMean) * (b[i] - bMean));
            aMag += ((a[i] - aMean) * (a[i] - aMean));
            bMag += ((b[i] - bMean) * (b[i] - aMean));
        }
        cor = (num / (Math.sqrt(aMag) * Math.sqrt(bMag)));
        return cor;
    }
}
