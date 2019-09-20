package jseqa.alignment;

import static org.junit.Assert.*;

import org.junit.Test;

import jseqa.util.Pair;

public class AlignmentMatrixScoreTest {

    @Test
    public void testGetMaxCell() {
        int nrow = 4;
        int ncol = 4;
        AlignmentMatrixScore mat = new AlignmentMatrixScore(nrow, ncol);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                mat.set(i, j, i + j);
            }
        }
        Pair<Integer,Integer> maxCell = mat.getMaxCell(nrow - 1, ncol - 1);
        assertEquals(3, maxCell.first().intValue());
        assertEquals(3, maxCell.second().intValue());
        
        mat.set(2, 1, 1000);
        Pair<Integer,Integer> maxCell2 = mat.getMaxCell(nrow - 1, ncol - 1);
        assertEquals(2, maxCell2.first().intValue());
        assertEquals(1, maxCell2.second().intValue());
    }

}
