package jseqa.alignment;

import jseqa.alignment.substitution.SubstitutionMatrix;
import jseqa.util.Pair;

/**
 * Base class for pairwise alignments, only contains bare minimum for different implementations of 
 * pairwise alignments.
 *
 */
public abstract class AbstractPairwiseAlignment implements PairwiseAlignment {

    /** Along the rows of the matrix. */
    protected String inputSeqA;

    /** Along the columns of the matrix. */
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
    public abstract void initMatrix();

    @Override
    public void fillMatrix() {
        fillMatrix(this.lenSeqA + 1, this.lenSeqB + 1);
    }

    abstract void fillMatrix(int nrow, int ncol);

    @Override
    public void bandedFillMatrix(int bandwidth) {
        bandedFillMatrix(this.lenSeqA + 1, this.lenSeqB + 1, bandwidth);
    }

    abstract void bandedFillMatrix(int nrow, int ncol, int bandwidth);

    /**
     * Fill cell [i,j] of the dynamic programming matrix.
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
        if (aVal >= bVal && aVal >= cVal) {
            return 0;
        } else if (bVal > cVal) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Backtracks from the end cell of the extending class.
     */
    @Override
    public abstract Alignment backtrack();
    
    /**
     * Recursive method that backtracks and returns the compiled {@code Alignment} object.
     * @param i int index of row.
     * @param j int index of column.
     * @param aln {@code AlignmentBuilder} to build up alignment.
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
