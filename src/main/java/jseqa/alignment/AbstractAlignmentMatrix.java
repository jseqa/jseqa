package jseqa.alignment;

public abstract class AbstractAlignmentMatrix implements AlignmentMatrix {

    protected int numRows;

    protected int numCols;

    public AbstractAlignmentMatrix(int nrow, int ncol) {
        this.numRows = nrow;
        this.numCols = ncol;
    }

    /**
     * Clears the contents of the entire matrix.
     */
    @Override
    public void clearMatrix() {
        this.clearSubMatrix(this.numRows, this.numCols);
    }

    /**
     * Clear the contents of a submatrix from [0..maxRow][0..maxCol]
     * @param maxRow int row index 
     * @param maxCol int column index
     */
    public void clearSubMatrix(int maxRow, int maxCol) {
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxCol; j++) {
                clearCell(i, j);
            }
        }
    }

    /**
     * Clear the contents of only the upper triangle.
     */
    public void clearUpperTriangle() {
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numCols; j++) {
                if (i < j) {
                    clearCell(i, j);
                }
            }
        }
    }

    @Override
    public abstract void clearCell(int i, int j);

    @Override
    public abstract void setLowestVal(int i, int j);

    @Override
    public int nrows() {
        return this.numRows;
    }

    @Override
    public int ncols() {
        return this.numCols;
    }

    @Override
    public String toString() {
        return this.toString(this.numRows, this.numCols);
    }
    
    /**
     * Represent the alignment as a {@code String}.
     * @param maxRow
     * @param maxCol
     * @return
     */
    public String toString(int maxRow, int maxCol) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxCol; j++) {
                s.append(cellToString(i, j));
                if (j < maxCol - 1) {
                    s.append(",");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }

    public abstract String cellToString(int i, int j);
}
