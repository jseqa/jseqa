package jseqa.alignment;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AffineAlignmentMatrixScoreTest {

    AffineAlignmentMatrixScore scoreMat;
    String diagMat = "0,0,0\n"
            + "0,1,0\n"
            + "0,0,0\n";
    String insertMat = "0,0,0\n"
            + "0,0,3\n"
            + "0,0,0\n";
    String delMat = "0,0,0\n"
            + "0,0,0\n"
            + "0,0,-2\n";
    
    @Before
    public void init() {
        scoreMat = new AffineAlignmentMatrixScore(3, 3);
        scoreMat.setDiagonal(1, 1, 1);
        scoreMat.setLeft(2, 2, -2);
        scoreMat.setUp(1, 2, 3);
    }
    
    @Test
    public void testClearMatrix() {
        scoreMat.clearMatrix();
        assertEquals(0, scoreMat.getDiagonal(1, 1));
    }

    @Test
    public void testClearCell() {
        scoreMat.clearCell(1, 1);
        assertEquals(0, scoreMat.getDiagonal(1, 1));
    }

    @Test
    public void testSetLowestVal() {
        scoreMat.setLowestVal(1, 1);
        assertEquals(AlignmentMatrix.MIN_VALUE, scoreMat.getDiagonal(1, 1)); 
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
//    public void testAffineAlignmentMatrixScore() {
//        fail("Not yet implemented");
//    }

//    @Test
//    public void testSetDiagonal() {
//        fail("Not yet implemented");
//    }
//
//    @Test
//    public void testSetInsertion() {
//        fail("Not yet implemented");
//    }
//
//    @Test
//    public void testSetDeletion() {
//        fail("Not yet implemented");
//    }
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
