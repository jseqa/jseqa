package jseqa.alignment;

import static org.junit.Assert.*;

import org.junit.Test;

import jseqa.alignment.substitution.DnaSubstitutionMatrix;

public class GlobalAlignmentTest {

	@Test
	public void testSimple() {
		String a = "AAATTT";
		String b = "AATC";
		GlobalAlignment galn = new GlobalAlignment(a, b, new DnaSubstitutionMatrix(-5));
		galn.setAlignmentMatrix(new AlignmentMatrixScore(10,10), new AlignmentMatrixBacktrack(10,10));
		galn.initMatrix();
		galn.fillMatrix();
		Alignment aln = galn.backtrack();
		assertEquals(1, aln.score());
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
		GlobalAlignment galn = new GlobalAlignment(v13, v17, new DnaSubstitutionMatrix(-10));
		galn.setAlignmentMatrix(new AlignmentMatrixScore(300,300), new AlignmentMatrixBacktrack(300,300));
		galn.initMatrix();
		galn.fillMatrix();
		Alignment aln = galn.backtrack();
		assertEquals(1174, aln.score()); // score determined by EMBOSS
	}
	
}
