package jseqa.alignment;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AlignmentBuilderTest {

    private AlignmentBuilder a0;
    
    @Before
    public void init() {
        a0 = new AlignmentBuilder();
        a0.addColumn('A', 'A');
        a0.addColumn('A', 'C');
        a0.addColumn('A', 'C');
        a0.addColumn('A', 'A');
    }
    
    @Test
    public void testAlignmentBuilder() {
        assertEquals(Integer.MIN_VALUE, a0.toAlignment().score());
    }

    @Test
    public void testGetTopRow() {
        assertEquals("AAAA", a0.getTopRow());
    }

    @Test
    public void testGetBottomRow() {
        assertEquals("ACCA", a0.getBottomRow());
    }

}
