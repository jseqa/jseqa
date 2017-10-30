package jseqa.alignment;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.Test;

import jseqa.alignment.substitution.DnaSubstitutionMatrix;
import jseqa.alignment.substitution.SubstitutionMatrix;

public class AffineGlobalAlignmentIT {

	@Test
	public void testSingleMutation() {
		//IGHV1S13*01
		String v13 = "CAGGAGCAGCTGGAGGAATCCGGGGGAGGCCTGGTCACGCCTGGAGGAACCCTGACACTC" +
		"ACCTGCACAGTCTCTGGATTCTCCCTCAGTAGCTATGGAGTAAGCTGGGTCCGCCAGGCT" +
		"GCAGGGAAGGGGCTGGAATGGATCGGATACATTAGTAGTAGTGGTAGCGCATACTACGCG" +
		"AGTTGGGTAAATGGTCGATTCACCATCTCCAAAACCTCGAGCACGGTGGATCTGAAAATG" + 
		"ACCAGTCTGAGAGCCGCGGACACGGCCACCTATTTCTGTGCCAGAAA";
		
		int len = v13.length();
		Random rand = new Random(0);
		int numIter = 10000;
		
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
		
		AffineAlignmentMatrixScore alignScoreMat = new AffineAlignmentMatrixScore(300,300);
		AffineAlignmentMatrixBacktrack alignPtrMat = new AffineAlignmentMatrixBacktrack(300,300);
		SubstitutionMatrix subMat = new DnaSubstitutionMatrix(-10);
		IntStream.range(0, numIter).forEach(i -> {
			int pos = rand.nextInt(len);
			StringBuilder mutV13 = new StringBuilder(v13);
			// mutate by 1
			char germlineCh = v13.charAt(pos);
			int randIdx = rand.nextInt(3);
			char mutCh = subLists.get(germlineCh)[randIdx];
			mutV13.setCharAt(pos, mutCh);
			// now align
			Alignment aln = FullAlignments.affineGlobalAlignNt(v13, mutV13.toString(), alignScoreMat, alignPtrMat, subMat);
			// should be off by 1
			int diff = aln.numColumns() - aln.numMatches();
			assertEquals(1, diff);
		});
		
	}

}
