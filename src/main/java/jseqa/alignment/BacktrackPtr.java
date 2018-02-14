package jseqa.alignment;

/**
 * The different allowed backtracking pointers for alignments.
 *
 */
public enum BacktrackPtr {
    
    /** Diagonal, i.e., match/mismatch. */
    DIAG,
    
    /** Left, i.e., insertion. */
    LEFT,
    
    /** Right, i.e., deletion. */
    UP,
    
    /** Free-ride to origin. */
    FREERIDE;

    @Override
    public String toString() {
        if (super.equals(BacktrackPtr.DIAG)) {
            return "D";
        } else if (super.equals(BacktrackPtr.LEFT)) {
            return "L";
        } else if (super.equals(BacktrackPtr.UP)) {
            return "U";
        } else if (super.equals(BacktrackPtr.FREERIDE)) {
            return "F";
        } else {
            return "X";
        }
    }
}
