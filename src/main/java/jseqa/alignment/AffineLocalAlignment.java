package jseqa.alignment;

import jseqa.alignment.substitution.SubstitutionMatrix;
import jseqa.util.Pair;

/**
 * 
 * @deprecated
 */
public class AffineLocalAlignment extends AffineGlobalAlignment {

	public AffineLocalAlignment(String inSeqA, String inSeqB, SubstitutionMatrix scoremat) {
		super(inSeqA, inSeqB, scoremat);
	}
	
	@Override
	public void initMatrix() {
		super.initMatrix();
		// have free rides,no penalties
		for(int i = 1; i < this.lenSeqA+1; i++) {
			this.alignScoreMatrix.setUp(i, 0, 0);
			this.alignScoreMatrix.setLeft(i, 0, 0);
			this.alignScoreMatrix.setDiagonal(i, 0, 0);
			//
			this.alignPtrMatrix.setUp(i, 0, BacktrackPtr.FREERIDE);
			this.alignPtrMatrix.setLeft(i, 0, BacktrackPtr.FREERIDE);
			this.alignPtrMatrix.setDiagonal(i, 0, BacktrackPtr.FREERIDE);
		}
		for(int j = 1; j < this.lenSeqB+1; j++) {
			this.alignScoreMatrix.setLeft(0, j, 0);
			this.alignScoreMatrix.setUp(0, j, 0);
			this.alignScoreMatrix.setDiagonal(0, j, 0);
			//
			this.alignPtrMatrix.setLeft(0, j, BacktrackPtr.FREERIDE);
			this.alignPtrMatrix.setUp(0, j, BacktrackPtr.FREERIDE);
			this.alignPtrMatrix.setDiagonal(0, j, BacktrackPtr.FREERIDE);
		}
		this.alignScoreMatrix.setDiagonal(0, 0, 0);
		this.alignScoreMatrix.setInsertion(0, 0, 0);
		this.alignScoreMatrix.setDeletion(0, 0, 0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fillCell(int i, int j) {
		super.fillCell(i, j);
		// now check if a free-ride is better than what global alignment set for diagonal matrix
		if(this.alignScoreMatrix.getDiagonal(i, j) <= 0) {
			this.alignScoreMatrix.setDiagonal(i, j, 0);
			this.alignPtrMatrix.setDiagonal(i, j, BacktrackPtr.FREERIDE);
		}
//		System.err.println(i + "\t" + j + "\t" + this.alignScoreMatrix.getDiagonal(i, j) + "\t" + this.alignPtrMatrix.getDiagonal(i, j));
	}
	
	/**
	 * {@inheritDoc}
	 * <br>
	 * Search all three N x M matrix for best scoring cell and backtrack starting from there.
	 */
	@Override
	protected void setBacktrackCell() {
		Pair<Integer,Integer> diagBest = this.getMaxCell(this.alignScoreMatrix.diagMatrix, this.lenSeqA, this.lenSeqB);
		Pair<Integer,Integer> insBest = this.getMaxCell(this.alignScoreMatrix.insMatrix, this.lenSeqA, this.lenSeqB);
		Pair<Integer,Integer> delBest = this.getMaxCell(this.alignScoreMatrix.delMatrix, this.lenSeqA, this.lenSeqB);
		
		int diagScore = this.alignScoreMatrix.getDiagonal(diagBest.first(), diagBest.second());
		int insScore = this.alignScoreMatrix.getInsertion(diagBest.first(), diagBest.second());
		int delScore = this.alignScoreMatrix.getDeletion(diagBest.first(), diagBest.second());
		
		int retval = this.argmax3(diagScore, insScore, delScore);
		if(retval == 0) {
			this.endCell = diagBest;
			this.endMatrix = BacktrackPtr.DIAG;
		} else if(retval == 1) {
			this.endCell = insBest;
			this.endMatrix = BacktrackPtr.LEFT;
		} else { //if(retval == 2) {
			this.endCell = delBest;
			this.endMatrix = BacktrackPtr.UP;
		} 
	}
	
	/**
	 * Helper method that finds the maximum scoring cell in the matrix of scores.
	 * @param mat {@code AlignmentMatrixScore} of scores to search.
	 * @param maxNRow 
	 * @param maxNCol
	 * @return
	 */
	private Pair<Integer,Integer> getMaxCell(AlignmentMatrixScore mat, int maxNRow, int maxNCol) {
		Pair<Integer,Integer> maxCell = new Pair<Integer,Integer>(maxNRow, maxNCol);
		int maxScore = mat.get(maxNRow, maxNCol);
		for(int i = 0; i < maxNRow+1; i++) {
			for(int j = 0; j < maxNCol+1; j++) {
				if(mat.get(i, j) > maxScore) {
					maxCell = new Pair<Integer,Integer>(i, j);
					maxScore = mat.get(i, j);
				}
			}
		}
		return maxCell;
	}
	
	/** {@inheritDoc} */
	protected Alignment recursiveBacktrack(int i, int j, BacktrackPtr matrix, AlignmentBuilder aln) {
		if(i <= 0 && j <= 0) {
			return aln.toAlignment();
		}
//		System.out.println(i + "\t" + j + "\t" + matrix + "\t" + aln.getTopRow() + "\t" + aln.getBottomRow());
		
		if(matrix == BacktrackPtr.FREERIDE) {
			return this.recursiveBacktrack(0, 0, matrix, aln);
		}
		
		BacktrackPtr nextMove = null;
		if(matrix == BacktrackPtr.DIAG) {
			nextMove = this.alignPtrMatrix.getDiagonal(i, j);
		} else if(matrix == BacktrackPtr.LEFT) {
			nextMove = this.alignPtrMatrix.getInsertion(i, j);
		} else if(matrix == BacktrackPtr.UP) {
			nextMove = this.alignPtrMatrix.getDeletion(i, j);
		}
		
		if(nextMove == BacktrackPtr.FREERIDE) {
			return this.recursiveBacktrack(0, 0, matrix, aln);
		} else {
			return super.recursiveBacktrack(i, j, matrix, aln);
		}
	}
}
