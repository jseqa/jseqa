package jseqa.alignment;

import static org.junit.Assert.*;

import org.junit.Test;

import jseqa.alignment.substitution.DnaSubstitutionMatrix;

public class AffineLocalAlignmentNoPtrsTest {

	@Test
	public void testSimple() {
		String a = "AAAAAATTTTT";
		String b =     "AATT";
		AffineLocalAlignmentNoPtrs galn = new AffineLocalAlignmentNoPtrs(a, b, new DnaSubstitutionMatrix(-5, -1));
		galn.setAlignmentMatrix(new AffineAlignmentMatrixScore(20,20), new AffineAlignmentMatrixBacktrack(20,20));
		galn.initMatrix();
//		System.out.println(galn.alignScoreMatrix.toString(a.length()+1, b.length()+1));
//		System.out.println(galn.alignPtrMatrix.toString(a.length()+1, b.length()+1));
		galn.fillMatrix();
//		System.out.println(galn.alignScoreMatrix.toString(a.length()+1, b.length()+1));
//		System.out.println(galn.alignPtrMatrix.toString(a.length()+1, b.length()+1));
		Alignment aln = galn.backtrack();
//		System.out.println(aln);
		assertEquals(5*4, aln.score());
		assertEquals(4, aln.numMatches());
		
		Alignment aln2 = FullAlignments.localAlignNt(a, b, new DnaSubstitutionMatrix(-5, -1));
		assertEquals(20, aln2.score());
	}
	
	@Test
	public void testRabbitVGenes() {
		//IGHV1S13*01
		String v13 = "CAGGAGCAGCTGGAGGAATCCGGGGGAGGCCTGGTCACGCCTGGAGGAACCCTGACACTC" +
		"ACCTGCACAGTCTCTGGATTCTCCCTCAGTAGCTATGGAGTAAGCTGGGTCCGCCAGGCT" +
		"GCAGGGAAGGGGCTGGAATGGATCGGATACATTAGTAGTAGTGGTAGCGCATACTACGCG" +
		"AGTTGGGTAAATGGTCGATTCACCATCTCCAAAACCTCGAGCACGGTGGATCTGAAAATG" + 
		"ACCAGTCTGAGAGCCGCGGACACGGCCACCTATTTCTGTGCCAGAAA";
		//IGHV1S17*01
		String v17 = "CAGGAGCAGCAGAAGGAGTCCGGGGGTCGACTGGTCATGCCTGGAGGATCCCTGACACTC" +
		"ACCTGCACAGTCTCTGGATTCTCCCTCAGTAGCTACAACATGGGCTGGGTCCGCCAGGCT" +
		"CCAGGGGAGGGGCTGGAATACATCGGATGGATTAGTACTGGTGGTAGCGCATACTACGCG" +
		"AGCTGGGTGAATGGTCGATTCACCATCTCCAAAACCTCGACCACGATGGATCTGAAAATG" + 
		"ACCAGTCTGACAGCCGCGGACACGGCCACCTATTTCTGTGCCAGAGA";
		AffineLocalAlignmentNoPtrs galn = new AffineLocalAlignmentNoPtrs(v13, v17, new DnaSubstitutionMatrix(-10));
		galn.setAlignmentMatrix(new AffineAlignmentMatrixScore(300,300), new AffineAlignmentMatrixBacktrack(300,300));
		galn.initMatrix();
		galn.fillMatrix();
//		galn.alignScoreMatrix.
		Alignment aln = galn.backtrack();
//		System.out.println(aln.toString(50));
		assertEquals(1174, aln.score()); // score determined by EMBOSS
	}
}
