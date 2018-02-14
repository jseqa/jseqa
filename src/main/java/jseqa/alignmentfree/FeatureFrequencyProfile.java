package jseqa.alignmentfree;

import java.util.stream.IntStream;

/**
 * Feature frequency profile (FFP) for comparing sequences efficiently; a fast, 
 * alignment-free approach to sequence comparison. Creates a frequency profile 
 * of l-mers for two sequences, then compares the frequency vectors using different
 * similarity/distance measures.
 *
 */
public class FeatureFrequencyProfile {
    private int lmerLen;
    private int numNt;

    private int[] fcpA;
    private int[] fcpB;

    private double[] ffpA;
    private double[] ffpB;

    /**
     * Constructor for the feature frequency profile using an l-mer of the given length.
     * @param lLen int length of l-mer to use.
     */
    public FeatureFrequencyProfile(int lLen) {
        this.lmerLen = lLen;
        this.numNt = (int) Math.pow(4.0, this.lmerLen);
        this.ffpA = new double[this.numNt];
        this.ffpB = new double[this.numNt];
        //
        this.fcpA = new int[this.numNt];
        this.fcpB = new int[this.numNt];
    }

    protected double[] getFfpA() {
        return this.ffpA;
    }

    protected double[] getFfpB() {
        return this.ffpB;
    }

    /**
     * Compare two sequences using FFP.
     * @param seqA {@code String} sequence A to compare.
     * @param seqB {@code String} sequence B to compare.
     */
    public void compareSeqs(String seqA, String seqB) {
        for (int i = 0; i < this.numNt; i++) {
            this.fcpA[i] = 0;
            this.fcpB[i] = 0;
        }
        this.computeFCP(seqA, this.fcpA);
        this.computeFCP(seqB, this.fcpB);
        this.computeFFP(this.fcpA, this.ffpA);
        this.computeFFP(this.fcpB, this.ffpB);
    }

    /**
     * Computes Euclidean distance of the FFPs.
     * @return
     */
    public double euclideanDist() {
        return DistanceMetrics.euclideanDist(this.ffpA, this.ffpB);
    }

    /**
     * Computes cosine similarity of the FFPs.
     * @return
     */
    public double cosineSimilarity() {
        return SimilarityMeasures.cosine(this.ffpA, this.ffpB);
    }

    /**
     * Computes Pearson correlation of the FFPs.
     * @return
     */
    public double pearsonCor() {
        return DistributionStatistics.pearsonCor(this.ffpA, this.ffpB);
    }

    protected void computeFFP(int[] fcp, double[] ffp) {
        double sum = IntStream.range(0, fcp.length).mapToDouble(i -> (double)fcp[i]).sum();
        IntStream.range(0, fcp.length).forEach(i -> ffp[i] = (double)fcp[i] / sum);
    }

    protected void computeFCP(String seq, int[] fcp) {
        for (int i = 0; i < (seq.length() - this.lmerLen); i++) {
            String lmer = seq.substring(i, i + this.lmerLen);
            int lmerIdx = this.encodeKmer(lmer);
            fcp[lmerIdx]++;
        }
    }

    /**
     * Encodes a k-mer {@code String} into an int.
     * @param kmer {@code String} of length k to encode as an int.
     * @return int representing the k-mer.
     */
    protected int encodeKmer(String kmer) {
        int val = 0;
        for (int i = 0; i < kmer.length(); i++) {
            char ch = kmer.charAt(i);
            int lclVal = 0;
            if (ch == 'A') {
                lclVal = 0;
            } else if (ch == 'C') {
                lclVal = 1;
            } else if (ch == 'G') {
                lclVal = 2;
            } else if (ch == 'T') {
                lclVal = 3;
            }
            val += lclVal;
            if (i < kmer.length() - 1) {
                val = val << 2;
            }
        }
        return val;
    }

}
