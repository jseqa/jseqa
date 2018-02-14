package jseqa.alignment;

/**
 * Alignment matrix interface, either for scoring or backtracking pointers.
 *
 */
public interface AlignmentMatrix {

    public void clearMatrix();

    public void clearCell(int i, int j);

    public void setLowestVal(int i, int j);

    public int nrows();

    public int ncols();

    public static final int MIN_VALUE = -10000000;
}
