package jseqa.sequence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class AaSequenceTest {

    String myoglobAa = "MGLSDGEWQLVLNVWGKVEADIPGHGQEVLIRLFKGHPETLEKFDKFKHLKSEDEMKASEDLKKHGATVLTALGGILKKKGHHEAEIKPLAQSHATKHKIPVKYLEFISECIIQVLQSKHPGDFGADAQGAMNKALELFRKDMASNYKELGFQG";
    AaSequence myo = null;
    private static final double EPSILON = 0.0000001;

    @Before
    public void init() {
        myo = new AaSequence(myoglobAa);
    }

    @Test
    public void testAaSequence() {

    }

    @Test
    public void testContainsStop() {
        AaSequence seq1 = new AaSequence(new NtSequence("TTTTGA").translateFrame(0).toString());
        assertTrue(seq1.containsStop());
        assertFalse(myo.containsStop());
    }

    @Test
    public void testMolecularWeight() {
        assertEquals(17172.95, myo.molecularWeight(), 0.01);
    }

}
