package jseqa.alignment;

import jseqa.alignment.substitution.SubstitutionMatrix;
import jseqa.util.Pair;

/**
 * Local alignment using affine gap penalties. Uses three matrices to keep track. 
 *
 */
public class AffineLocalAlignment extends AffineGlobalAlignment {

    public AffineLocalAlignment(String inSeqA, String inSeqB, SubstitutionMatrix scoremat) {
        super(inSeqA, inSeqB, scoremat);
    }

    @Override
    public void initMatrix() {
        super.initMatrix();
        // have free rides,no penalties
        for (int i = 1; i < this.lenSeqA + 1; i++) {
            this.alignScoreMatrix.setUp(i, 0, 0);
            this.alignScoreMatrix.setLeft(i, 0, 0);
            this.alignScoreMatrix.setDiagonal(i, 0, 0);
        }
        for (int j = 1; j < this.lenSeqB + 1; j++) {
            this.alignScoreMatrix.setLeft(0, j, 0);
            this.alignScoreMatrix.setUp(0, j, 0);
            this.alignScoreMatrix.setDiagonal(0, j, 0);
        }
        this.alignScoreMatrix.setDiagonal(0, 0, 0);
        this.alignScoreMatrix.setInsertion(0, 0, 0);
        this.alignScoreMatrix.setDeletion(0, 0, 0);
    }

    /**
     * {@inheritDoc}
     * <br>
     * Additionally adds a free-ride to the current cell if is better than other alternatives.
     */
    @Override
    public void fillCell(int i, int j) {
        super.fillCell(i, j);
        // now check if a free-ride is better than what global alignment set for diagonal matrix
        if (this.alignScoreMatrix.getDiagonal(i, j) <= 0) {
            this.alignScoreMatrix.setDiagonal(i, j, 0);
        }
    }

    /**
     * {@inheritDoc}
     * <br>
     * Search all three N x M matrices for the best scoring cell.
     */
    @Override
    protected void setBacktrackCell() {
        this.setBacktrackCell(PairwiseAlignment.UNBANDED);
    }

    /** Search only cells populated by the requested band. */
    @Override
    protected void setBacktrackCell(int bandwidth) {
        Pair<Integer,Integer> diagBest = this.getMaxCell(this.alignScoreMatrix.diagMatrix,
                this.lenSeqA, this.lenSeqB, bandwidth);
        Pair<Integer,Integer> insBest = this.getMaxCell(this.alignScoreMatrix.insMatrix,
                this.lenSeqA, this.lenSeqB, bandwidth);
        Pair<Integer,Integer> delBest = this.getMaxCell(this.alignScoreMatrix.delMatrix,
                this.lenSeqA, this.lenSeqB, bandwidth);

        int diagScore = this.alignScoreMatrix.getDiagonal(diagBest.first(), diagBest.second());
        int insScore = this.alignScoreMatrix.getInsertion(insBest.first(), insBest.second());
        int delScore = this.alignScoreMatrix.getDeletion(delBest.first(), delBest.second());

        int retval = this.argmax3(diagScore, insScore, delScore);
        if (retval == 0) {
            this.endCell = diagBest;
            this.endMatrix = BacktrackPtr.DIAG;
        } else if (retval == 1) {
            this.endCell = insBest;
            this.endMatrix = BacktrackPtr.UP;
        } else {
            this.endCell = delBest;
            this.endMatrix = BacktrackPtr.LEFT;
        }
    }

    /**
     * Find the maximum scoring cell in a score matrix.
     */
    private Pair<Integer,Integer> getMaxCell(AlignmentMatrixScore mat,
                                              int maxNRow,
                                              int maxNCol,
                                              int bandwidth) {
        int max_i = bandwidth == PairwiseAlignment.UNBANDED ? maxNRow : 0;
        int max_j = bandwidth == PairwiseAlignment.UNBANDED ? maxNCol : 0;
        int maxScore = mat.get(max_i, max_j);
        int firstRow = bandwidth == PairwiseAlignment.UNBANDED ? 0 : 1;
        for (int i = firstRow; i < maxNRow + 1; i++) {
            int firstColumn = 1;
            int endColumn = maxNCol + 1;
            if (bandwidth == PairwiseAlignment.UNBANDED) {
                firstColumn = 0;
            } else {
                if (bandwidth < 1) {
                    throw new IllegalArgumentException("Bandwidth must be at least 1");
                }
                firstColumn = this.firstBandColumn(i,
                        maxNCol + 1, bandwidth);
                endColumn = this.endBandColumn(i,
                        maxNCol + 1, bandwidth);
            }
            for (int j = firstColumn; j < endColumn; j++) {
                if (mat.get(i, j) > maxScore) {
                    max_i = i;
                    max_j = j;
                    maxScore = mat.get(i, j);
                }
            }
        }
        return new Pair<Integer,Integer>(max_i, max_j);
    }

    @Override
    protected BacktrackPtr getBestMatrixDiagonal(int i, int j) {
        BacktrackPtr ptr = super.getBestMatrixDiagonal(i, j);
        if (ptr == BacktrackPtr.DIAG) {
            return this.alignScoreMatrix.getDiagonal(i - 1, j - 1) == 0 ? BacktrackPtr.FREERIDE : ptr;
        } else if (ptr == BacktrackPtr.LEFT) {
            return this.alignScoreMatrix.getDeletion(i - 1, j - 1) == 0 ? BacktrackPtr.FREERIDE : ptr;
        } else {
            return this.alignScoreMatrix.getInsertion(i - 1, j - 1) == 0 ? BacktrackPtr.FREERIDE : ptr;
        }
    }

    @Override
    protected Alignment recursiveBacktrack(int i, int j, BacktrackPtr matrix, AlignmentBuilder aln) {
        if (i <= 0 && j <= 0) {
            return aln.toAlignment();
        }

        if (matrix == BacktrackPtr.FREERIDE) {
            return this.recursiveBacktrack(0, 0, matrix, aln);
        }

        BacktrackPtr nextMove = null;
        if (matrix == BacktrackPtr.DIAG) {
            nextMove = this.getBestMatrixDiagonal(i, j);
        } else if (matrix == BacktrackPtr.LEFT) {
            nextMove = this.getBestMatrixLeft(i, j);
        } else if (matrix == BacktrackPtr.UP) {
            nextMove = this.getBestMatrixUp(i, j);
        }

        return super.recursiveBacktrack(i, j, matrix, aln);
    }
}
