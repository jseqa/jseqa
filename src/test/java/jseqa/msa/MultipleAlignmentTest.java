package jseqa.msa;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import jseqa.msa.MultipleAlignment;

public class MultipleAlignmentTest {

	private MultipleAlignment msa;
	
	@Before
	public void init() {
		List<String> seqs = new LinkedList<String>();
		seqs.add("AACATA");
		seqs.add("AATCTG");
		
		msa = new MultipleAlignment(seqs);
	}
	
	@Test
	public void testPrintMatrix() {
		assertEquals(6, msa.numCols());
		
		msa.removeMonomorphicColumns();
		assertEquals(3, msa.numCols());
	}

	@Test
	public void testCopyConst() {
		msa.removeMonomorphicColumns();
		MultipleAlignment msaCopy = new MultipleAlignment(msa);
		
		assertEquals(msa.numCols(), msaCopy.numCols());	
	}
	
}
