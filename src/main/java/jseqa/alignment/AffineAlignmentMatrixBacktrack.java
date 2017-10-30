package jseqa.alignment;

/**
 * Alignment backtracking matrix for affine gap penalties, uses three matrices. 
 *
 */
public class AffineAlignmentMatrixBacktrack extends AbstractAlignmentMatrix {

	/** Diagonal matrix */
	protected AlignmentMatrixBacktrack diagMatrix;
	
	/** Insertion matrix (UP) */
	protected AlignmentMatrixBacktrack insMatrix;
	
	/** Deletion matrix (LEFT) */
	protected AlignmentMatrixBacktrack delMatrix;
	
	public AffineAlignmentMatrixBacktrack(int nrow, int ncol) {
		super(nrow, ncol);
		this.diagMatrix = new AlignmentMatrixBacktrack(nrow, ncol);
		this.insMatrix = new AlignmentMatrixBacktrack(nrow, ncol);
		this.delMatrix = new AlignmentMatrixBacktrack(nrow, ncol);
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

	public void setDiagonal(int i, int j, BacktrackPtr ptr) {
		this.diagMatrix.set(i, j, ptr);
	}
	
	public void setInsertion(int i, int j, BacktrackPtr ptr) {
		this.insMatrix.set(i, j, ptr);
	}
	
	public void setDeletion(int i, int j, BacktrackPtr ptr) {
		this.delMatrix.set(i, j, ptr);
	}
	
	/** Set insertion */
	public void setUp(int i, int j, BacktrackPtr ptr) {
		this.insMatrix.set(i, j, ptr);
	}
	
	/** Set deletion */
	public void setLeft(int i, int j, BacktrackPtr ptr) {
		this.delMatrix.set(i, j, ptr);
	}
	
	public BacktrackPtr getDiagonal(int i, int j) {
		return this.diagMatrix.get(i, j);
	}
	
	public BacktrackPtr getInsertion(int i, int j) {
		return this.insMatrix.get(i, j);
	}
	
	public BacktrackPtr getDeletion(int i, int j) {
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
		return this.toStringDiagonal() + "\n" + this.toStringInsertion() + "\n" + this.toStringDeletion();
	}
	
	public String toString(int maxRow, int maxCol) {
		return this.diagMatrix.toString(maxRow, maxCol) + "\n" 
				+ this.insMatrix.toString(maxRow, maxCol) + "\n" 
				+ this.delMatrix.toString(maxRow, maxCol);
	}
}
