package jseqa.alignment.substitution;

/**
 * Uniformly penalizes all mismatches with -1, and matches with +1.
 *
 */
public class UniformNtSubstitutionMatrix extends AbstractNtSubstitutionMatrix {

    public UniformNtSubstitutionMatrix() {
        super(-1, -1);
        this.initFlatSparceMatrix(1, -1, -2, -1);
    }
}