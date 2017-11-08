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

}
