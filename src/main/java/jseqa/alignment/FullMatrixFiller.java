package jseqa.alignment;

/**
 * Populates the full N x M matrix, to be used as a mixin.
 *
 */
public interface FullMatrixFiller {

    /**
     * Fills each cell of an N x M matrix.
     * @param nrow int number of rows (N)
     * @param ncol int number of columns (M)
     */
    public default void fillMatrix(int nrow, int ncol) {
        for (int i = 1; i < nrow; i++) {
            for (int j = 1; j < ncol; j++) {
                fillCell(i,j);
            }
        }
    }

    /**
     * Fill cell [i,j] of the dynamic programming matrix.
     * @param i int row index of cell to fill.
     * @param j int column index of cell to fill.
     */
    public abstract void fillCell(int i, int j);

}
