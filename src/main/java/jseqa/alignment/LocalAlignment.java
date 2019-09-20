package jseqa.alignment;

import jseqa.alignment.substitution.SubstitutionMatrix;
import jseqa.util.Pair;

/**
 * Local alignment of two sequences using basic indel penalties (Smith-Waterman).
 *
 */
public class LocalAlignment extends AbstractBasicPairwiseAlignment 
    implements FullMatrixFiller, BandedMatrixFiller {

    private int maxScore;
    private int best_i;
    private int best_j;

    private int[] lclMat;
    private int ncols;
    private int nrows;
    private final int gappenal;
    private int matchmismatch;

    /**
     * Constructor for local sequence alignment.
     * @param inSeqA {@code String} sequence A to locally align.
     * @param inSeqB {@code String} sequence B to locally align.
     * @param scoremat {@code SubstitutionMatrix} to use in local alignment.
     */
    public LocalAlignment(String inSeqA, String inSeqB, SubstitutionMatrix scoremat) {
        super(inSeqA, inSeqB, scoremat);

        this.gappenal = this.scoreMatrix.gapPenalty();
        this.matchmismatch = -this.scoreMatrix.gapPenalty();
    }

    /**
     * {@inheritDoc}
     * <br>
     * Init matrix with freerides.
     */
    @Override
    public void initMatrix() {
        for (int i = 0; i < this.lenSeqA + 1; i++) {
            this.alignScoreMatrix.set(i, 0, 0);
            this.alignPtrMatrix.set(i, 0, BacktrackPtr.FREERIDE);
        }
        for (int j = 0; j < this.lenSeqB + 1; j++) {
            this.alignScoreMatrix.set(0, j, 0);
            this.alignPtrMatrix.set(0, j, BacktrackPtr.FREERIDE);
        }
        this.maxScore = 0;
        this.best_i = 0;
        this.best_j = 0;
    }

    /** {@inheritDoc} */
    @Override
    public void fillMatrix(int nrow, int ncol) {
        //		FullMatrixFiller.super.fillMatrix(nrow, ncol);
        for (int i = 1; i < nrow; i++) {
            for (int j = 1; j < ncol; j++) {
                this.fillCell(i,j);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    void bandedFillMatrix(int nrow, int ncol, int bandwidth) {
        BandedMatrixFiller.super.fillBandedMatrix(nrow, ncol, bandwidth);
    }

    /**
     * Fill in the each cell using the local alignment (Smith-Waterman) recurrence relation:<br>
     * <i>d</i><sub>ij</sub> = max { 
     * 0, <br>
     * <i>d</i><sub>i-1j</sub>+ &Delta;, <br> 
     * <i>d</i><sub>ij-1</sub> + &Delta;, <br>
     * <i>d</i><sub>i-1j-1</sub> + &delta;(i,j)<br> }
     * <br>
     * For &Delta; as the gap penalty and &delta;(x,y) defined as the substitution score of a &rarr; b. 
     */
    @Override	
    public void fillCell(int i, int j) {
        char aCh = this.inputSeqA.charAt(i - 1);
        char bCh = this.inputSeqB.charAt(j - 1);

        int insScore = this.alignScoreMatrix.get(i - 1, j) + this.scoreMatrix.gapPenalty();
        int delScore = this.alignScoreMatrix.get(i, j - 1) + this.scoreMatrix.gapPenalty();

        int diagScore = this.alignScoreMatrix.get(i - 1, j - 1) + this.scoreMatrix.score(aCh, bCh);
        int score = this.setCellOrig(i, j, diagScore, insScore, delScore);
    }

    private int setCellOrig(int i, int j, int diagScore, int insScore, int delScore) {
        int retval = this.argmax3(diagScore, insScore, delScore);
        BacktrackPtr ptr = null;
        int score = 0;
        if (retval == 0) {
            score = diagScore;
            ptr = BacktrackPtr.DIAG;
        } else if (retval == 1) {
            score = insScore;
            ptr = BacktrackPtr.UP;
        } else {
            score = delScore;
            ptr = BacktrackPtr.LEFT;
        }
        if (score < 0) {
            score = 0;
            ptr = BacktrackPtr.FREERIDE;
        } else if (score > this.maxScore) {
            this.maxScore = score;
            this.best_i = i;
            this.best_j = j;
        }

        this.alignScoreMatrix.set(i, j, score);
        this.alignPtrMatrix.set(i, j, ptr);
        return score;
    }

    /**
     * {@inheritDoc}
     * <br>
     * Search N x M matrix for best scoring cell and backtrack starting from there.
     */
    @Override
    protected void setBacktrackCell() {
        // used when finding max while filling in
        this.endCell = new Pair<Integer,Integer>(this.best_i, this.best_j);
    }

    /** {@inheritDoc} */
    @Override
    protected Alignment recursiveBacktrack(int i, int j, AlignmentBuilder aln) {
        if (i <= 0 && j <= 0) {
            return aln.toAlignment();
        }
        BacktrackPtr ptr = this.alignPtrMatrix.get(i, j);
        if (ptr == BacktrackPtr.DIAG) {
            char aCh = this.inputSeqA.charAt(i - 1);
            char bCh = this.inputSeqB.charAt(j - 1);
            aln.addColumn(aCh, bCh);
            return this.recursiveBacktrack(i - 1, j - 1, aln);
        } else if (ptr == BacktrackPtr.UP) {
            char aCh = this.inputSeqA.charAt(i - 1);
            aln.addColumn(aCh, '-');
            return this.recursiveBacktrack(i - 1, j, aln);
        } else if (ptr == BacktrackPtr.LEFT) {
            char bCh = this.inputSeqB.charAt(j - 1);
            aln.addColumn('-', bCh);
            return this.recursiveBacktrack(i, j - 1, aln);
        } else if (ptr == BacktrackPtr.FREERIDE) {
            return this.recursiveBacktrack(0, 0, aln);
        } else {
            return aln.toAlignment();
        }
    }

}
