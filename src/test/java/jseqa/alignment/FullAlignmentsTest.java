package jseqa.alignment;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import jseqa.alignment.substitution.DnaSubstitutionMatrix;
import jseqa.alignment.substitution.SubstitutionMatrix;

public class FullAlignmentsTest {
    
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
    public void testGlobalAlignScore() {
        int score = FullAlignments.globalAlignScore(seqA, seqB, scoreMat, ptrMat, subMat);
        assertEquals(1, score);
    }

    @Test
    public void testGlobalAlign() {
        int score = FullAlignments.globalAlign(seqA, seqB, subMat).score();
        assertEquals(1, score);
    }

    @Test
    public void testGlobalAlignNtStringStringSubstitutionMatrix() {
        int score = FullAlignments.globalAlignNt(seqA, seqB, subMat).score();
        assertEquals(1, score);
    }

    @Test
    public void testGlobalAlignNtStringStringAlignmentMatrixScoreAlignmentMatrixBacktrackSubstitutionMatrix() {
        int score = FullAlignments.globalAlignNt(seqA, seqB, scoreMat, ptrMat, subMat).score();
        assertEquals(1, score);
    }

    @Test
    public void testLocalAlignScore() {
        int score = FullAlignments.localAlignScore(seqA, seqB, scoreMat, ptrMat, subMat);
        assertEquals(5*3, score);
    }

    @Test
    public void testLocalAlign() {
        int score = FullAlignments.localAlign(seqA, seqB, subMat).score();
        assertEquals(5*3, score);
    }

    @Test
    public void testLocalAlignNtStringStringSubstitutionMatrix() {
        int score = FullAlignments.localAlignNt(seqA, seqB, subMat).score();
        assertEquals(5*3, score);
    }

    @Test
    public void testLocalAlignNtStringStringAlignmentMatrixScoreAlignmentMatrixBacktrackSubstitutionMatrix() {
        int score = FullAlignments.localAlignNt(seqA, seqB, scoreMat, ptrMat, subMat).score();
        assertEquals(5*3, score);
    }

    @Test
    public void testFittingAlignStringStringSubstitutionMatrix() {
        int score = FullAlignments.fittingAlign(seqB, seqA, subMat).score();
        assertEquals(5*3-4, score);
    }

    @Test
    public void testFittingAlignStringStringAlignmentMatrixScoreAlignmentMatrixBacktrackSubstitutionMatrix() {
        int score = FullAlignments.fittingAlign(seqB, seqA, scoreMat, ptrMat, subMat).score();
        assertEquals(5*3-4, score);
    }

    @Test
    public void testAffineGlobalAlignStringStringSubstitutionMatrix() {
        int score = FullAlignments.affineGlobalAlign(seqA, seqB, subMat).score();
        assertEquals(1, score);
    }

    @Test
    public void testAffineGlobalAlignStringStringAffineAlignmentMatrixScoreAffineAlignmentMatrixBacktrackSubstitutionMatrix() {
        int score = FullAlignments.affineGlobalAlign(seqA, seqB, affScoreMat, affPtrMat, subMat).score();
        assertEquals(1, score);
    }

    @Test
    public void testAffineLocalAlignStringStringAffineAlignmentMatrixScoreAffineAlignmentMatrixBacktrackSubstitutionMatrix() {
        int score = FullAlignments.affineLocalAlign(seqA, seqB, affScoreMat, affPtrMat, subMat).score();
        assertEquals(5*3, score);
    }

    @Test
    public void testAffineLocalAlignStringStringSubstitutionMatrix() {
        int score = FullAlignments.affineLocalAlign(seqA, seqB, subMat).score();
        assertEquals(5*3, score);
    }

    @Test
    public void testAffineOverlapAlignStringStringSubstitutionMatrix() {
        int score = FullAlignments.affineOverlapAlign(seqA, seqB, subMat).score();
        assertEquals(11, score);
    }

    @Test
    public void testAffineOverlapAlignStringStringAffineAlignmentMatrixScoreAffineAlignmentMatrixBacktrackSubstitutionMatrix() {
        int score = FullAlignments.affineOverlapAlign(seqA, seqB, affScoreMat, affPtrMat, subMat).score();
        assertEquals(5*3-4, score);
    }

    @Test
    public void testAffineGlobalAlignScore() {
        int score = FullAlignments.affineGlobalAlignScore(seqA, seqB, affScoreMat, affPtrMat, subMat);
        assertEquals(1, score);
    }

    @Test
    public void testAffineGlobalAlignNtStringStringSubstitutionMatrix() {
        int score = FullAlignments.affineGlobalAlignNt(seqA, seqB, subMat).score();
        assertEquals(1, score);
    }

    @Test
    public void testAffineGlobalAlignNtStringStringAffineAlignmentMatrixScoreAffineAlignmentMatrixBacktrackSubstitutionMatrix() {
        int score = FullAlignments.affineGlobalAlignNt(seqA, seqB, affScoreMat, affPtrMat, subMat).score();
        assertEquals(1, score);
    }

    @Test
    public void testAffineLocalAlignNtStringStringSubstitutionMatrix() {
        int score  = FullAlignments.affineLocalAlignNt(seqA, seqB, subMat).score();
        assertEquals(5*3, score);
    }

    @Test
    public void testAffineLocalAlignNtStringStringAffineAlignmentMatrixScoreAffineAlignmentMatrixBacktrackSubstitutionMatrix() {
        int score = FullAlignments.affineLocalAlignNt(seqA, seqB, affScoreMat, affPtrMat, subMat).score();
        assertEquals(5*3, score);
    }

}
