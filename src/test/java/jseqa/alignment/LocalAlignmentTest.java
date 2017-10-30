package jseqa.alignment;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import jseqa.alignment.substitution.BlosumMatrices;
import jseqa.alignment.substitution.BlosumSubstitutionMatrix;
import jseqa.alignment.substitution.DnaSubstitutionMatrix;
import jseqa.alignment.substitution.SubstitutionMatrix;

public class LocalAlignmentTest {

	@Test
	public void testSimple() {
		String a = "AAATTT";
		String b =  "AATC";
		LocalAlignment galn = new LocalAlignment(a, b, new DnaSubstitutionMatrix(-5));
		AlignmentMatrixScore scoreMat = new AlignmentMatrixScore(10,10);
		galn.setAlignmentMatrix(scoreMat, new AlignmentMatrixBacktrack(10,10));
		galn.initMatrix();
		galn.fillMatrix();
		Alignment aln = galn.backtrack();
		System.out.println(scoreMat);
		assertEquals(5*3, aln.score());
	}
	
	@Test
	public void testSimpleLocal() {
		String a = "GGGGGGGAAATTTCCCCCCCCCCC";
		String b =        "AAATTTC";
		int len = a.length()+5;
		LocalAlignment galn = new LocalAlignment(a, b, new DnaSubstitutionMatrix(-5));
		galn.setAlignmentMatrix(new AlignmentMatrixScore(len, len), new AlignmentMatrixBacktrack(len, len));
		galn.initMatrix();
		galn.fillMatrix();
		Alignment aln = galn.backtrack();
		assertEquals(7*5, aln.score());
		assertEquals(b, aln.top());
		assertEquals(b, aln.bottom());
	}
	
	@Test
	public void testFos() {
		String mouseFos = "MMFSGFNADYEASSSRCSSASPAGDSLSYYHSPADSFSSMGSPVNTQDFCADLSVSSANFIPTVTAISTSPDLQWLVQPTLVSSVAPSQTRAPHPYGLPTQSAGAYARAGMVKTVSGGRAQSIGRRGKVEQLSPEEEEKRRIRRERNKMAAAKCRNRRRELTDTLQAETDQLEDEKSALQTEIANLLKEKEKLEFILAAHRPACKIPDDLGFPEEMSVASLDLTGGLPEASTPESEEAFTLPLLNDPEPKPSLEPVKSISNVELKAEPFDDFLFPASSRPSGSETSRSVPDVDLSGSFYAADWEPLHSNSLGMGPMVTELEPLCTPVVTCTPGCTTYTSSFVFTYPEADSFPSCAAAHRKGSSSNEPSSDSLSSPTLLAL";
		String mouseFosB = "MFQAFPGDYDSGSRCSSSPSAESQYLSSVDSFGSPPTAAASQECAGLGEMPGSFVPTVTAITTSQDLQWLVQPTLISSMAQSQGQPLASQPPAVDPYDMPGTSYSTPGLSAYSTGGASGSGGPSTSTTTSGPVSARPARARPRRPREETLTPEEEEKRRVRRERNKLAAAKCRNRRRELTDRLQAETDQLEEEKAELESEIAELQKEKERLEFVLVAHKPGCKIPYEEGPGPGPLAEVRDLPGSTSAKEDGFGWLLPPPPPPPLPFQSSRDAPPNLTASLFTHSEVQVLGDPFPVVSPSYTSSFVLTCPEVSAFAGAQRTSGSEQPSDPLNSPSLLAL";
		SubstitutionMatrix blosum62 = new BlosumSubstitutionMatrix(BlosumMatrices.BLOSUM62, -10, -1);
		Alignment aln = FullAlignments.localAlignNt(mouseFos, mouseFosB, blosum62);
//		System.out.println(aln.toString(45));
//		assertEquals(381, aln.score()); //611
	}
	
	@Test
	public void testRab() {
		String v13 = "CAGGAGCAGCTGGAGGAATCCGGGGGAGGCCTGGTCACGCCTGGAGGAACCCTGACACTC" +
				"ACCTGCACAGTCTCTGGATTCTCCCTCAGTAGCTATGGAGTAAGCTGGGTCCGCCAGGCT" +
				"GCAGGGAAGGGGCTGGAATGGATCGGATACATTAGTAGTAGTGGTAGCGCATACTACGCG" +
				"AGTTGGGTAAATGGTCGATTCACCATCTCCAAAACCTCGAGCACGGTGGATCTGAAAATG" + 
				"ACCAGTCTGAGAGCCGCGGACACGGCCACCTATTTCTGTGCCAGAAA";
		AlignmentMatrixScore alignScoreMat = new AlignmentMatrixScore(300,300);
		AlignmentMatrixBacktrack alignPtrMat = new AlignmentMatrixBacktrack(300,300);
		jseqa.alignment.substitution.SubstitutionMatrix subMat = new DnaSubstitutionMatrix(-10);
		Alignment aln = FullAlignments.localAlignNt(v13, v13, alignScoreMat, alignPtrMat, subMat);
		System.out.println(aln);
		
		Map<BacktrackPtr,Integer> ptrHist = new HashMap<BacktrackPtr,Integer>();
		ptrHist.put(BacktrackPtr.DIAG, 0);
		ptrHist.put(BacktrackPtr.LEFT, 0);
		ptrHist.put(BacktrackPtr.UP, 0);
		ptrHist.put(BacktrackPtr.FREERIDE, 0);
		for(int i = 1; i < v13.length()+1; i++) {
			for(int j = 1; j < v13.length()+1; j++) {
				int val = ptrHist.get(alignPtrMat.get(i, j));
				ptrHist.put(alignPtrMat.get(i, j), val+1);
			}
		}
		System.out.println(ptrHist);
	}
	
}
