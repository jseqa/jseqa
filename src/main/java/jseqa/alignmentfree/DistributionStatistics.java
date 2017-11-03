package jseqa.alignmentfree;

import java.util.stream.IntStream;

public class DistributionStatistics {
	
	/**
	 * Computes the mean of a distribution of values.
	 * @param vals
	 * @return
	 */
	public static double mean(double[] vals) {
		double n = (double)vals.length;
		return IntStream.range(0, vals.length)
						.mapToDouble(i -> vals[i] / n)
						.sum();
	}
	
	/**
	 * Computes the variance of a distribution of values.
	 * @param vals
	 * @return
	 */
	public static double var(double[] vals) {
		double mean = DistributionStatistics.mean(vals);
		double sum = IntStream.range(0,vals.length)
				 .mapToDouble(i -> {
					double diff = vals[i] - mean;
					return diff*diff;
				 })
				.sum();
		return (sum / (double)vals.length);
	}
	
	/**
	 * Comptues the standard deviation of a distribution of values.
	 * @param vals
	 * @return
	 */
	public static double std(double[] vals) {
		return Math.sqrt(DistributionStatistics.var(vals));
	}
	
	/**
	 * Pearson correlation coefficient, ranges from [-1.0,1.0]
	 * @param a
	 * @param b
	 * @return
	 */
	public static double pearsonCor(double[] a, double[] b) {
		double cor = 0.0;
		int len = a.length;
		double aMean = DistributionStatistics.mean(a);
		double bMean = DistributionStatistics.mean(b);
		double num = 0.0;
		double a_mag = 0.0;
		double b_mag = 0.0;
		for(int i = 0; i < len; i++) {
			num += ((a[i]-aMean)*(b[i]-bMean));
			a_mag += ((a[i]-aMean)*(a[i]-aMean));
			b_mag += ((b[i]-bMean)*(b[i]-aMean));
		}
		cor = (num / (Math.sqrt(a_mag)*Math.sqrt(b_mag)));
		return cor;
	}
}
