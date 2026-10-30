package jseqa.alignment;

/**
 * Populates the banded diagonal of an N x M matrix, to be used as a mixin.
 * <br>
 * For example a band of 2 in the matrix below would fill in cells marked with #.
 * <pre>{@code  
 *  ---------
 *  |##
 *  |####
 *  | ####
 *  |  ####
 *  |   ####
 * 
 * }</pre>
 *
 */
public interface BandedMatrixFiller {

    /**
     * Get the first matrix column filled for a row.
     */
    public default int firstBandColumn(int row, int ncol, int bandwidth) {
        return Math.max(1, row - bandwidth);
    }

    /**
     * Get the exclusive upper bound of matrix columns filled for a row.
     */
    public default int endBandColumn(int row, int ncol, int bandwidth) {
        return Math.min(ncol, row + bandwidth);
    }

    /**
     * Return whether an interior matrix cell is contained in the band.
     */
    public default boolean isInsideBand(int row, int col, int ncol, int bandwidth) {
        return col >= firstBandColumn(row, ncol, bandwidth)
                && col < endBandColumn(row, ncol, bandwidth);
    }

    /**
     * Make cells immediately outside the band unreachable. These cells form a
     * one-cell halo around the band and prevent a recurrence from entering the
     * band through an unfilled or stale score.
     *
     * @param nrow number of rows in the active matrix
     * @param ncol number of columns in the active matrix
     * @param bandwidth width of the band
     * @param scoreMatrix score matrix to initialize
     */
    public default void initBandedMatrix(int nrow,
                                         int ncol,
                                         int bandwidth,
                                         AlignmentMatrix scoreMatrix) {
        if (bandwidth < 1) {
            throw new IllegalArgumentException("Bandwidth must be at least 1");
        }

        for (int i = 1; i < nrow; i++) {
            int firstColumn = firstBandColumn(i, ncol, bandwidth);
            int endColumn = endBandColumn(i, ncol, bandwidth);
            if (firstColumn < endColumn) {
                if (firstColumn > 1) {
                    scoreMatrix.setLowestVal(i, firstColumn - 1);
                }
                if (endColumn < ncol) {
                    scoreMatrix.setLowestVal(i, endColumn);
                }
            }
        }
    }

    /**
     * Fill only the cells of a matrix within the band.
     * @param nrow int number of rows in the matrix
     * @param ncol int number of columns in the matrix
     * @param bandwidth int width of the band, goes from i-bandwidth to i+bandwidth 
     *        for every i in [1,nrow].
     * @param scoreMatrix score matrix whose band boundary should be initialized
     */
    public default void fillBandedMatrix(int nrow,
                                         int ncol,
                                         int bandwidth,
                                         AlignmentMatrix scoreMatrix) {
        initBandedMatrix(nrow, ncol, bandwidth, scoreMatrix);
        for (int i = 1; i < nrow; i++) {
            int firstColumn = firstBandColumn(i, ncol, bandwidth);
            int endColumn = endBandColumn(i, ncol, bandwidth);
            for (int j = firstColumn; j < endColumn; j++) {
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
