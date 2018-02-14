package jseqa.alignment.substitution;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BlosumSubstitutionMatrixTest {

    @Test
    public void testBlosum50() {
        BlosumSubstitutionMatrix blosum50 = new BlosumSubstitutionMatrix(BlosumMatrices.BLOSUM50, -5,-1);
        assertEquals(5, blosum50.score('A', 'A'));
        assertEquals(-2, blosum50.score('A', 'R'));
        assertEquals(-2, blosum50.score('R', 'A'));

        assertEquals(0, blosum50.score('G', 'A'));
        assertEquals(-3, blosum50.score('G', 'Y'));
        assertEquals(-5, blosum50.score('G', '*'));

        BlosumSubstitutionMatrix blosum50v2 = BlosumSubstitutionMatrix.BLOSUM50;
        assertEquals(blosum50.score('A', 'A'), blosum50v2.score('A', 'A')); 
        assertEquals(blosum50.score('A', 'R'), blosum50v2.score('A', 'R')); 
        assertEquals(blosum50.score('G', 'A'), blosum50v2.score('G', 'A')); 
        assertEquals(blosum50.score('L', 'V'), blosum50v2.score('L', 'V')); 
        assertEquals(blosum50.score('R', 'Y'), blosum50v2.score('R', 'Y'));
    }

    @Test
    public void testBlosum62() {
        BlosumSubstitutionMatrix blosum62 = new BlosumSubstitutionMatrix(BlosumMatrices.BLOSUM62, -5,-1);
        assertEquals(-3, blosum62.score('C', 'R'));
        assertEquals(5, blosum62.score('Q', 'Q'));
        assertEquals(-1, blosum62.score('M', 'S'));

        assertEquals(-4, blosum62.score('W', 'N'));
        assertEquals(-4, blosum62.score('W', 'P'));
        assertEquals(-4, blosum62.score('F', '*'));
        assertEquals(-4, blosum62.score('*', 'G'));
    }

}
