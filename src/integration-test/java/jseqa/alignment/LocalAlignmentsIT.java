package jseqa.alignment;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import jseqa.alignment.substitution.DnaSubstitutionMatrix;
import jseqa.alignment.substitution.SubstitutionMatrix;

public class LocalAlignmentsIT {

	//IGHV1S13*01
	private String v13 = "CAGGAGCAGCTGGAGGAATCCGGGGGAGGCCTGGTCACGCCTGGAGGAACCCTGACACTC" +
			"ACCTGCACAGTCTCTGGATTCTCCCTCAGTAGCTATGGAGTAAGCTGGGTCCGCCAGGCT" +
			"GCAGGGAAGGGGCTGGAATGGATCGGATACATTAGTAGTAGTGGTAGCGCATACTACGCG" +
			"AGTTGGGTAAATGGTCGATTCACCATCTCCAAAACCTCGAGCACGGTGGATCTGAAAATG" + 
			"ACCAGTCTGAGAGCCGCGGACACGGCCACCTATTTCTGTGCCAGAAA";

	@Test
	public void testCompareSimpleAndAffine() {
		int len = v13.length();
		int numIter = 20000;

		AffineAlignmentMatrixScore affineAlignScoreMat = new AffineAlignmentMatrixScore(len+2,len+2);
		AffineAlignmentMatrixBacktrack affineAlignPtrMat = new AffineAlignmentMatrixBacktrack(len+2, len+2);

		AlignmentMatrixScore alignScoreMat = new AlignmentMatrixScore(300,300);
		AlignmentMatrixBacktrack alignPtrMat = new AlignmentMatrixBacktrack(300,300);

		SubstitutionMatrix subMat = new DnaSubstitutionMatrix(-10);
		
		List<String> mutSeqs = GlobalAlignmentsIT.mutateSeq(v13, numIter);

		for(String mutV13 : mutSeqs) {
			Alignment affAln = FullAlignments.affineLocalAlignNt(v13, mutV13.toString(), affineAlignScoreMat, affineAlignPtrMat, subMat);
			Alignment simpAln = FullAlignments.localAlignNt(v13, mutV13.toString(), alignScoreMat, alignPtrMat, subMat);

			assertFalse(simpAln == affAln);
			assertEquals(simpAln.score(), affAln.score());
			assertEquals(simpAln.numColumns(), affAln.numColumns());
			assertEquals(simpAln.numMatches(), affAln.numMatches());
		}
	}

}
