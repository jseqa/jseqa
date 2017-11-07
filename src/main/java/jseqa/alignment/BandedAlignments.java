package jseqa.alignment;

import jseqa.alignment.substitution.SubstitutionMatrix;

/**
 * Contains banded alignment utility methods. See {@see BandedMatrixFiller} for an explanation.
 *
 */
public class BandedAlignments {
	
	public static Alignment globalBasic(String seqA, 
			String seqB, 
			SubstitutionMatrix scoreMat,
			int bandwidth) {
		return BandedAlignments.globalBasic(seqA, 
				seqB, 
				new AlignmentMatrixScore(seqA.length()+5, seqB.length()+5), 
				new AlignmentMatrixBacktrack(seqA.length()+5, seqB.length()+5), 
				scoreMat, 
				bandwidth);		
	}
	
	public static Alignment globalBasic(String seqA, 
			String seqB, 
			AlignmentMatrixScore alignScoreMat,
			AlignmentMatrixBacktrack alignPtrMat,
			SubstitutionMatrix scoreMat,
			int bandwidth) {
		GlobalAlignment galn = new GlobalAlignment(seqA, seqB, scoreMat);
		galn.setAlignmentMatrix(alignScoreMat, alignPtrMat);
		galn.initMatrix();
		galn.bandedFillMatrix(bandwidth);
		return galn.backtrack();
	}
	
	public static Alignment globalAffineNt(String seqA, 
			String seqB, 
			SubstitutionMatrix scoreMat,
			int bandwidth) {
		return BandedAlignments.globalAffine(seqA, 
				seqB, 
				new AffineAlignmentMatrixScore(seqA.length()+5, seqB.length()+5), 
				new AffineAlignmentMatrixBacktrack(seqA.length()+5, seqB.length()+5), 
				scoreMat, 
				bandwidth);
	}

	public static Alignment globalAffine(String seqA, 
			String seqB, 
			AffineAlignmentMatrixScore alignScoreMat,
			AffineAlignmentMatrixBacktrack alignPtrMat,
			SubstitutionMatrix scoreMat,
			int bandwidth) {
		AffineGlobalAlignmentNoPtrs galn = new AffineGlobalAlignmentNoPtrs(seqA, seqB, scoreMat);
		galn.setAlignmentMatrix(alignScoreMat, alignPtrMat);
		galn.initMatrix();
		galn.bandedFillMatrix(bandwidth);
		return galn.backtrack();
	}
	
	public static Alignment localBasic(String seqA, 
			String seqB, 
			SubstitutionMatrix scoreMat,
			int bandwidth) {
		return BandedAlignments.localBasic(seqA, 
				seqB,
				new AlignmentMatrixScore(seqA.length()+5, seqB.length()+5), 
				new AlignmentMatrixBacktrack(seqA.length()+5, seqB.length()+5), 
				scoreMat, 
				bandwidth);	
	}
	
	public static Alignment localBasic(String seqA, 
			String seqB, 
			AlignmentMatrixScore alignScoreMat,
			AlignmentMatrixBacktrack alignPtrMat,
			SubstitutionMatrix scoreMat,
			int bandwidth) {
		LocalAlignment laln = new LocalAlignment(seqA, seqB, scoreMat);
		laln.setAlignmentMatrix(alignScoreMat, alignPtrMat);
		laln.initMatrix();
		laln.bandedFillMatrix(bandwidth);
		return laln.backtrack();
	}
	
	
	private BandedAlignments() {}
}
