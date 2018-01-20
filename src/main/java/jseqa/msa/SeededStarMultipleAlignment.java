package jseqa.msa;

import java.util.ArrayList;
import java.util.Collection;

import jseqa.alignment.substitution.SubstitutionMatrix;

/**
 * Create a multiple sequence alignment using the star approach {@link StarMultipleAlignment}, but seeding it
 * with the centroid rather than identifying a centroid from sequences. 
 *
 */
public class SeededStarMultipleAlignment extends StarMultipleAlignment {

	public SeededStarMultipleAlignment(String seedGuideSeq, Collection<String> otherSeqs, SubstitutionMatrix submat) {
		super(submat);
		this.guideSeq = seedGuideSeq;
		this.seqs = new ArrayList<String>(otherSeqs.size());
		this.seqs.addAll(otherSeqs);
	}
	
}
