package jseqa.alignment;

import jseqa.alignment.substitution.SubstitutionMatrix;
import jseqa.util.Pair;

/**
 * Base class for pairwise alignments, only contains bare minimum for different implementations of 
 * pairwise alignments.
 *
 */
public abstract class AbstractPairwiseAlignment implements PairwiseAlignment {
	
	/** Along the rows of the matrix */
	protected String inputSeqA;
	
	/** Along the columns of the matrix */
	protected String inputSeqB;
	
	protected int lenSeqA;
	
	protected int lenSeqB;
	
	protected SubstitutionMatrix scoreMatrix;
	
	protected Pair<Integer,Integer> endCell;
	
	protected Alignment currAlign;
	
	public AbstractPairwiseAlignment(String inSeqA, String inSeqB, SubstitutionMatrix scoremat) {
		this.inputSeqA = inSeqA;
		this.inputSeqB = inSeqB;
		this.lenSeqA = this.inputSeqA.length();
		this.lenSeqB = this.inputSeqB.length();
		this.scoreMatrix = scoremat;
	}
	
	@Override
	abstract public void initMatrix();
//	public void initMatrix() {
//		this.alignScoreMatrix.clearSubMatrix(this.lenSeqA, this.lenSeqB);
//		this.alignPtrMatrix.clearSubMatrix(this.lenSeqA, this.lenSeqB);
//	}

	@Override
	public void fillMatrix() {
		fillMatrix(this.lenSeqA+1, this.lenSeqB+1);
	}
	
	abstract void fillMatrix(int nrow, int ncol);
	
	@Override
	public void bandedFillMatrix(int bandwidth) {
		bandedFillMatrix(this.lenSeqA+1, this.lenSeqB+1, bandwidth);
	}
	
	abstract void bandedFillMatrix(int nrow, int ncol, int bandwidth);
	
//	public void fillMatrix() {
//		for(int i = 1; i < this.lenSeqA+1; i++) {
//			for(int j = 1; j < this.lenSeqB+1; j++) {
//				fillCell(i,j);
//			}
//		}
//	}
	
	/**
	 * Fill cell [i,j] of the dynamic programming matrix
	 * @param i int row index of cell
	 * @param j int column index of cell
	 */
	abstract public void fillCell(int i, int j);
	
	abstract protected void setBacktrackCell();
	
	/**
	 * Returns the argmax for three values, i.e., 0, 1, 2, corresponding 
	 * to aVal, bVal, and cVal, respectively.
	 * @param aVal
	 * @param bVal
	 * @param cVal
	 * @return
	 */
	protected int argmax3(int aVal, int bVal, int cVal) {
		if(aVal >= bVal && aVal >= cVal) {
			return 0;
		} else if(bVal > cVal) {
			return 1;
		} else {
			return 2;
		}
	}

	protected BacktrackPtr argmax3Ptr(int diagVal, int insVal, int delVal) {
		if(diagVal >= delVal && diagVal >= insVal) {
			return BacktrackPtr.DIAG;
		} else if(insVal > delVal) {
			return BacktrackPtr.LEFT;
		} else {
			return BacktrackPtr.UP;
		}
	}
	
//	protected int maxVal(int aVal, int bVal, int cVal) {
//		if(aVal >= bVal && aVal >= cVal) {
//			return aVal;
//		} else if(bVal > cVal) {
//			return bVal;
//		} else {
//			return cVal;
//		}
//	}
	
	protected int maxB(int a, int b, int c) {
		if (a > b) {
			if (a > c) {
				return a;
	         }
	         return c;
        }
        if (b > c) {
        	return b;
	    }
        return c;
    }
	
	/**
	 * Backtracks from the end cell of the extending class.
	 */
	@Override
	abstract public Alignment backtrack();
//	public Alignment backtrack() {
//		this.setBacktrackCell();
//		int score = this.alignScoreMatrix.get(this.endCell.first(), this.endCell.second());
//		this.currAlign = this.recursiveBacktrack(this.endCell.first(), this.endCell.second(), new AlignmentBuilder(score));
//		return this.currAlign;
//	}
	
	/**
	 * Recursive method that backtracks and returns the compiled {@code Alignment} object.
	 * @param i
	 * @param j
	 * @param aln
	 * @return
	 */
	abstract protected Alignment recursiveBacktrack(int i, int j, AlignmentBuilder aln);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Alignment getAlignment() {
		return this.currAlign;
	}

}
