package jseqa.alignment;

/**
 * Populates the banded diagonal of an N x M matrix, to be used as a mixin.
 * <br>
 * For example a band of 2 in the matrix below would fill in cells marked with #.
 * <pre>{@code  
 * 	---------
 * 	|##
 * 	|####
 * 	| ####
 * 	|  ####
 * 	|   ####
 * 
 * }</pre>
 *
 */
public interface BandedMatrixFiller {

	/**
	 * Fill only the cells of a matrix within the band.
	 * @param nrow int number of rows in the matrix
	 * @param ncol int number of columns in the matrix
	 * @param bandwidth int width of the band, goes from i-bandwidth to i+bandwidth for every i in [1,nrow].
	 */
	public default void fillBandedMatrix(int nrow, int ncol, int bandwidth) {
		for(int i = 1; i < nrow; i++) {
			for(int j = i-bandwidth; j < i+bandwidth; j++) {
				if(j > 0 && j < ncol) {
					fillCell(i,j);
				}
			}
		}
	}
	
	/**
	 * Fill cell [i,j] of the dynamic programming matrix
	 * @param i int row index of cell to fill
	 * @param j int column index of cell to fill
	 */
	abstract public void fillCell(int i, int j);
	
}
