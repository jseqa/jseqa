package jseqa.alignmentfree;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import jseqa.alignment.GlobalAlignmentsIT;

public class FeatureFrequencyProfileIT {

	//IGHV1S13*01
	private String v13 = "CAGGAGCAGCTGGAGGAATCCGGGGGAGGCCTGGTCACGCCTGGAGGAACCCTGACACTC" +
			"ACCTGCACAGTCTCTGGATTCTCCCTCAGTAGCTATGGAGTAAGCTGGGTCCGCCAGGCT" +
			"GCAGGGAAGGGGCTGGAATGGATCGGATACATTAGTAGTAGTGGTAGCGCATACTACGCG" +
			"AGTTGGGTAAATGGTCGATTCACCATCTCCAAAACCTCGAGCACGGTGGATCTGAAAATG" + 
			"ACCAGTCTGAGAGCCGCGGACACGGCCACCTATTTCTGTGCCAGAAA";
	
	@Test
	public void testSimilar() {
		int len = v13.length();
		int numIter = 20000;

		List<String> mutSeqs = GlobalAlignmentsIT.mutateSeq(v13, numIter, 0);
		
		List<Double> muts = new LinkedList<Double>();
		FeatureFrequencyProfile ffp = new FeatureFrequencyProfile(6);
		for(String mutV13 : mutSeqs) {
			ffp.compareSeqs(v13, mutV13);
			muts.add( ffp.pearsonCor() );
		}

		double mean = DistributionStatistics.mean( muts.stream().mapToDouble(d -> d).toArray() );
		double std = DistributionStatistics.std( muts.stream().mapToDouble(d -> d).toArray() );
		assertEquals(0.99999, mean, 0.0001);		
	}

	@Test
	public void testRandom() {
		int len = v13.length();
		int numIter = 20000;
		
		char[] nucs = {'A', 'C', 'G', 'T'};
		Random rand = new Random(0);
		String initRandSeq = IntStream.range(0,len).mapToObj(i -> "" + nucs[rand.nextInt(nucs.length)]).collect(Collectors.joining(""));
				
		List<Double> muts = new LinkedList<Double>();
		FeatureFrequencyProfile ffp = new FeatureFrequencyProfile(6);
		for(int i = 0; i < numIter; i++) {
			String altSeq = new String(initRandSeq);
			List<String> lst = Arrays.asList(altSeq.split(""));
			Collections.shuffle(lst, rand);
			String altSeq2 = lst.stream().map(ch -> ch).collect(Collectors.joining(""));
			
			ffp.compareSeqs(initRandSeq, altSeq2);
			muts.add( ffp.pearsonCor() );
		}

		double mean = DistributionStatistics.mean( muts.stream().mapToDouble(d -> d).toArray() );
		double std = DistributionStatistics.std( muts.stream().mapToDouble(d -> d).toArray() );
		assertEquals(0.035522, mean, 0.000001);
		assertTrue(std < 0.006);
	}
	
}
