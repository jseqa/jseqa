package jseqa.alignment.substitution;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DnaSubstitutionMatrixTest {

    @Test
    public void testScore() {
        DnaSubstitutionMatrix dnaSub = new DnaSubstitutionMatrix(-10, -1);
        assertEquals(5, dnaSub.score('A', 'A'));
        assertEquals(5, dnaSub.score('C', 'C'));
        assertEquals(-4, dnaSub.score('T', 'A'));
        assertEquals(-4, dnaSub.score('A', 'G'));
        assertEquals(5, dnaSub.score('G', 'G'));
    }

}
