package jseqa.msa;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import jseqa.alignment.Alignment;
import jseqa.alignment.FullAlignments;
import jseqa.alignment.substitution.CustomDnaSubstitutionMatrix;
import jseqa.alignment.substitution.SubstitutionMatrix;
import jseqa.alignmentfree.FeatureFrequencyProfile;

/**
 * Perform multiple alignment by:
 * <ol>
 * 	<li>Find best candidate sequence to use as centroid by fast, alignment-free, comparisons of all pairs</li>
 * 	<li>Perform n-1 pairwise alignnments to centroid (i.e., guide sequence).</li>
 * 	<li>Iteratively add sequences from their pairwise alignments adjusting using the guide sequence.</li>
 * </ol>
 *
 */
public class StarMultipleAlignment {
	
	private static final int DEFAULT_LMER_LEN = 4;
	
	protected List<String> seqs;
	
	protected String guideSeq;
	
	private int lmerLen = 4;
	
	private FeatureFrequencyProfile ffp;
		
	protected SubstitutionMatrix subMat;
	
	public StarMultipleAlignment(SubstitutionMatrix submat) {
		this.subMat = submat;
	}
	
	public StarMultipleAlignment(List<String> rawSeqs) {
		this(rawSeqs, DEFAULT_LMER_LEN, new CustomDnaSubstitutionMatrix(5, -4, -10, -1));
	}
	
	public StarMultipleAlignment(List<String> rawSeqs, int lmerlen) {
		this(rawSeqs, lmerlen, new CustomDnaSubstitutionMatrix(5, -4, -10, -1));
	}
	/**
	 * Constructor for the star MSA. Note, the minimum sequence length MUST be larger than the l-mer length used for FFP.
	 * @param rawSeqs {@code List<String>} sequences to align
	 * @param lmerLen int l-mer length to use for FFP computation.
	 * @param submat substitution matrix to use for alignment of sequences
	 */
	public StarMultipleAlignment(List<String> rawSeqs, int lmerlen, SubstitutionMatrix submat) {
		this(submat);
		this.lmerLen = lmerlen;
		this.ffp = new FeatureFrequencyProfile(this.lmerLen);
		this.guideSeq = this.centroidSeq(rawSeqs);
		this.seqs = new ArrayList<String>(rawSeqs.size());
		this.seqs.addAll(rawSeqs);
		this.seqs.remove(this.guideSeq);
	}
	
	/**
	 * Find centroid sequence, i.e., the one that minimizes that FFP distance to all other sequences.
	 * @param seqs {@code List<String>} of sequences to find centroid.
	 * @return 
	 */
	String centroidSeq(List<String> seqs) {
		List<Double> vals = new ArrayList<Double>(seqs.size());
		for(int i = 0; i < seqs.size(); i++) {
			String currSeq = seqs.get(i);
			List<Double> dists = 
					seqs.stream()
						.map(s -> {
							this.ffp.compareSeqs(currSeq, s);
							return this.ffp.euclideanDist();
						})
						.collect(Collectors.toList());
			double mean = dists.stream().mapToDouble(v -> v).average().getAsDouble();
			vals.add(i, mean);
		}
		double minDist = vals.stream().mapToDouble(v -> v).min().getAsDouble();
		int minIdx = vals.indexOf(minDist);
		return seqs.get(minIdx);
	}
	
	/**
	 * Build the MSA using the star alignment approach.
	 * @return {@code MultipleAlignment} of all reads
	 */
	public MultipleAlignment buidMsa() {
		return this.buidMsa(aln -> false);
	}
	
	/**
	 * Buld the MSA using the star alignment approach. Any {@code Alignment} that the predicate returns as true, is 
	 * not added to the MSA.
	 * @param apred {@code Predicate} to filter out {@code Alignment} by any criteria.
	 * @return {@code MultipleAlignment}.
	 */
	public MultipleAlignment buidMsa(Predicate<Alignment> apred) {
//		Alignment a1 = FullAlignments.affineGlobalAlign(this.guideSeq, this.seqs.get(0), this.subMat);
		Alignment a1 = FullAlignments.affineOverlapAlign(this.guideSeq, this.seqs.get(0), this.subMat);
		SingleGuideMultipleAlignmentBuilder smsa = new SingleGuideMultipleAlignmentBuilder(a1);
		for(int i = 1; i < this.seqs.size(); i++) {
//			Alignment aln = FullAlignments.affineGlobalAlign(this.guideSeq, this.seqs.get(i), this.subMat);
			Alignment aln = FullAlignments.affineOverlapAlign(this.guideSeq, this.seqs.get(i), this.subMat);
			if(!apred.test(aln)) {
				continue;
			}
			smsa.add(aln);
		}
		return smsa.toMultipleAlignment();
	}
}
