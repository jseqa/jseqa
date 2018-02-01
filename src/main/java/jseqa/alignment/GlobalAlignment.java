package jseqa.alignment;

import jseqa.alignment.substitution.SubstitutionMatrix;
import jseqa.util.Pair;

/**
 * Global alignment using basic indel penalties (Needleman-Wunch).
 *
 */
public class GlobalAlignment extends AbstractBasicPairwiseAlignment 
    implements FullMatrixFiller, BandedMatrixFiller {

    public GlobalAlignment(String inSeqA, String inSeqB, SubstitutionMatrix scoremat) {
        super(inSeqA, inSeqB, scoremat);
    }

    /**
     * {@inheritDoc}
     * <br>
     * Initialize the first row/column with indel penalties.
     */
    @Override
    public void initMatrix() {
        //		super.initMatrix();
        // now have penalties in first row/column
        for(int i = 1; i < this.lenSeqA+1; i++) {
            int val = i * this.scoreMatrix.gapPenalty();
            this.alignScoreMatrix.set(i, 0, val);
            this.alignPtrMatrix.set(i, 0, BacktrackPtr.UP);
        }
        for(int j = 1; j < this.lenSeqB+1; j++) {
            int val = j * this.scoreMatrix.gapPenalty();
            this.alignScoreMatrix.set(0, j, val);
            this.alignPtrMatrix.set(0, j, BacktrackPtr.LEFT);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void fillMatrix(int nrow, int ncol) {
        FullMatrixFiller.super.fillMatrix(nrow, ncol);
    }

    /** {@inheritDoc} */
    @Override
    void bandedFillMatrix(int nrow, int ncol, int bandwidth) {
        BandedMatrixFiller.super.fillBandedMatrix(nrow, ncol, bandwidth);
    }

    /** {@inheritDoc} */
    @Override
    public void fillCell(int i, int j) {
        super.fillCell(i, j);
    }
    //	/**
    //	 * {@inheritDoc}
    //	 * 
    //	 * Fill in the each cell using the global alignment (Needleman-Wunch) recurrence relation:<br>
    //	 * <i>d</i><sub>ij</sub> = max { 
    //	 * <i>d</i><sub>i-1j</sub>+ &Delta;, <br>
    //	 * <i>d</i><sub>ij-1</sub> + &Delta;, <br>
    //	 * <i>d</i><sub>i-1j-1</sub> + &delta;(i,j) <br> }
    //	 * <br>
    //	 * For &Delta; as the gap penalty and &delta;(x,y) defined as the substitution of a &rarr; b. 
    //	 */
    //	@Override
    //	public void fillCell(int i, int j) {
    //		char aCh = this.inputSeqA.charAt(i-1);
    //		char bCh = this.inputSeqB.charAt(j-1);
    //		
    //		int insScore = this.alignScoreMatrix.get(i-1, j) + this.scoreMatrix.gapPenalty();
    //		int delScore = this.alignScoreMatrix.get(i, j-1) + this.scoreMatrix.gapPenalty();
    //		int diagScore = this.alignScoreMatrix.get(i-1, j-1) + this.scoreMatrix.score(aCh, bCh);
    //		
    //		int retval = this.argmax3(diagScore, insScore, delScore);
    //		BacktrackPtr ptr = null;
    //		int score = 0;
    //		if(retval == 0) {
    //			score = diagScore;
    //			ptr = BacktrackPtr.DIAG;
    //		} else if(retval == 1) {
    //			score = insScore;
    //			ptr = BacktrackPtr.UP;
    //		} else {
    //			score = delScore;
    //			ptr = BacktrackPtr.LEFT;
    //		}
    //		this.alignScoreMatrix.set(i, j, score);
    //		this.alignPtrMatrix.set(i, j, ptr);
    //	}

    /** 
     * {@inheritDoc}
     * <br>
     * Backtracks from terminal cell [{@code lenSeqA}, {@code lenSeqB}].
     */
    @Override
    protected void setBacktrackCell() {
        this.endCell = new Pair<Integer,Integer>(this.lenSeqA, this.lenSeqB);
    }

    //	/** {@inheritDoc} */
    //	@Override
    //	protected Alignment recursiveBacktrack(int i, int j, AlignmentBuilder aln) {
    //		if(i <= 0 && j <= 0) {
    //			return aln.toAlignment();
    //		}
    //		BacktrackPtr ptr = this.alignPtrMatrix.get(i, j);
    //		if(ptr == BacktrackPtr.DIAG) {
    //			char aCh = this.inputSeqA.charAt(i-1);
    //			char bCh = this.inputSeqB.charAt(j-1);
    //			aln.addColumn(aCh, bCh);
    //			return this.recursiveBacktrack(i-1, j-1, aln);
    //		} else if(ptr == BacktrackPtr.UP) {
    //			char aCh = this.inputSeqA.charAt(i-1);
    //			aln.addColumn(aCh, '-');
    //			return this.recursiveBacktrack(i-1, j, aln);
    //		} else if(ptr == BacktrackPtr.LEFT) {
    //			char bCh = this.inputSeqB.charAt(j-1);
    //			aln.addColumn('-', bCh);
    //			return this.recursiveBacktrack(i, j-1, aln);
    //		} else {
    //			return aln.toAlignment();
    //		}
    //	}

}
