package jseqa.alignment;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AffineAlignmentMatrixBacktrackTest {

    AffineAlignmentMatrixBacktrack scoreMat;
    String diagMat = "D,D,D\n"
            + "D,D,D\n"
            + "D,D,D\n";
    String insertMat = "D,D,D\n"
            + "D,D,U\n"
            + "D,D,D\n";
    String delMat = "D,D,D\n"
            + "D,D,D\n"
            + "D,D,L\n";
    
    @Before
    public void init() {
        scoreMat = new AffineAlignmentMatrixBacktrack(3, 3);
        scoreMat.setDiagonal(1, 1, BacktrackPtr.DIAG);
        scoreMat.setLeft(2, 2, BacktrackPtr.LEFT);
        scoreMat.setUp(1, 2, BacktrackPtr.UP);
    }
    
    @Test
    public void testClearMatrix() {
        scoreMat.clearMatrix();
        assertEquals(BacktrackPtr.DIAG, scoreMat.getDiagonal(1, 2));
    }

    @Test
    public void testClearCell() {
        scoreMat.clearCell(1, 2);
        assertEquals(BacktrackPtr.DIAG, scoreMat.getDiagonal(1, 2));
    }

    @Test
    public void testSetLowestVal() {
        scoreMat.setLowestVal(1, 1);
        assertEquals(BacktrackPtr.FREERIDE, scoreMat.getDiagonal(1, 1)); 
    }

    @Test
    public void testToString() {
        String expStr = diagMat + "\n" + insertMat + "\n" + delMat;
        assertEquals(expStr, scoreMat.toString());
    }

    @Test
    public void testToStringIntInt() {
        String expStr = diagMat + "\n" + insertMat + "\n" + delMat;
        assertEquals(expStr, scoreMat.toString(3, 3));
    }

//    @Test
//    public void testCellToString() {
//        fail("Not yet implemented");
//    }

//    @Test
//    public void testAffineAlignmentMatrixBacktrack() {
//        fail("Not yet implemented");
//    }

    @Test
    public void testSetDiagonal() {
        scoreMat.setDiagonal(0, 0, BacktrackPtr.FREERIDE);
        assertEquals(BacktrackPtr.FREERIDE, scoreMat.getDiagonal(0, 0));
    }

    @Test
    public void testSetInsertion() {
        scoreMat.setInsertion(0, 1, BacktrackPtr.FREERIDE);
        assertEquals(BacktrackPtr.FREERIDE, scoreMat.getInsertion(0, 1));
    }

    @Test
    public void testSetDeletion() {
        scoreMat.setDeletion(1, 0, BacktrackPtr.FREERIDE);
        assertEquals(BacktrackPtr.FREERIDE, scoreMat.getDeletion(1, 0));
    }
//
//    @Test
//    public void testSetUp() {
//        fail("Not yet implemented");
//    }
//
//    @Test
//    public void testSetLeft() {
//        fail("Not yet implemented");
//    }
//
//    @Test
//    public void testGetDiagonal() {
//        fail("Not yet implemented");
//    }
//
//    @Test
//    public void testGetInsertion() {
//        fail("Not yet implemented");
//    }
//
//    @Test
//    public void testGetDeletion() {
//        fail("Not yet implemented");
//    }

    @Test
    public void testToStringDiagonal() {
        assertEquals(diagMat, scoreMat.toStringDiagonal());
    }

    @Test
    public void testToStringInsertion() {
        assertEquals(insertMat, scoreMat.toStringInsertion());
    }

    @Test
    public void testToStringDeletion() {
        assertEquals(delMat, scoreMat.toStringDeletion());
    }

}
