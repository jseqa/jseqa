package jseqa.alignment.substitution;

/**
 * Abstract class representing a nucleotide substitution matrix. Uses a sparse, flat,
 * matrix to access the substitution values.
 *
 */
public abstract class AbstractNtSubstitutionMatrix extends AbstractSubstitutionMatrix {

    /** Sparse flat matrix representation. */
    private int[] flatSparceMatrix;

    /** Nucleotide alphabet allowed. */
    private char[] nucs = {'A', 'C', 'G', 'T', 'N'};

    public AbstractNtSubstitutionMatrix() {
        this(-5, -1);
    }

    /**
     * Constructor that uses default substitution values of 5/-4 for non-N nts.
     * @param gapPenal
     * @param gapExtendPenal
     */
    public AbstractNtSubstitutionMatrix(int gapPenal, int gapExtendPenal) {
        super(gapPenal, gapExtendPenal);
        this.flatSparceMatrix = new int[128 * 128];
        this.initFlatSparceMatrix(5, -4, -2, -1);
    }

    /**
     * Initializes flat sparse matrix with general match, mismatch penalties 
     * (i.e., diagonal/off-diagonal values).
     * @param match
     * @param mismatch
     * @param nMismatch
     * @param nMatch
     */
    protected void initFlatSparceMatrix(int match, int mismatch, int nMismatch, int nMatch) {
        for (int i = 0; i < nucs.length; i++) {
            for (int j = 0; j < nucs.length; j++) {
                int val = 0;
                if (nucs[i] == 'N' || nucs[j] == 'N') {
                    if (nucs[i] == 'N' && nucs[j] == 'N') {
                        val = nMatch;
                    } else {
                        val = nMismatch;
                    }
                } else {
                    if (nucs[i] == nucs[j]) {
                        val = match;
                    } else {
                        val = mismatch;
                    }
                }
                this.flatSparceMatrix[(nucs[i] * 128) + nucs[j]] = val;
            }
        }
    }

    @Override
    public int score(char a, char b) {
        return this.flatSparceMatrix[(a * 128) + b];
    }
}
