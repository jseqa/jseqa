package jseqa.alignmentfree;

public class DistanceMetrics {

	public static double euclideanDist(double[] a, double[] b) {
		double sum = 0.0;
		int len = a.length;
		for(int i = 0; i < len; i++) {
			double diff = (a[i]-b[i]);
			sum += Math.pow(diff, 2.0);
		}
		return Math.sqrt(sum);
	}
}
