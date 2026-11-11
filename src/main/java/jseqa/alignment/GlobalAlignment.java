package jseqa.alignment;

import jseqa.alignment.substitution.SubstitutionMatrix;
import jseqa.util.Pair;

/**
 * Global alignment, aligning two full length sequences, using basic indel penalties
 * (<a href="https://en.wikipedia.org/wiki/Needleman%E2%80%93Wunsch_algorithm">Needleman-Wunch</a>).
 * <br><br>
 *  The alignment matrix \(M\), using indel penalty \(\Delta\) and substitution matrix \(\delta\), is computed
 *  for sequences \(u\) and \(v\) by the recurrence relation:
 *  \[
 *  M_{i,j} = \max
 *     \begin{cases}
 *     M_{i-1,j-1} + \delta(u_i,v_j) &amp;   \\
 *     M_{i-1,j} + \Delta &amp; \\
 *     M_{i,j-1} + \Delta
 *     \end{cases}
 *  \]
 *  Backtracking starts from the last element of the matrix, cell \(M_{nm}\).
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
        this.alignScoreMatrix.set(0, 0, 0);
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
        BandedMatrixFiller.super.fillBandedMatrix(nrow, ncol, bandwidth,
                this.alignScoreMatrix);
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
