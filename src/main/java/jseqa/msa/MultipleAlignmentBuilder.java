package jseqa.msa;

import jseqa.alignment.Alignment;

/**
 * Multiple sequence alignment builder.
 *
 */
public interface MultipleAlignmentBuilder {

    /**
     * Add the sequences in a single pairwise alignment to the MSA.
     * @param aln {@code Alignment} to add to the MSA.
     */
    public void add(Alignment aln);

    /**
     * Output the current state as a {@code MultipleAlignment} object.
     * @return
     */
    public MultipleAlignment toMultipleAlignment();
}
