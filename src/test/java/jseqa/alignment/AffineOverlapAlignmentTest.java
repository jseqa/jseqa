package jseqa.alignment;

import static org.junit.Assert.*;

import org.junit.Test;

import jseqa.alignment.substitution.DnaSubstitutionMatrix;

public class AffineOverlapAlignmentTest {

    @Test
    public void testFillCell() {
        String seqA = "CTAAGGGATTCCGGTAATTAGACAG";
        String seqB = "ATAGACCATATGTCAGTGACTGTGTAA";


        Alignment aln = FullAlignments.affineOverlapAlign(seqA, seqB, new DnaSubstitutionMatrix(-10, -1));
        assertEquals("-----------CTAAGGGATTCCG-GTAATTAGACAG", aln.top());
        assertEquals("ATAGACCATATGTCAGTGA--CTGTGTAA--------", aln.bottom());
        assertEquals(18, aln.score());
    }

}
