package jseqa.alignment;

import jseqa.alignment.substitution.SubstitutionMatrix;

public abstract class AbstractAffinePairwiseAlignment extends AbstractPairwiseAlignment {

	/** Alignment score matrix for affine penalties - contains three matrices */
	protected AffineAlignmentMatrixScore alignScoreMatrix;
	
	/** Alignment pointer matrix for affine penalties - contains three matrices */
	@Deprecated
	protected AffineAlignmentMatrixBacktrack alignPtrMatrix;
	
	protected BacktrackPtr endMatrix;
	
	public AbstractAffinePairwiseAlignment(String inSeqA, String inSeqB, SubstitutionMatrix scoremat) {
		super(inSeqA, inSeqB, scoremat);
	}

	public void setAlignmentMatrix(AffineAlignmentMatrixScore alignScoreMat, AffineAlignmentMatrixBacktrack alignPtrMat) {
		if(alignScoreMat.nrows() < this.lenSeqA) {
			throw new IllegalArgumentException("Score matrix has too few rows");
		}
		if(alignScoreMat.ncols() < this.lenSeqB) {
			throw new IllegalArgumentException("Score matrix has too few cols");
		}
		this.alignScoreMatrix = alignScoreMat;
		this.alignPtrMatrix = alignPtrMat;
	}
	
	@Override
	public void initMatrix() {
		this.alignScoreMatrix.clearSubMatrix(this.lenSeqA, this.lenSeqB);
		this.alignPtrMatrix.clearSubMatrix(this.lenSeqA, this.lenSeqB);
	}
	
	@Override
	abstract public void fillCell(int i, int j);

	/**
	 * {@inheritDoc}
	 * 
	 * Sets the cell [i,j] to backtrack from as well as the {@link #endMatrix}.
	 */
	@Override
	abstract protected void setBacktrackCell();
	
	public int getScore() {
		this.setBacktrackCell();
		if(this.endMatrix == BacktrackPtr.DIAG) {
			return this.alignScoreMatrix.getDiagonal(this.endCell.first(), this.endCell.second());
		} else if(this.endMatrix == BacktrackPtr.LEFT) {
			return this.alignScoreMatrix.getInsertion(this.endCell.first(), this.endCell.second());
		} else {
			return this.alignScoreMatrix.getDeletion(this.endCell.first(), this.endCell.second());
		}
	}
	
	/**
	 * Get the best matrix to move from (or to if backtracking) if starting from {@code BacktrackPtr.DIAG} matrix.
	 * @param i int row index
	 * @param j int column index
	 * @return {@code BacktrackPtr} of best score among diagonal, deletion, insertion matrices at [i,j].
	 */
	protected BacktrackPtr getBestMatrixDiagonal(int i, int j) {
		int diagScore = this.alignScoreMatrix.getDiagonal(i-1, j-1); 
		int leftScore = this.alignScoreMatrix.getDeletion(i-1, j-1); 
		int upScore = this.alignScoreMatrix.getInsertion(i-1, j-1); 
		
		int retval = this.argmax3(diagScore, leftScore, upScore);
		if(retval == 0) {
			return BacktrackPtr.DIAG;
		} else if(retval == 1) {
			return BacktrackPtr.LEFT;
		} else {
			return BacktrackPtr.UP;
		}
	}
	
	/**
	 * Get the best matrix to move from (or to if backtracking) if starting from {@code BacktrackPtr.UP} matrix.
	 * @param i int row index
	 * @param j int column index
	 * @return {@code BacktrackPtr} of best score among diagonal or insertion matrices at [i,j].
	 */
	protected BacktrackPtr getBestMatrixUp(int i, int j) {
		int upExtScore = this.alignScoreMatrix.getInsertion(i-1, j);
		int upStrtScore = this.alignScoreMatrix.getDiagonal(i-1, j);
		if(upExtScore >= upStrtScore) {
			return BacktrackPtr.UP;
		} else {
			return BacktrackPtr.DIAG;
		}
	}
	
	/**
	 * Get the best matrix to move from (or to if backtracking) if starting from {@code BacktrackPtr.LEFT} matrix.
	 * @param i int row index
	 * @param j int column index
	 * @return {@code BacktrackPtr} of best score among diagonal or deletion matrices at [i,j].
	 */
	protected BacktrackPtr getBestMatrixLeft(int i, int j) {
		int leftExtScore = this.alignScoreMatrix.getDeletion(i, j-1);
		int leftStrtScore = this.alignScoreMatrix.getDiagonal(i, j-1);
		if(leftExtScore >= leftStrtScore) {
			return BacktrackPtr.LEFT;
		} else {
			return BacktrackPtr.DIAG;
		}
	}
	
	/**
	 * Backtracks from the end cell of the extending class.
	 */
	@Override
	public Alignment backtrack() {
		this.setBacktrackCell();
		
		int diagScore = this.alignScoreMatrix.getDiagonal(this.endCell.first(), this.endCell.second());
		int insScore = this.alignScoreMatrix.getInsertion(this.endCell.first(), this.endCell.second());
		int delScore = this.alignScoreMatrix.getDeletion(this.endCell.first(), this.endCell.second());
		int maxScore = Math.max(diagScore, Math.max(insScore, delScore));
		
//		this.currAlign = this.recursiveBacktrack(this.endCell.first(), this.endCell.second(), new AlignmentBuilder(maxScore));
		this.currAlign = this.recursiveBacktrack(this.endCell.first(), this.endCell.second(), this.endMatrix, new AlignmentBuilder(maxScore));
		return this.currAlign;
	}

	@Override
//	abstract protected Alignment recursiveBacktrack(int i, int j, AlignmentBuilder aln);
	protected Alignment recursiveBacktrack(int i, int j, AlignmentBuilder aln) {
		return null;
	}
	
	abstract protected Alignment recursiveBacktrack(int i, int j, BacktrackPtr matrix, AlignmentBuilder aln);

}
