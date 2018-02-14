package jseqa.alignment;

import jseqa.alignment.substitution.SubstitutionMatrix;
import jseqa.util.Pair;

/**
 * Fitting alignment (i.e., semi-global alignment), fits one sequence into another 
 * larger sequence, without penalizing either end. 
 * <br>
 * <pre>{@code
 * larger seq:    ----------------------------
 * smaller seq:            ------------
 * }</pre>
 *
 */
public class FittingAlignment extends AbstractBasicPairwiseAlignment 
    implements FullMatrixFiller, BandedMatrixFiller {

    /**
     * Constructor that fits smaller seq A, into larger seq B.
     * @param inSeqA {@code String} smaller sequence.
     * @param inSeqB {@code String} larger sequence.
     * @param scoremat {@code SubstitutionMatrix} used in pairwise alignments.
     */
    public FittingAlignment(String inSeqA, String inSeqB, SubstitutionMatrix scoremat) {
        super(inSeqA, inSeqB, scoremat);
    }

    /**
     * {@inheritDoc}
     * <br>
     * Initialize the first row/column with indel penalties.
     */
    @Override
    public void initMatrix() {
        super.initMatrix();
        // normal penalties for start of smaller sequence
        for (int i = 1; i < this.lenSeqA + 1; i++) {
            int val = i * this.scoreMatrix.gapPenalty();
            this.alignScoreMatrix.set(i, 0, val);
            this.alignPtrMatrix.set(i, 0, BacktrackPtr.UP);
        }
        // have no penalties for starting the smaller sequence anywhere along larger sequence
        for (int j = 1; j < this.lenSeqB + 1; j++) {
            int val = 0;
            this.alignScoreMatrix.set(0, j, val);
            this.alignPtrMatrix.set(0, j, BacktrackPtr.LEFT);
        }
    }

    @Override
    public void fillMatrix(int nrow, int ncol) {
        FullMatrixFiller.super.fillMatrix(nrow, ncol);
    }

    @Override
    void bandedFillMatrix(int nrow, int ncol, int bandwidth) {
        BandedMatrixFiller.super.fillBandedMatrix(nrow, ncol, bandwidth);
    }

    @Override
    public void fillCell(int i, int j) {
        super.fillCell(i, j);
    }

    /**
     * {@inheritDoc}
     * <br>
     * Search best scoring cell among all positions at end of smaller sequence (i.e., last row).
     */
    @Override
    protected void setBacktrackCell() {
        this.endCell = new Pair<Integer,Integer>(this.lenSeqA, this.lenSeqB);
        int maxScore = this.alignScoreMatrix.get(this.lenSeqA, this.lenSeqB);
        int maxRow = this.lenSeqA;
        for (int j = 0; j < this.lenSeqB + 1; j++) {
            if (this.alignScoreMatrix.get(maxRow, j) > maxScore) {
                this.endCell = new Pair<Integer,Integer>(maxRow, j);
                maxScore = this.alignScoreMatrix.get(maxRow, j);
            }
        }
    }

}
