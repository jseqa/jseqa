package jseqa.alignment;

import jseqa.util.Pair;

/**
 * Matrix of alignment scores. cell [i,j] corresponds to the alignment score from submatrix [1..i][1..j]
 *
 */
public class AlignmentMatrixScore extends AbstractAlignmentMatrix {

    //	private int[][] matrix;
    private int[] matrix;

    public AlignmentMatrixScore(int nrow, int ncol) {
        super(nrow, ncol);
        //		this.matrix = new int[this.numRows][this.numCols];
        this.matrix = new int[this.numRows * this.numCols];
        this.clearMatrix();
    }

    @Override
    public void clearCell(int i, int j) {
        //		this.matrix[i][j] = 0;
        this.set(i, j, 0);
    }

    @Override
    public void setLowestVal(int i, int j) {
        //		this.matrix[i][j] = MIN_VALUE;
        this.set(i, j, MIN_VALUE);
    }

    public int get(int i, int j) { 
        //		return this.matrix[i][j];
        return this.matrix[this.numCols*i + j];
    }

    public void set(int i, int j, int val) {
        //		this.matrix[i][j] = val;
        this.matrix[(this.numCols * i) + j] = val;
    }

    @Override
    public String cellToString(int i, int j) {
        //		return "" + this.matrix[i][j];
        return "" + this.get(i, j);
    }

    /**
     * Searches the matrix for the best scoring cell.
     * @param maxNRow
     * @param maxNCol
     * @return
     */
    public Pair<Integer,Integer> getMaxCell(int maxNRow, int maxNCol) {
        Pair<Integer,Integer> maxCell = new Pair<Integer,Integer>(maxNRow, maxNCol);
        int maxScore = this.get(maxNRow, maxNCol);
        for (int i = 0; i < maxNRow + 1; i++) {
            for (int j = 0; j < maxNCol + 1; j++) {
                if (this.get(i, j) > maxScore) {
                    maxCell = new Pair<Integer,Integer>(i, j);
                    maxScore = this.get(i, j);
                }
            }
        }
        return maxCell;
    }
}
