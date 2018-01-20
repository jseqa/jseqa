package jseqa.alignment;

import jseqa.alignment.substitution.SubstitutionMatrix;

/**
 * Alignment utility methods that fill the entire N x M matrix.
 *
 */
public class FullAlignments {
	
	/**
	 * Utility method that returns the score of a global alignment (Needleman-Wunch). {@see GlobalAlignment}.
	 * @param seqA {@code String} first sequence to align.
	 * @param seqB {@code String} second sequence to align.
	 * @param alignScoreMat
	 * @param alignPtrMat
	 * @param scoreMat {@code ScoringMatrix} used to score match/mismatch/indel.
	 * @return int score of global alignment.
	 */
	public static int globalAlignScore(String seqA, 
									   String seqB, 
									   AlignmentMatrixScore alignScoreMat,
									   AlignmentMatrixBacktrack alignPtrMat,
									   SubstitutionMatrix scoreMat) {
		GlobalAlignment galn = new GlobalAlignment(seqA, seqB, scoreMat);
		galn.setAlignmentMatrix(alignScoreMat, alignPtrMat);
		galn.initMatrix();
		galn.fillMatrix();
		return galn.getScore();
	}
	
	/**
	 * Utility method for aligning two sequences using global alignment (Needleman-Wunch). {@see GlobalAlignment}.
	 * @param seqA {@code String} first sequence to align.
	 * @param seqB {@code String} second sequence to align.
	 * @param scoreMat {@code ScoringMatrix} used to score match/mismatch/indel.
	 * @return {@code Alignment} of globally aligned sequences.
	 */
	public static Alignment globalAlign(String seqA, String seqB, SubstitutionMatrix scoreMat) {
		return FullAlignments.globalAlignNt(seqA, 
									   seqB, 
									   new AlignmentMatrixScore(seqA.length()+5, seqB.length()+5), 
									   new AlignmentMatrixBacktrack(seqA.length()+5, seqB.length()+5),
									   scoreMat);
	}
	
	public static Alignment globalAlignNt(String seqA, String seqB, SubstitutionMatrix scoreMat) {
		return FullAlignments.globalAlign(seqA, seqB, scoreMat);
	}
	
	/**
	 * Utility method for globally aligning (Needleman-Wunch) two sequences using existing matrices. {@see GlobalAlignment}.
	 * @param seqA {@code String} first sequence to align.
	 * @param seqB {@code String} second sequence to align.
	 * @param alignScoreMat {@code AlignmentMatrixScore} existing scoring matrix to fill.
	 * @param alignPtrMat {@code AlignmentMatrixBacktrack} existing backtracking matrix to fill.
	 * @param scoreMat {@code ScoringMatrix} used to score match/mismatch/indel.
	 * @return {@code Alignment} of globally aligned sequences.
	 */
	public static Alignment globalAlignNt(String seqA, 
							 String seqB, 
							 AlignmentMatrixScore alignScoreMat,
							 AlignmentMatrixBacktrack alignPtrMat,
							 SubstitutionMatrix scoreMat) {
		GlobalAlignment galn = new GlobalAlignment(seqA, seqB, scoreMat);
		galn.setAlignmentMatrix(alignScoreMat, alignPtrMat);
		galn.initMatrix();
		galn.fillMatrix();
		return galn.backtrack();
	}
	
	public static int localAlignScore(String seqA, 
									  String seqB, 
									  AlignmentMatrixScore alignScoreMat,
									  AlignmentMatrixBacktrack alignPtrMat,
									  SubstitutionMatrix scoreMat) {
		LocalAlignment galn = new LocalAlignment(seqA, seqB, scoreMat);
		galn.setAlignmentMatrix(alignScoreMat, alignPtrMat);
		galn.initMatrix();
		galn.fillMatrix();
		return galn.getScore();
	}
	
	/**
	 * Utility method for locally aligning (Smith-Waterman) two sequences using default matrices. 
	 * @param seqA {@code String} first sequence to align.
	 * @param seqB {@code String} second sequence to align.
	 * @param scoreMat
	 * @return {@code Alignment} of locally aligne sequences.
	 */
	public static Alignment localAlign(String seqA, String seqB, SubstitutionMatrix scoreMat) {
		return FullAlignments.localAlignNt(seqA, 
										   seqB, 
										   new AlignmentMatrixScore(seqA.length()+5, seqB.length()+5), 
										   new AlignmentMatrixBacktrack(seqA.length()+5, seqB.length()+5),
										   scoreMat);
	}
	
	public static Alignment localAlignNt(String seqA, String seqB, SubstitutionMatrix scoreMat) {
		return FullAlignments.localAlign(seqA, seqB, scoreMat);
	}
	
