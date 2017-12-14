package jseqa.msa;

import static org.junit.Assert.*;

import org.junit.Test;

import jseqa.alignment.Alignment;
import jseqa.msa.MultipleAlignment;
import jseqa.msa.MultipleAlignmentBuilder;

public class SingleGuideMultipleAlignmentBuilderTest {

	@Test
	public void testSimple() {
		Alignment a1 = new Alignment("ACT", "TCT"); 
		Alignment a2 = new Alignment("ACT", "-CT"); 
		Alignment a3 = new Alignment("A-CT", "ATCT");
		Alignment a4 = new Alignment("ACT", "ACT");
		
		SingleGuideMultipleAlignmentBuilder maBuild = new SingleGuideMultipleAlignmentBuilder(a1);
		maBuild.add(a2.top(), a2.bottom());
		maBuild.add(a3.top(), a3.bottom());
		maBuild.add(a4.top(), a4.bottom());
		
		MultipleAlignment ma = maBuild.toMultipleAlignment();
//		System.out.println(ma.toString());
		String expMsa = "A-CT\n" + 
				"T-CT\n" + 
				"--CT\n" + 
				"ATCT\n" + 
				"A-CT\n";
		assertEquals(expMsa, ma.toString());
	}
	
	@Test
	public void testLarger() {
		Alignment a1 = new Alignment("ATTGCCATT", 
									 "ATGGCCATT");
		Alignment a2 = new Alignment("ATTGCCATT--", 
									 "ATC-CAATTTT");
		Alignment a3 = new Alignment("ATTGCCATT", 
									 "ATCTTC-TT");
		Alignment a4 = new Alignment("ATTGCCATT", 
									 "ACTGACC--");
		
		SingleGuideMultipleAlignmentBuilder maBuild = new SingleGuideMultipleAlignmentBuilder(a1);
		maBuild.add(a2.top(), a2.bottom());
		maBuild.add(a3.top(), a3.bottom());
		maBuild.add(a4.top(), a4.bottom());
		
		MultipleAlignment ma = maBuild.toMultipleAlignment();
		
//		System.out.println(ma.toString());
		String expMsa = 
				"ATTGCCATT--\n" + 
				"ATGGCCATT--\n" + 
				"ATC-CAATTTT\n" + 
				"ATCTTC-TT--\n" + 
				"ACTGACC----\n"; 
		assertEquals(expMsa, ma.toString());
	}

}
