package jseqa.msa;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import jseqa.msa.MultipleAlignment;
import jseqa.msa.MultipleAlignment.MsaCell;
import jseqa.util.Interval;

public class MultipleAlignmentTest {

    private MultipleAlignment msa;
    MultipleAlignment msa2;

    @Before
    public void init() {
        List<String> seqs = new LinkedList<String>();
        seqs.add("AACATA");
        seqs.add("AATCTG");

        msa = new MultipleAlignment(seqs);

        List<String> rows = Arrays.asList(
                "ATTGCCATT--", 
                "ATGGCCATT--", 
                "ATC-CAATTTT",
                "ATCTTC-TT--",
                "ACTGACC----");
        msa2 = new MultipleAlignment(rows);
    }

    @Test
    public void testConstruction() {
        List<MsaCell> row1 = msa2.getRow(0);
        for(int i = 0; i < row1.size(); i++) {
            assertTrue(row1.get(i).pos() < 9);
        }
        List<MsaCell> row3 = msa2.getRow(2);
        for(int i = 0; i < row3.size(); i++) {
            assertTrue(row1.get(i).pos() < 10);
        }
    }

    @Test
    public void testPrintMatrix() {
        assertEquals(6, msa.numCols());

        msa.removeMonomorphicColumns();
        assertEquals(3, msa.numCols());
    }

    @Test
    public void testCopyConst() {
        msa.removeMonomorphicColumns();
        MultipleAlignment msaCopy = new MultipleAlignment(msa);

        assertEquals(msa.numCols(), msaCopy.numCols());	
    }

    @Test
    public void testRemoveIndelColumns() {
        msa2.removeIndelColumns();
        assertEquals(5, msa2.numCols());
        assertEquals("ATTCC", msa2.getRowSequence(0));
    }

    @Test
    public void testGetTrueInterval() {
        Interval trueRow2Ival = msa2.getTrueInterval(new Interval(2,5), 2);
        assertEquals(new Interval(2,4), trueRow2Ival);
    }

    @Test
    public void testToStringFixedWidth() {
        String str = msa.toStringFixedWidth(4);
        String[] rows = str.split("\n");
        for (int i = 0; i < 3; i++) {
            assertEquals(4, rows[i].length());
        }
        for (int i = 4; i < 6; i++) {
            assertEquals(2, rows[i].length());
        }

        String str2 = msa2.toStringFixedWidth(6);
        String[] rows2 = str2.split("\n");
        for (int i = 0; i < 6; i++) {
            assertEquals(6, rows2[i].length());
        }
        for (int i = 7; i < 13; i++) {
            assertEquals(5, rows2[i].length());
        }
    }
    
    @Test
    public void testToStringWithConsnsus() {
        String expConsStr = "  .. .";
        String consStr = msa.toStringWithConsensus(true).split("\n")[2];
        assertEquals(expConsStr, consStr);
    }
}
