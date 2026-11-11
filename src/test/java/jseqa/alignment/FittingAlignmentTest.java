package jseqa.alignment;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jseqa.alignment.substitution.DnaSubstitutionMatrix;

public class FittingAlignmentTest {

    @Test
    public void testSimpleFit() {
        String a = "GGGGGGGAAATTTCCCCCCCCCCC";
        String b =        "AAATTTC";
        int len = a.length()+5;
        FittingAlignment galn = new FittingAlignment(b, a, new DnaSubstitutionMatrix(-5));
        galn.setAlignmentMatrix(new AlignmentMatrixScore(len, len), new AlignmentMatrixBacktrack(len, len));
        galn.initMatrix();
        galn.fillMatrix();
        Alignment aln = galn.backtrack();
        assertEquals(7*5, aln.score());
        assertEquals("-------"+b, aln.top());
        assertEquals("GGGGGGG"+b, aln.bottom());
    }

    @Test
    public void testSimpleFit2() {
        String seqA = "AAAAACCCCTTTTTTT";
        String seqB =        "CCCC";
        Alignment aln = FullAlignments.fittingAlign(seqB, seqA, new DnaSubstitutionMatrix(-5));
        assertEquals(20, aln.score());
    }

    @Test
    public void testSimple() {
        String a = "AAATTT";
        String b = "AATC";
        int len = a.length();
        FittingAlignment galn = new FittingAlignment(b, a, new DnaSubstitutionMatrix(-5));
        galn.setAlignmentMatrix(new AlignmentMatrixScore(len, len), new AlignmentMatrixBacktrack(len, len));
        galn.initMatrix();
        galn.fillMatrix();
        Alignment aln = galn.backtrack();
        assertEquals(11, aln.score());
    }

    @Test
    public void testBandedBacktrackIgnoresScoresOutsideBand() {
        String larger = "GGGGGGGAAATTTCCCCCCCCCCC";
        String smaller = "AAATTTC";
        int size = larger.length() + 5;

        FittingAlignment fresh = new FittingAlignment(smaller, larger,
                new DnaSubstitutionMatrix(-5));
        fresh.setAlignmentMatrix(new AlignmentMatrixScore(size, size),
                new AlignmentMatrixBacktrack(size, size));
        fresh.initMatrix();
        fresh.bandedFillMatrix(10);
        Alignment expected = fresh.backtrack(10);

        AlignmentMatrixScore reusedScores = new AlignmentMatrixScore(size, size);
        for (int i = 0; i <= smaller.length(); i++) {
            for (int j = 0; j <= larger.length(); j++) {
                reusedScores.set(i, j, 100000);
            }
        }
        FittingAlignment reused = new FittingAlignment(smaller, larger,
                new DnaSubstitutionMatrix(-5));
        reused.setAlignmentMatrix(reusedScores,
                new AlignmentMatrixBacktrack(size, size));
        reused.initMatrix();
        reused.bandedFillMatrix(10);
        Alignment actual = reused.backtrack(10);

        assertEquals(expected.toString(), actual.toString());
        assertEquals(expected.score(), actual.score());
    }

}
