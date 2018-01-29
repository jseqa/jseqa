package jseqa.alignment;

import jseqa.alignment.substitution.SubstitutionMatrix;
import jseqa.util.Pair;

/**
 * Global alignment using affine gap penalties, i.e., separate gap open and gap extension penalties. Uses three
 * matrices to keep track.
 *
 */
public class AffineGlobalAlignment extends AbstractAffinePairwiseAlignment implements FullMatrixFiller, BandedMatrixFiller {

	protected BacktrackPtr currMatrix;
	
	private int[] currDrow;
	private int[] prevDrow;
	
	public AffineGlobalAlignment(String inSeqA, String inSeqB, SubstitutionMatrix scoremat) {
		super(inSeqA, inSeqB, scoremat);
		this.currMatrix = null;
	}

	/** {@inheritDoc} */
	@Override
	public void initMatrix() {
//		super.initMatrix();
		int gapOpen = this.scoreMatrix.gapPenalty();
		int gapExt = this.scoreMatrix.gapExtensionPenalty();
		// now have penalties in first row/column
		for(int i = 1; i < this.lenSeqA+1; i++) {
			int val = gapOpen + ((i-1)*gapExt);
			this.alignScoreMatrix.setUp(i, 0, val);
			this.alignScoreMatrix.setLeft(i, 0, PairwiseAlignment.MIN_VALUE);
			this.alignScoreMatrix.setDiagonal(i, 0, PairwiseAlignment.MIN_VALUE);
		}
		for(int j = 1; j < this.lenSeqB+1; j++) {
			int val = gapOpen + ((j-1)*gapExt);
			this.alignScoreMatrix.setLeft(0, j, val);
			this.alignScoreMatrix.setUp(0, j, PairwiseAlignment.MIN_VALUE);
			this.alignScoreMatrix.setDiagonal(0, j, PairwiseAlignment.MIN_VALUE);
		}
		this.alignScoreMatrix.setDiagonal(0, 0, 0);
		this.alignScoreMatrix.setInsertion(0, 0, 0);
		this.alignScoreMatrix.setDeletion(0, 0, 0);
	}
	
	/** {@inheritDoc} */
	@Override
	public void fillMatrix(int nrow, int ncol) {
//		FullMatrixFiller.super.fillMatrix(nrow, ncol);
		for(int i = 1; i < nrow; i++) {
			for(int j = 1; j < ncol; j++) {
				fillCell(i,j);
			}
		}
	}
	
	/** {@inheritDoc} */
	@Override
	void bandedFillMatrix(int nrow, int ncol, int bandwidth) {
		BandedMatrixFiller.super.fillBandedMatrix(nrow, ncol, bandwidth);
	}
	
	/**
	 * {@inheritDoc}
	 * <br>
	 * Fills in each cell, of each matrix, using global alignment recurrence with affine gaps.
	 */
	public void fillCell(int i, int j) {
		// diagonal recurrence looks at diagonal, up, left
		char aCh = this.inputSeqA.charAt(i-1);
		char bCh = this.inputSeqB.charAt(j-1);
		int gapOpenPenal = this.scoreMatrix.gapPenalty();
		int gapExtendPenal = this.scoreMatrix.gapExtensionPenalty();
		
		int diagScore = this.alignScoreMatrix.getDiagonal(i-1, j-1);
		int leftScore = this.alignScoreMatrix.getDeletion(i-1, j-1);
		int upScore = this.alignScoreMatrix.getInsertion(i-1, j-1);
		
//		int matchmismatchScore = this.scoreMatrix.score(aCh, bCh);
//		this.alignScoreMatrix.setDiagonal(i, j, Math.max(diagScore, Math.max(leftScore, upScore)) + matchmismatchScore);
		this.alignScoreMatrix.setDiagonal(i, j, Math.max(diagScore, Math.max(leftScore, upScore)) + this.scoreMatrix.score(aCh, bCh)); 
		
		// left recurrence looks to extend (left), or start a new gap (diag)
		int leftExtScore = this.alignScoreMatrix.getDeletion(i, j-1) + gapExtendPenal;
		int leftStrtScore = this.alignScoreMatrix.getDiagonal(i, j-1) + gapOpenPenal;
		this.alignScoreMatrix.setDeletion(i, j, Math.max(leftExtScore, leftStrtScore));
		
		// up recurrence looks to extend (up), or start a new gap (diag)
		int upExtScore = this.alignScoreMatrix.getInsertion(i-1, j) + gapExtendPenal;
		int upStrtScore = this.alignScoreMatrix.getDiagonal(i-1, j) + gapOpenPenal;
		this.alignScoreMatrix.setInsertion(i, j, Math.max(upExtScore, upStrtScore)); 
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * Sets the backtrack cell as the best scoring final cell of all three matrices.
	 */
	@Override
	protected void setBacktrackCell() {
		this.endCell = new Pair<Integer,Integer>(this.lenSeqA, this.lenSeqB);
		// determine which matrix to start from
		int diagScore = this.alignScoreMatrix.getDiagonal(this.lenSeqA, this.lenSeqB);
		int leftScore = this.alignScoreMatrix.getDeletion(this.lenSeqA, this.lenSeqB);
		int upScore = this.alignScoreMatrix.getInsertion(this.lenSeqA, this.lenSeqB);
		
		int retval = this.argmax3(diagScore, leftScore, upScore);
		if(retval == 0) {
			this.endMatrix = BacktrackPtr.DIAG;
		} else if(retval == 1) {
			this.endMatrix = BacktrackPtr.LEFT;
		} else {
			this.endMatrix = BacktrackPtr.UP;
		}
	}

	/** {@inheritDoc} */
	@Override
	protected Alignment recursiveBacktrack(int i, int j, BacktrackPtr matrix, AlignmentBuilder aln) {
		if(i <= 0 && j <= 0) {
			return aln.toAlignment();
		}
		
		if(matrix == BacktrackPtr.DIAG) {
			BacktrackPtr nextPtr = this.getBestMatrixDiagonal(i, j);
			char aCh = this.inputSeqA.charAt(i-1);
			char bCh = this.inputSeqB.charAt(j-1);
			aln.addColumn(aCh, bCh);
			return this.recursiveBacktrack(i-1, j-1, nextPtr, aln);
		} else if(matrix == BacktrackPtr.UP) {
			BacktrackPtr nextPtr = this.getBestMatrixUp(i, j);
			char aCh = this.inputSeqA.charAt(i-1);
			aln.addColumn(aCh, '-');
			return this.recursiveBacktrack(i-1, j, nextPtr, aln);
		} else if(matrix == BacktrackPtr.LEFT) {
			BacktrackPtr nextPtr = this.getBestMatrixLeft(i, j);
			char bCh = this.inputSeqB.charAt(j-1);
			aln.addColumn('-', bCh);
			return this.recursiveBacktrack(i, j-1, nextPtr, aln);
		} else {
			return null;
		}
	}
	
}
