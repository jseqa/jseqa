package jseqa.alignment;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

import jseqa.alignment.substitution.DnaSubstitutionMatrix;
import jseqa.alignment.substitution.SubstitutionMatrix;

public class GlobalAlignmentsIT {


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
			Alignment affAln = FullAlignments.affineGlobalAlignNt(v13, mutV13.toString(), affineAlignScoreMat, affineAlignPtrMat, subMat);
			Alignment simpAln = FullAlignments.globalAlignNt(v13, mutV13.toString(), alignScoreMat, alignPtrMat, subMat);
			
			assertFalse(simpAln == affAln);
			assertEquals(simpAln.score(), affAln.score());
			assertEquals(simpAln.numColumns(), affAln.numColumns());
			assertEquals(simpAln.numMatches(), affAln.numMatches());
		}
	}
	
	public static List<String> mutateSeq(String seq, int numIter) {
		return GlobalAlignmentsIT.mutateSeq(seq, numIter, 0);
	}
	
	public static List<String> mutateSeq(String seq, int numIter, long rngSeed) {
		Map<Character,char[]> subLists = new HashMap<Character,char[]>();
		char[] l1 = {'C', 'G', 'T'};
		subLists.put('A', l1);
		//
		char[] l2 = {'A', 'G', 'T'};
		subLists.put('C', l2);
		//
		char[] l3 = {'A', 'C', 'T'};
		subLists.put('G', l3);
		//
		char[] l4 = {'A', 'C', 'G'};
		subLists.put('T', l4);

		int len = seq.length();
		Random rand = new Random(rngSeed);
		return IntStream.range(0, numIter)
				.mapToObj(i -> {
					int pos = rand.nextInt(len);
					StringBuilder mutV13 = new StringBuilder(seq);
					// mutate by 1
					char germlineCh = seq.charAt(pos);
					int randIdx = rand.nextInt(3);
					char mutCh = subLists.get(germlineCh)[randIdx];
					mutV13.setCharAt(pos, mutCh);
					return mutV13.toString();
				})
				.collect(Collectors.toCollection(ArrayList::new));
	}
}