	/**
	 * Utility for locally aligning (Smith-Waterman) two sequences using existing matrices. {@see LocalAlignment}.
	 * @param seqA {@code String} first sequence to align.
	 * @param seqB {@code String} second sequence to align.
	 * @param alignScoreMat {@code AlignmentMatrixScore} existing scoring matrix to fill.
	 * @param alignPtrMat {@code AlignmentMatrixBacktrack} existing backtracking matrix to fill.
	 * @param scoreMat {@code ScoringMatrix} used to score match/mismatch/indel.
	 * @return {@code Alignment} of locally aligned sequences.
	 */
	public static Alignment localAlignNt(String seqA, 
										 String seqB, 
										 AlignmentMatrixScore alignScoreMat,
										 AlignmentMatrixBacktrack alignPtrMat,
										 SubstitutionMatrix scoreMat) {
		LocalAlignment galn = new LocalAlignment(seqA, seqB, scoreMat);
		galn.setAlignmentMatrix(alignScoreMat, alignPtrMat);
		galn.initMatrix();
		galn.fillMatrix();
		return galn.backtrack();
	}
	
	/**
	 * Utility for performing fitting alignment (aka, semi-global alignment), whereby one sequence 'fit'into a larger sequence.
	 * Here {@code seqA} is assumed to be the larger sequence, and {@code seqB} is assumed to be the sequence to be 'fit'.
	 * @param seqA {@code String} smaller sequence to be 'fit' into larger one.
	 * @param seqB {@code String} larger sequence.
	 * @param scoreMat  {@code ScoringMatrix} used to score match/mismatch/indel.
	 * @return {@code Alignment} of fitting alignment.
	 */
	public static Alignment fittingAlign(String seqA,
										 String seqB,
										 SubstitutionMatrix scoreMat) {
		return FullAlignments.fittingAlign(seqA, 
										   seqB,
										   new AlignmentMatrixScore(seqA.length()+5, seqB.length()+5), 
										   new AlignmentMatrixBacktrack(seqA.length()+5, seqB.length()+5),
										   scoreMat);
	}
	
	/**
	 * Utility for performing fitting alignment (aka, semi-global alignment), whereby one sequence 'fit'into a larger sequence.
	 * Here {@code seqA} is assumed to be the larger sequence, and {@code seqB} is assumed to be the sequence to be 'fit'.
	 * @param seqA {@code String} smaller sequence to be 'fit' into larger one.
	 * @param seqB {@code String} larger sequence.
	 * @param alignScoreMat {@code AlignmentMatrixScore} existing scoring matrix to fill.
	 * @param alignPtrMat {@code AlignmentMatrixBacktrack} existing backtracking matrix to fill.
	 * @param scoreMat {@code ScoringMatrix} used to score match/mismatch/indel.
	 * @return {@code Alignment} of fitting alignment.
	 */
	public static Alignment fittingAlign(String seqA,
										 String seqB,
										 AlignmentMatrixScore alignScoreMat,
										 AlignmentMatrixBacktrack alignPtrMat,
										 SubstitutionMatrix scoreMat) {
		FittingAlignment galn = new FittingAlignment(seqA, seqB, scoreMat);
		galn.setAlignmentMatrix(alignScoreMat, alignPtrMat);
		galn.initMatrix();
		galn.fillMatrix();
		return galn.backtrack();
	}
	
	/**
	 * Utility method for globally aligning two sequences with affine gap penalties.
	 * @param seqA {@code String} first sequence to align.
	 * @param seqB {@code String} second sequence to align.
	 * @param scoreMat
	 * @return
	 */
	public static Alignment affineGlobalAlign(String seqA, String seqB, SubstitutionMatrix scoreMat) {
		return FullAlignments.affineGlobalAlign(seqA, 
									   seqB, 
									   new AffineAlignmentMatrixScore(seqA.length()+5, seqB.length()+5), 
									   new AffineAlignmentMatrixBacktrack(seqA.length()+5, seqB.length()+5),
									   scoreMat);
	}
	
	/**
	 * Utility method for globally aligning two sequences using existing matrices with affine gap penalties.
	 * @param seqA {@code String} first sequence to align.
	 * @param seqB {@code String} second sequence to align.
	 * @param alignScoreMat
	 * @param alignPtrMat
	 * @param scoreMat
	 * @return
	 */
	public static Alignment affineGlobalAlign(String seqA, 
							 String seqB, 
							 AffineAlignmentMatrixScore alignScoreMat,
							 AffineAlignmentMatrixBacktrack alignPtrMat,
							 SubstitutionMatrix scoreMat) {
		AffineGlobalAlignment galn = new AffineGlobalAlignment(seqA, seqB, scoreMat);
		galn.setAlignmentMatrix(alignScoreMat, alignPtrMat);
		galn.initMatrix();
		galn.fillMatrix();
		return galn.backtrack();
	}
	
	public static Alignment affineLocalAlign(String seqA, 
			String seqB, 
			AffineAlignmentMatrixScore alignScoreMat,
			AffineAlignmentMatrixBacktrack alignPtrMat,
			SubstitutionMatrix scoreMat) {
		AffineLocalAlignment galn = new AffineLocalAlignment(seqA, seqB, scoreMat);
		galn.setAlignmentMatrix(alignScoreMat, alignPtrMat);
		galn.initMatrix();
		galn.fillMatrix();
		return galn.backtrack();
	}
	
