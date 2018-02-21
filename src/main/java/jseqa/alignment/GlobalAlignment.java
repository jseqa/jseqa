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
        for (int i = 1; i < this.lenSeqA + 1; i++) {
            int val = i * this.scoreMatrix.gapPenalty();
            this.alignScoreMatrix.set(i, 0, val);
            this.alignPtrMatrix.set(i, 0, BacktrackPtr.UP);
        }
        for (int j = 1; j < this.lenSeqB + 1; j++) {
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

    /** 
     * {@inheritDoc}
     * <br>
     * Backtracks from terminal cell [{@code lenSeqA}, {@code lenSeqB}].
     */
    @Override
    protected void setBacktrackCell() {
        this.endCell = new Pair<Integer,Integer>(this.lenSeqA, this.lenSeqB);
    }
    
}
