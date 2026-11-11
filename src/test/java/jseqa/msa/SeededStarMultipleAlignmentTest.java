package jseqa.msa;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import jseqa.alignment.substitution.CustomDnaSubstitutionMatrix;

public class SeededStarMultipleAlignmentTest {

    @Test
    public void testBuildMsaWithExplicitGuide() {
        String guide = "ACGTACGT";
        List<String> sequences = Arrays.asList(
                "ACGTTCGT",
                "ACGACGT",
                "ACGTTACGT");

        SeededStarMultipleAlignment seededMsa = new SeededStarMultipleAlignment(
                guide,
                sequences,
                new CustomDnaSubstitutionMatrix(5, -4, -10, -1));

        MultipleAlignment msa = seededMsa.buidMsa();

        assertEquals(sequences.size() + 1, msa.numRows());
        assertEquals(guide, removeGaps(msa.getRowSequence(0)));
        for (int i = 0; i < sequences.size(); i++) {
            assertEquals(sequences.get(i), removeGaps(msa.getRowSequence(i + 1)));
        }
        for (int i = 0; i < msa.numRows(); i++) {
            assertEquals(msa.numCols(), msa.getRowSequence(i).length());
        }
    }

    private static String removeGaps(String sequence) {
        return sequence.replace("-", "");
    }
}
