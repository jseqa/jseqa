package jseqa.alignment;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

public class AlignmentTest {

    Alignment aln1;
    Alignment aln2;
    
    @Before
    public void init() {
        aln1 = new Alignment("ACGT", "ACGT");
        aln2 = new Alignment("ACGT", "AGGT");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor() {
        new Alignment("ACGT", "ACG");
    }
    
    @Test
    public void testNumColumns() {
        assertEquals(4, aln1.numColumns());
        assertEquals(4, aln2.numColumns());
    }

    @Test
    public void testNumMatches() {
        assertEquals(4, aln1.numMatches());
        assertEquals(3, aln2.numMatches());
    }

    @Test
    public void testPercentMatch() {
        assertEquals(1.0, aln1.percentMatch(), 0.0000001);
        assertEquals(0.75, aln2.percentMatch(), 0.0000001);
    }

}
