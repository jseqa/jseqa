package jseqa.msa;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import jseqa.alignment.Alignment;
import jseqa.alignment.FullAlignments;
import jseqa.alignment.substitution.CustomDnaSubstitutionMatrix;

public class StarMultipleAlignmentTest {

	
	
	@Test
	public void testStarMultipleAlignment() {
		List<String> seqs = new LinkedList<String>();		
		seqs.add("ATTGCCATT");
		seqs.add("ATGGCCATT");
		seqs.add("ATCCAATTTT");
		seqs.add("ATCTTCTT");
		seqs.add("ACTGACC");
		
		StarMultipleAlignment star = new StarMultipleAlignment(seqs);
		MultipleAlignment msa = star.buidMsa();
		System.out.println(msa.toString());
	}
	
}
