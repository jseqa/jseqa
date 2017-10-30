package jseqa.alignment;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jseqa.alignment.substitution.BlosumMatrices;
import jseqa.alignment.substitution.BlosumSubstitutionMatrix;
import jseqa.alignment.substitution.DnaSubstitutionMatrix;

public class AffineGlobalAlignmentNoPtrsTest {

	@Test
	public void testSimpleFromGlobal() {
		String a = "AAATTT";
		String b = "AATC";
		AffineGlobalAlignmentNoPtrs galn = new AffineGlobalAlignmentNoPtrs(a, b, new DnaSubstitutionMatrix(-5, -5));
		galn.setAlignmentMatrix(new AffineAlignmentMatrixScore(10,10), new AffineAlignmentMatrixBacktrack(10,10));
		galn.initMatrix();
		galn.fillMatrix();
		Alignment aln = galn.backtrack();
		assertEquals(1, aln.score());
		
		Alignment aln2 = FullAlignments.affineGlobalAlignNt(a, b, new DnaSubstitutionMatrix(-5));
		assertEquals(1, aln2.score());
	}
	
	@Test
	public void testSimple() {
		String a = "AAAAAATTTTT";
		String b =     "AATT";
		AffineGlobalAlignmentNoPtrs galn = new AffineGlobalAlignmentNoPtrs(a, b, new DnaSubstitutionMatrix(-5, -1));
		galn.setAlignmentMatrix(new AffineAlignmentMatrixScore(20,20), new AffineAlignmentMatrixBacktrack(20,20));
		galn.initMatrix();
//		System.out.println(galn.alignScoreMatrix.toString(a.length()+1, b.length()+1));
//		System.out.println(galn.alignPtrMatrix.toString(a.length()+1, b.length()+1));
		galn.fillMatrix();
//		System.out.println(galn.alignScoreMatrix.toString(a.length()+1, b.length()+1));
//		System.out.println(galn.alignPtrMatrix.toString(a.length()+1, b.length()+1));
		Alignment aln = galn.backtrack();
//		System.out.println(aln);
		assertEquals(9, aln.score());
		assertEquals(4, aln.numMatches());
		
		Alignment aln2 = FullAlignments.affineGlobalAlignNt(a, b, new DnaSubstitutionMatrix(-5, -1));
		assertEquals(9, aln2.score());
	}
	
	@Test
	public void testBandedSimple() {
		String a= "ATAAGCGT";
		String b = "ATAGAGT";
		
		
		Alignment aln = FullAlignments.affineGlobalAlignNt(a, b, new DnaSubstitutionMatrix(-5, -1));
		assertEquals(21, aln.score());
		
		Alignment aln2 = BandedAlignments.globalAffineNt(a, b, new DnaSubstitutionMatrix(-5, -1), 8);
		assertEquals(21, aln2.score());
		assertEquals(aln.top(), aln2.top());
		assertEquals(aln.bottom(), aln2.bottom());
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
		AffineGlobalAlignmentNoPtrs galn = new AffineGlobalAlignmentNoPtrs(v13, v17, new DnaSubstitutionMatrix(-10));
		galn.setAlignmentMatrix(new AffineAlignmentMatrixScore(300,300), new AffineAlignmentMatrixBacktrack(300,300));

		galn.initMatrix();
		galn.fillMatrix();
//		galn.alignScoreMatrix.
		Alignment aln = galn.backtrack();
//		System.out.println(aln.toString(50));
		assertEquals(1174, aln.score()); // score determined by EMBOSS
	}

	@Test
	public void testGlobalBlosum62() {
		String seq1 = "TELKDD";
		String seq2 = "LKTEL";
		BlosumSubstitutionMatrix blosum62 = new BlosumSubstitutionMatrix(BlosumMatrices.BLOSUM62, -10, -2);
	    Alignment aln = FullAlignments.affineGlobalAlignNt(seq1, seq2, blosum62);
	    assertEquals(-12, aln.score());
	}
	
}
