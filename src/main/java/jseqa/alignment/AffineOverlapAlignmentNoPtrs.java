package jseqa.alignment;

import jseqa.alignment.substitution.SubstitutionMatrix;

/**
 * Overlap alignment using affine gap penalties; i.e., global alignment that does not penalize gaps at start/end.
 *
 */
public class AffineOverlapAlignmentNoPtrs extends AffineGlobalAlignmentNoPtrs {

	public AffineOverlapAlignmentNoPtrs(String inSeqA, String inSeqB, SubstitutionMatrix scoremat) {
		super(inSeqA, inSeqB, scoremat);
	}
	
	/** 
	 * {@inheritDoc}
	 * <br>
	 * Initializing matrix without penalizing starting gaps 
	 */
	@Override
	public void initMatrix() {
		// now have penalties in first row/column
		for(int i = 1; i < this.lenSeqA+1; i++) {
			int val = 0;
			this.alignScoreMatrix.setUp(i, 0, val);
			this.alignScoreMatrix.setLeft(i, 0, PairwiseAlignment.MIN_VALUE);
			this.alignScoreMatrix.setDiagonal(i, 0, PairwiseAlignment.MIN_VALUE);
		}
		for(int j = 1; j < this.lenSeqB+1; j++) {
			int val = 0;
			this.alignScoreMatrix.setLeft(0, j, val);
			this.alignScoreMatrix.setUp(0, j, PairwiseAlignment.MIN_VALUE);
			this.alignScoreMatrix.setDiagonal(0, j, PairwiseAlignment.MIN_VALUE);
		}
		this.alignScoreMatrix.setDiagonal(0, 0, 0);
		this.alignScoreMatrix.setInsertion(0, 0, 0);
		this.alignScoreMatrix.setDeletion(0, 0, 0);
	}

	public void fillCell(int i, int j) {
		// diagonal recurrence looks at diagonal, up, left
		char aCh = this.inputSeqA.charAt(i-1);
		char bCh = this.inputSeqB.charAt(j-1);
		int gapOpenPenal = this.scoreMatrix.gapPenalty();
		int gapExtendPenal = this.scoreMatrix.gapExtensionPenalty();
		
		int diagScore = this.alignScoreMatrix.getDiagonal(i-1, j-1);
		int leftScore = this.alignScoreMatrix.getDeletion(i-1, j-1);
		int upScore = this.alignScoreMatrix.getInsertion(i-1, j-1);
		
		this.alignScoreMatrix.setDiagonal(i, j, Math.max(diagScore, Math.max(leftScore, upScore)) + this.scoreMatrix.score(aCh, bCh)); 
		
		// left recurrence looks to extend (left), or start a new gap (diag)
		int leftExtScore = this.alignScoreMatrix.getDeletion(i, j-1) + gapExtendPenal;
		int leftStrtScore = this.alignScoreMatrix.getDiagonal(i, j-1) + gapOpenPenal;
		if(i == this.lenSeqA) {
			leftExtScore -= gapExtendPenal;
			leftStrtScore -= gapOpenPenal;
		}
		this.alignScoreMatrix.setDeletion(i, j, Math.max(leftExtScore, leftStrtScore));
		
		// up recurrence looks to extend (up), or start a new gap (diag)
		int upExtScore = this.alignScoreMatrix.getInsertion(i-1, j) + gapExtendPenal;
		int upStrtScore = this.alignScoreMatrix.getDiagonal(i-1, j) + gapOpenPenal;
		if(j == this.lenSeqB) {
			upExtScore -= gapExtendPenal;
			upStrtScore -= gapOpenPenal;
		}
		this.alignScoreMatrix.setInsertion(i, j, Math.max(upExtScore, upStrtScore)); 
	}
	
}
