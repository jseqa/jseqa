package jseqa.alignment;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import jseqa.alignment.substitution.DnaSubstitutionMatrix;
import jseqa.alignment.substitution.SubstitutionMatrix;

public class AffineLocalAlignmentIT {

    private static final int NUM_ALIGNMENTS = 500;
    private static final int BANDWIDTH = 5;
    private static final int WARMUP_ALIGNMENTS = 20;

    // IGHV1S13*01
    private static final String V13 =
            "CAGGAGCAGCTGGAGGAATCCGGGGGAGGCCTGGTCACGCCTGGAGGAACCCTGACACTC"
            + "ACCTGCACAGTCTCTGGATTCTCCCTCAGTAGCTATGGAGTAAGCTGGGTCCGCCAGGCT"
            + "GCAGGGAAGGGGCTGGAATGGATCGGATACATTAGTAGTAGTGGTAGCGCATACTACGCG"
            + "AGTTGGGTAAATGGTCGATTCACCATCTCCAAAACCTCGAGCACGGTGGATCTGAAAATG"
            + "ACCAGTCTGAGAGCCGCGGACACGGCCACCTATTTCTGTGCCAGAAA";

    @Test
    public void testBandedAndFullAffineLocalPerformance() {
        int matrixSize = V13.length() + 2;
        SubstitutionMatrix subMat = new DnaSubstitutionMatrix(-10);
        List<String> mutatedSequences = GlobalAlignmentsIT.mutateSeq(
                V13, NUM_ALIGNMENTS, 0);

        AffineAlignmentMatrixScore fullScores =
                new AffineAlignmentMatrixScore(matrixSize, matrixSize);
        AffineAlignmentMatrixBacktrack fullPointers =
                new AffineAlignmentMatrixBacktrack(matrixSize, matrixSize);
        AffineAlignmentMatrixScore bandedScores =
                new AffineAlignmentMatrixScore(matrixSize, matrixSize);
        AffineAlignmentMatrixBacktrack bandedPointers =
                new AffineAlignmentMatrixBacktrack(matrixSize, matrixSize);

        for (int i = 0; i < WARMUP_ALIGNMENTS; i++) {
            String mutatedSequence = mutatedSequences.get(i);
            FullAlignments.affineLocalAlignNt(V13, mutatedSequence,
                    fullScores, fullPointers, subMat);
            BandedAlignments.affineLocal(V13, mutatedSequence,
                    bandedScores, bandedPointers, subMat, BANDWIDTH);
        }

        Alignment[] fullAlignments = new Alignment[NUM_ALIGNMENTS];
        long fullStart = System.nanoTime();
        for (int i = 0; i < NUM_ALIGNMENTS; i++) {
            fullAlignments[i] = FullAlignments.affineLocalAlignNt(
                    V13, mutatedSequences.get(i), fullScores, fullPointers, subMat);
        }
        long fullNanos = System.nanoTime() - fullStart;

        Alignment[] bandedAlignments = new Alignment[NUM_ALIGNMENTS];
        long bandedStart = System.nanoTime();
        for (int i = 0; i < NUM_ALIGNMENTS; i++) {
            bandedAlignments[i] = BandedAlignments.affineLocal(
                    V13, mutatedSequences.get(i), bandedScores, bandedPointers,
                    subMat, BANDWIDTH);
        }
        long bandedNanos = System.nanoTime() - bandedStart;

        for (int i = 0; i < NUM_ALIGNMENTS; i++) {
            assertEquals(fullAlignments[i].score(), bandedAlignments[i].score());
            assertEquals(fullAlignments[i].numColumns(), bandedAlignments[i].numColumns());
            assertEquals(fullAlignments[i].numMatches(), bandedAlignments[i].numMatches());
            assertEquals(fullAlignments[i].toString(), bandedAlignments[i].toString());
        }

        double fullMillis = fullNanos / 1_000_000.0;
        double bandedMillis = bandedNanos / 1_000_000.0;
        double speedup = fullNanos / (double) bandedNanos;
        System.out.printf(
                "Affine local alignment (%d calls): full=%.3f ms, banded=%.3f ms, speedup=%.2fx%n",
                NUM_ALIGNMENTS, fullMillis, bandedMillis, speedup);
    }
}
