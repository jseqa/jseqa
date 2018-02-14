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
