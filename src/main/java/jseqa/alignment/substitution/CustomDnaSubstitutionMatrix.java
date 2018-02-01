package jseqa.alignment.substitution;

/**
 * Creates a custom scoring matrix for nucleotides, where the diagonal (i.e., matches) 
 * are all one value, and off-diagonals (i.e., mismatches) are all another, user-defined, value.
 *
 */
public class CustomDnaSubstitutionMatrix extends DnaSubstitutionMatrix {

    public CustomDnaSubstitutionMatrix(int match, int mismatch, int gap, int extension) {
        super(gap, extension);
        this.initFlatSparceMatrix(match, mismatch, match / 2, -1);
    }
}
