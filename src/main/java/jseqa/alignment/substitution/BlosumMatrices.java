package jseqa.alignment.substitution;

/**
 * Represents the available <a href="https://en.wikipedia.org/wiki/BLOSUM">BLOSUM matrices</a> for amino acid substitutions. 
 * Each substitution matrix is coarsely defined by the number X, where 
 * the constituent sequences are X% similar to one another.
 * <br>
 * Originally described in <a href="https://www.ncbi.nlm.nih.gov/pmc/articles/PMC50453/">Henikoff and Henikoff 1992</a>.
 * <br>
 * The BLOSUM matrices were obtained from <a href="ftp://ftp.ncbi.nih.gov/blast/matrices/">NCBI</a>.
 *
 */
public enum BlosumMatrices {
    
    /** BLOSUM for 50% similar sequences. */
    BLOSUM50(50),

    /** BLOSUM for 62% similar sequences; a seemingly common choice. */
    BLOSUM62(62),

    /** BLOSUM for 80% similar sequences. */
    BLOSUM80(80),

    /** BLOSUM for 90% similar sequences. */
    BLOSUM90(90);

    private int blosumNum;

    private BlosumMatrices(int matNum) {
        this.blosumNum = matNum;
    }

    /**
     * Get the number X, representing the % similarity of constituent sequences.
     * @return 
     */
    public int getBlosumNum() {
        return this.blosumNum;
    }

}