	public static Alignment affineLocalAlign(String seqA, 
			String seqB, 
			SubstitutionMatrix scoreMat) {
		return FullAlignments.affineLocalAlign(seqA, 
				seqB, 
				new AffineAlignmentMatrixScore(seqA.length()+5, seqB.length()+5), 
				new AffineAlignmentMatrixBacktrack(seqA.length()+5, seqB.length()+5),
				scoreMat);
	}
	
	/**
	 * Utility method for globally aligning two sequences with affine gap penalties.
	 * @param seqA {@code String} first sequence to align.
	 * @param seqB {@code String} second sequence to align.
	 * @param scoreMat
	 * @return
	 */
	public static Alignment affineOverlapAlign(String seqA, String seqB, SubstitutionMatrix scoreMat) {
		return FullAlignments.affineOverlapAlign(seqA, 
									   seqB, 
									   new AffineAlignmentMatrixScore(seqA.length()+5, seqB.length()+5), 
									   new AffineAlignmentMatrixBacktrack(seqA.length()+5, seqB.length()+5),
									   scoreMat);
	}
	
	/**
	 * Utility method for globally aligning two sequences using existing matrices with affine gap penalties.
	 * @param seqA {@code String} first sequence to align.
	 * @param seqB {@code String} second sequence to align.
	 * @param alignScoreMat
	 * @param alignPtrMat
	 * @param scoreMat
	 * @return
	 */
	public static Alignment affineOverlapAlign(String seqA, 
							 String seqB, 
							 AffineAlignmentMatrixScore alignScoreMat,
							 AffineAlignmentMatrixBacktrack alignPtrMat,
							 SubstitutionMatrix scoreMat) {
		AffineOverlapAlignment galn = new AffineOverlapAlignment(seqA, seqB, scoreMat);
		galn.setAlignmentMatrix(alignScoreMat, alignPtrMat);
		galn.initMatrix();
		galn.fillMatrix();
		return galn.backtrack();
	}
	
	public static int affineGlobalAlignScore(String seqA, 
												 String seqB, 
												 AffineAlignmentMatrixScore alignScoreMat,
												 AffineAlignmentMatrixBacktrack alignPtrMat,
												 SubstitutionMatrix scoreMat) {
		AffineGlobalAlignment galn = new AffineGlobalAlignment(seqA, seqB, scoreMat);
		galn.setAlignmentMatrix(alignScoreMat, alignPtrMat);
		galn.initMatrix();
		galn.fillMatrix();
		return galn.getScore();
	}
	
//	public static int affineGlobalAlignScore(String seqA, 
//			String seqB, 
//			AffineAlignmentMatrixScore alignScoreMat,
//			AffineAlignmentMatrixBacktrack alignPtrMat,
//			SubstitutionMatrix scoreMat) {
//		AffineGlobalAlignment galn = new AffineGlobalAlignment(seqA, seqB, scoreMat);
//		galn.setAlignmentMatrix(alignScoreMat, alignPtrMat);
//		galn.initMatrix();
//		galn.fillMatrix();
//		return galn.getScore();
//	}
	
	
	public static Alignment affineGlobalAlignNt(String seqA, 
			String seqB, 
			SubstitutionMatrix scoreMat) {
		return FullAlignments.affineGlobalAlignNt(seqA, 
				seqB, 
				new AffineAlignmentMatrixScore(seqA.length()+5, seqB.length()+5), 
				new AffineAlignmentMatrixBacktrack(seqA.length()+5, seqB.length()+5),
				scoreMat);
	}
	
	public static Alignment affineGlobalAlignNt(String seqA, 
			String seqB, 
			AffineAlignmentMatrixScore alignScoreMat,
			AffineAlignmentMatrixBacktrack alignPtrMat,
			SubstitutionMatrix scoreMat) {
		AffineGlobalAlignment galn = new AffineGlobalAlignment(seqA, seqB, scoreMat);
		galn.setAlignmentMatrix(alignScoreMat, alignPtrMat);
		galn.initMatrix();
		galn.fillMatrix();
		return galn.backtrack();
	}
	
	/**
	 * Affine local alignment 
	 * @param seqA
	 * @param seqB
	 * @param scoreMat
	 * @return
	 */
	public static Alignment affineLocalAlignNt(String seqA, 
			String seqB, 
			SubstitutionMatrix scoreMat) {
		return FullAlignments.affineLocalAlignNt(seqA, 
				seqB, 
				new AffineAlignmentMatrixScore(seqA.length()+5, seqB.length()+5), 
				new AffineAlignmentMatrixBacktrack(seqA.length()+5, seqB.length()+5),
				scoreMat);
	}
	
	public static Alignment affineLocalAlignNt(String seqA, 
			String seqB, 
			AffineAlignmentMatrixScore alignScoreMat,
			AffineAlignmentMatrixBacktrack alignPtrMat,
			SubstitutionMatrix scoreMat) {
		AffineLocalAlignment galn = new AffineLocalAlignment(seqA, seqB, scoreMat);
		galn.setAlignmentMatrix(alignScoreMat, alignPtrMat);
		galn.initMatrix();
		galn.fillMatrix();
		return galn.backtrack();
	}
	
	private FullAlignments() {}
}
