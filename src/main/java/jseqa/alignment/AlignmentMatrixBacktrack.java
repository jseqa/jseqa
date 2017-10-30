package jseqa.alignment;

/**
 * Matrix of alignment backtracking pointers.
 *
 */
public class AlignmentMatrixBacktrack extends AbstractAlignmentMatrix {

//	private BacktrackPtr[][] matrix;
	private BacktrackPtr[] matrix;
	
	public AlignmentMatrixBacktrack(int nrow, int ncol) {
		super(nrow, ncol);
//		this.matrix = new BacktrackPtr[this.numRows][this.numCols];
		this.matrix = new BacktrackPtr[this.numRows*this.numCols];
		this.clearMatrix();
	}
	
	@Override
	public void clearCell(int i, int j) {
//		this.matrix[i][j] = BacktrackPtr.DIAG;
		this.matrix[i*this.numCols + j] = BacktrackPtr.DIAG;
	}

	@Override
	public void setLowestVal(int i, int j) {
//		this.matrix[i][j] = BacktrackPtr.FREERIDE;
		this.set(i, j, BacktrackPtr.FREERIDE);
	}
	
	public BacktrackPtr get(int i, int j) {
//		return this.matrix[i][j];
		return this.matrix[i*this.numCols + j];
	}
	
	public void set(int i, int j, BacktrackPtr ptr) {
//		this.matrix[i][j] = ptr;
		this.matrix[i*this.numCols + j] = ptr;
	}

	@Override
	public String cellToString(int i, int j) {
//		return this.matrix[i][j].toString();
		return this.get(i, j).toString();
	}

}
