package jseqa.alignment;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import jseqa.alignment.substitution.DnaSubstitutionMatrix;
import jseqa.alignment.substitution.SubstitutionMatrix;

public class BandedAlignmentsTest {
    
    String seqA = "AAATTT";
    String seqB =  "AATC";
    SubstitutionMatrix subMat;
    AlignmentMatrixScore scoreMat;
    AlignmentMatrixBacktrack ptrMat;
    
    AffineAlignmentMatrixScore affScoreMat;
    AffineAlignmentMatrixBacktrack affPtrMat;
    
    @Before
    public void init() {
        subMat = new DnaSubstitutionMatrix(-5);
        scoreMat = new AlignmentMatrixScore(10,10);
        ptrMat = new AlignmentMatrixBacktrack(10,10);
        
        affScoreMat = new AffineAlignmentMatrixScore(10,10);
        affPtrMat = new AffineAlignmentMatrixBacktrack(10,10);
    }
    
    @Test
    public void testGlobalBasicStringStringSubstitutionMatrixInt() {
        Alignment expAln = FullAlignments.globalAlignNt(seqA, seqB, subMat);
        Alignment bandAln = BandedAlignments.globalBasic(seqA, seqB, subMat, 10);
        assertEquals(expAln.toString(), bandAln.toString());
    }

    @Test
    public void testGlobalAffineNt() {
        Alignment expAln = FullAlignments.affineGlobalAlignNt(seqA, seqB, subMat);
        Alignment bandAln = BandedAlignments.affineGlobalNt(seqA, seqB, subMat, 10);
        assertEquals(expAln.toString(), bandAln.toString());
    }

    @Test
    public void testGlobalAffine() {
        Alignment expAln = FullAlignments.affineGlobalAlignNt(seqA, seqB, subMat);
        Alignment bandAln = BandedAlignments.affineGlobal(seqA, seqB, affScoreMat, affPtrMat, subMat, 10);
        assertEquals(expAln.toString(), bandAln.toString());
    }

    @Test
    public void testAffineLocal() {
        Alignment expAln = FullAlignments.affineLocalAlignNt(seqA, seqB, subMat);
        Alignment bandAln = BandedAlignments.affineLocalNt(seqA, seqB, subMat, 10);
        assertEquals(expAln.toString(), bandAln.toString());
        assertEquals(expAln.score(), bandAln.score());
    }

    @Test
    public void testAffineLocalBacktrackIgnoresScoresOutsideBand() {
        for (int i = 0; i <= seqA.length(); i++) {
            for (int j = 0; j <= seqB.length(); j++) {
                affScoreMat.setDiagonal(i, j, 100000);
                affScoreMat.setInsertion(i, j, 100000);
                affScoreMat.setDeletion(i, j, 100000);
            }
        }

        Alignment expected = BandedAlignments.affineLocalNt(seqA, seqB, subMat, 2);
        Alignment actual = BandedAlignments.affineLocal(seqA, seqB,
                affScoreMat, affPtrMat, subMat, 2);
        assertEquals(expected.toString(), actual.toString());
        assertEquals(expected.score(), actual.score());
    }

    @Test
    public void testBasicAlignmentsIgnoreStaleScoresOutsideBand() {
        for (int i = 0; i <= seqA.length(); i++) {
            for (int j = 0; j <= seqB.length(); j++) {
                scoreMat.set(i, j, 100000);
            }
        }

        Alignment expectedGlobal = BandedAlignments.globalBasic(seqA, seqB, subMat, 3);
        Alignment actualGlobal = BandedAlignments.globalBasic(seqA, seqB,
                scoreMat, ptrMat, subMat, 3);
        assertEquals(expectedGlobal.toString(), actualGlobal.toString());

        Alignment expectedLocal = BandedAlignments.localBasic(seqA, seqB, subMat, 2);
        Alignment actualLocal = BandedAlignments.localBasic(seqA, seqB,
                scoreMat, ptrMat, subMat, 2);
        assertEquals(expectedLocal.toString(), actualLocal.toString());
    }

    @Test
    public void testAffineGlobalIgnoresStaleScoresOutsideBand() {
        for (int i = 0; i <= seqA.length(); i++) {
            for (int j = 0; j <= seqB.length(); j++) {
                affScoreMat.setDiagonal(i, j, 100000);
                affScoreMat.setInsertion(i, j, 100000);
                affScoreMat.setDeletion(i, j, 100000);
            }
        }

        Alignment expected = BandedAlignments.affineGlobalNt(seqA, seqB, subMat, 3);
        Alignment actual = BandedAlignments.affineGlobal(seqA, seqB,
                affScoreMat, affPtrMat, subMat, 3);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGlobalRejectsBandWithoutTerminalCell() {
        BandedAlignments.globalBasic("AAAAAA", "A", subMat, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRejectsZeroBandwidth() {
        BandedAlignments.affineLocalNt(seqA, seqB, subMat, 0);
    }

    @Test
    public void testLocalBasicStringStringSubstitutionMatrixInt() {
        Alignment expAln = FullAlignments.affineLocalAlignNt(seqA, seqB, subMat);
        Alignment bandAln = BandedAlignments.localBasic(seqA, seqB, subMat, 10);
        assertEquals(expAln.toString(), bandAln.toString());
    }

    @Test
    public void testLocalBasicStringStringAlignmentMatrixScoreAlignmentMatrixBacktrackSubstitutionMatrixInt() {
        Alignment expAln = FullAlignments.affineLocalAlignNt(seqA, seqB, subMat);
        Alignment bandAln = BandedAlignments.localBasic(seqA, seqB, scoreMat, ptrMat, subMat, 10);
        assertEquals(expAln.toString(), bandAln.toString());
    }

}
