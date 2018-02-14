package jseqa.alignment;

/**
 * Alignment scoring matrix for affine gap penalties, uses three matrices. 
 *
 */
public class AffineAlignmentMatrixScore extends AbstractAlignmentMatrix {

    /** Diagonal matrix. */
    protected AlignmentMatrixScore diagMatrix;

    /** Insertion matrix (UP). */
    protected AlignmentMatrixScore insMatrix;

    /** Deletion matrix (LEFT). */
    protected AlignmentMatrixScore delMatrix;

    /**
     * Constructs three matrices with {@code nrow} x {@code ncol} cells.
     * @param nrow int number of rows.
     * @param ncol int number of columns.
     */
    public AffineAlignmentMatrixScore(int nrow, int ncol) {
        super(nrow, ncol);
        this.diagMatrix = new AlignmentMatrixScore(nrow, ncol);
        this.insMatrix = new AlignmentMatrixScore(nrow, ncol);
        this.delMatrix = new AlignmentMatrixScore(nrow, ncol);
    }

    @Override
    public void clearMatrix() {
        this.diagMatrix.clearMatrix();
        this.insMatrix.clearMatrix();
        this.delMatrix.clearMatrix();
    }

    @Override
    public void clearCell(int i, int j) {
        this.diagMatrix.clearCell(i, j);
        this.insMatrix.clearCell(i, j);
        this.delMatrix.clearCell(i, j);
    }

    @Override
    public void setLowestVal(int i, int j) {
        this.diagMatrix.setLowestVal(i, j);
        this.insMatrix.setLowestVal(i, j);
        this.delMatrix.setLowestVal(i, j);
    }

    public void setDiagonal(int i, int j, int val) {
        this.diagMatrix.set(i, j, val);
    }

    /** Set up */
    public void setInsertion(int i, int j, int val) {
        this.insMatrix.set(i, j, val);
    }

    /** Set left. */
    public void setDeletion(int i, int j, int val) {
        this.delMatrix.set(i, j, val);
    }

    /** Set insertion. */
    public void setUp(int i, int j, int val) {
        this.insMatrix.set(i, j, val);
    }

    /** Set deletion. */
    public void setLeft(int i, int j, int val) {
        this.delMatrix.set(i, j, val);
    }

    public int getDiagonal(int i, int j) {
        return this.diagMatrix.get(i, j);
    }

    public int getInsertion(int i, int j) {
        return this.insMatrix.get(i, j);
    }

    public int getDeletion(int i, int j) {
        return this.delMatrix.get(i, j);
    }

    public String toStringDiagonal() {
        return this.diagMatrix.toString();
    }

    public String toStringInsertion() {
        return this.insMatrix.toString();
    }

    public String toStringDeletion() {
        return this.delMatrix.toString();
    }

    @Override
    public String cellToString(int i, int j) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return this.toStringDiagonal() 
                + "\n" 
                + this.toStringInsertion() 
                + "\n" 
                + this.toStringDeletion();
    }

    public String toString(int maxRow, int maxCol) {
        return this.diagMatrix.toString(maxRow, maxCol) 
                + "\n"
                + this.insMatrix.toString(maxRow, maxCol) 
                + "\n"
                + this.delMatrix.toString(maxRow, maxCol);
    }

}
