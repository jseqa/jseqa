package jseqa.alignment;

import jseqa.alignment.substitution.SubstitutionMatrix;

/**
 * Abstract base class for the basic pairwise alignment algorithms.
 *
 */
public abstract class AbstractBasicPairwiseAlignment extends AbstractPairwiseAlignment {

	/** 
	 * An NxM matrix of scores, where N=length(seqA)+1; M=length(seqB)+1
	 */
	protected AlignmentMatrixScore alignScoreMatrix;
	
	/**
	 * An NxM matrix of backtracking pointers, where N=length(seqA)+1; M=length(seqB)+1
	 */
	protected AlignmentMatrixBacktrack alignPtrMatrix;
	
	public AbstractBasicPairwiseAlignment(String inSeqA, String inSeqB, SubstitutionMatrix scoremat) {
		super(inSeqA, inSeqB, scoremat);
	}
	
	public void setAlignmentMatrix(AlignmentMatrixScore alignScoreMat, AlignmentMatrixBacktrack alignPtrMat) {
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
	
	/**
	 * {@inheritDoc}
	 * <br>
	 * Fill in the each cell using the global alignment (Needleman-Wunch) recurrence relation:<br>
	 * <i>d</i><sub>ij</sub> = max { 
	 * <i>d</i><sub>i-1j</sub>+ &Delta;, <br>
	 * <i>d</i><sub>ij-1</sub> + &Delta;, <br>
	 * <i>d</i><sub>i-1j-1</sub> + &delta;(i,j) <br> }
	 * <br>
	 * For &Delta; as the gap penalty and &delta;(x,y) defined as the substitution score of a &rarr; b. 
	 */
	@Override
	public void fillCell(int i, int j) {
		char aCh = this.inputSeqA.charAt(i-1);
		char bCh = this.inputSeqB.charAt(j-1);
		
		int insScore = this.alignScoreMatrix.get(i-1, j) + this.scoreMatrix.gapPenalty();
		int delScore = this.alignScoreMatrix.get(i, j-1) + this.scoreMatrix.gapPenalty();
		int diagScore = this.alignScoreMatrix.get(i-1, j-1) + this.scoreMatrix.score(aCh, bCh);
		
		int retval = this.argmax3(diagScore, insScore, delScore);
		BacktrackPtr ptr = null;
		int score = 0;
		if(retval == 0) {
			score = diagScore;
			ptr = BacktrackPtr.DIAG;
		} else if(retval == 1) {
			score = insScore;
			ptr = BacktrackPtr.UP;
		} else {
			score = delScore;
			ptr = BacktrackPtr.LEFT;
		}
		this.alignScoreMatrix.set(i, j, score);
		this.alignPtrMatrix.set(i, j, ptr);
	}

	@Override
	abstract protected void setBacktrackCell();
	
	/**
	 * Find the cell that would be the start of backtracking, and report the score.
	 * @return int score of alignment.
	 */
	public int getScore() {
		this.setBacktrackCell();
		return this.alignScoreMatrix.get(this.endCell.first(), this.endCell.second());
	}
	
	/**
	 * Backtracks from the end cell of the extending class.
	 */
	@Override
	public Alignment backtrack() {
		this.setBacktrackCell();
		int score = this.alignScoreMatrix.get(this.endCell.first(), this.endCell.second());
		this.currAlign = this.recursiveBacktrack(this.endCell.first(), this.endCell.second(), new AlignmentBuilder(score));
		return this.currAlign;
	}

	@Override
//	abstract protected Alignment recursiveBacktrack(int i, int j, AlignmentBuilder aln);
	protected Alignment recursiveBacktrack(int i, int j, AlignmentBuilder aln) {
		if(i <= 0 && j <= 0) {
			return aln.toAlignment();
		}
		BacktrackPtr ptr = this.alignPtrMatrix.get(i, j);
		if(ptr == BacktrackPtr.DIAG) {
			char aCh = this.inputSeqA.charAt(i-1);
			char bCh = this.inputSeqB.charAt(j-1);
			aln.addColumn(aCh, bCh);
			return this.recursiveBacktrack(i-1, j-1, aln);
		} else if(ptr == BacktrackPtr.UP) {
			char aCh = this.inputSeqA.charAt(i-1);
			aln.addColumn(aCh, '-');
			return this.recursiveBacktrack(i-1, j, aln);
		} else if(ptr == BacktrackPtr.LEFT) {
			char bCh = this.inputSeqB.charAt(j-1);
			aln.addColumn('-', bCh);
			return this.recursiveBacktrack(i, j-1, aln);
		} else {
			return aln.toAlignment();
		}
	}

}
