package jseqa.alignment;

/**
 * Interface for classes that compute pairwise alignments.
 *
 */
public interface PairwiseAlignment {

	/** Minimum value to be used to prevent underflow. */
	public static final int MIN_VALUE = -1000000;
	
	/** Initialize the dynamic programming matrix */
	public void initMatrix();
	
	/** Fill the dynamic programming matrix */
	public void fillMatrix();
	
	/** 
	 * Fill only the band of the dynamic programming matrix
	 * @param bandwidth int width of band. 
	 */
	public void bandedFillMatrix(int bandwidth);
	
	/**
	 * Run backtracking procedure to produce an alignment
	 * @return {@code Alignment} produced by backtracking.
	 */
	public Alignment backtrack();
	
	/**
	 * Retrieve the last computed alignment.
	 * @return {@code Alignment} of the last compiled alignment.
	 */
	public Alignment getAlignment();
	
}
