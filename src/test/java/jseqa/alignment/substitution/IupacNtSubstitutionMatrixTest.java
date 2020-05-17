package jseqa.alignment.substitution;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IupacNtSubstitutionMatrixTest {

    @Test
    public void testIupacMatrix() {
        IupacNtSubstitutionMatrix iupac = new IupacNtSubstitutionMatrix(-15, -1);
        assertEquals(5, iupac.score('A', 'A'));
        assertEquals(5, iupac.score('C', 'C'));
        assertEquals(5, iupac.score('G', 'G'));
        assertEquals(5, iupac.score('T', 'T'));

        assertEquals(1, iupac.score('C', 'Y'));
        assertEquals(-4, iupac.score('G', 'Y'));
        assertEquals(1, iupac.score('A', 'M'));
        assertEquals(-2, iupac.score('T', 'N'));
    }

}
