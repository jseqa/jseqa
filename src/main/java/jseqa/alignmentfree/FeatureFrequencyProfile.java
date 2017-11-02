package jseqa.alignmentfree;

public class FeatureFrequencyProfile {
	private int lmerLen;
	private int numNt;
	
	private int[] ffpA;
	private int[] ffpB;
	
	public FeatureFrequencyProfile(int lLen) {
		this.lmerLen = lLen;
		this.numNt = (int) Math.pow(4.0, this.lmerLen);
		this.ffpA = new int[this.numNt];
		this.ffpB = new int[this.numNt];
	}
	
	public void compareSeqs(String seqA, String seqB) {
		this.computeFFP(seqA, this.ffpA);
		this.computeFFP(seqB, this.ffpB);
	}
	
	public double euclideanDist() {
		return DistanceMetrics.euclideanDist(this.ffpA, this.ffpB);
	}
	
	protected void computeFFP(String seq, int[] ffp) {
		for(int i = 0; i < seq.length()-this.lmerLen; i++) {
			String lmer = seq.substring(i, i+this.lmerLen);
			int lmerIdx = this.encodeKmer(lmer);
			ffp[lmerIdx]++;
		}
	}
	
	protected int encodeKmer(String kmer) {
		int val = 0;
		for(int i = 0; i < kmer.length(); i++) {
			char ch = kmer.charAt(i);
			int lclVal = 0;
			if(ch == 'A') {
				lclVal = 0;
			} else if(ch == 'C') {
				lclVal = 1;
			} else if(ch == 'G') {
				lclVal = 2;
			} else if(ch == 'T') {
				lclVal = 3;
			}
			val += lclVal;
			if(i < kmer.length()-1) {
				val = val << 2;
			}
		}
		return val;
	}
	
}
