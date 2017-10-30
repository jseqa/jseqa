package jseqa.alignment;

/**
 * Builds an alignment one column at a time.
 *
 */
public class AlignmentBuilder {
	
	private StringBuilder topRow;
	
	private StringBuilder bottomRow;
	
	private int score;
	
	/**
	 * Constructor that has no score assigned to it, uses {@code Integer.MIN_VALUE}.
	 */
	public AlignmentBuilder() {
		this(Integer.MIN_VALUE);
	}
	
	/**
	 * Constructor with {@code score} assigned to alignment.
	 * @param score int score of alignment to be built.
	 */
	public AlignmentBuilder(int score) {
		this.topRow = new StringBuilder();
		this.bottomRow = new StringBuilder();
		this.score = score;
	}
	
	/**
	 * Adds a column to the alignment.
	 * @param topCh char to add to {@link #topRow}.
	 * @param bottomCh char to add to {@link #bottomRow}.
	 */
	public void addColumn(char topCh, char bottomCh) {
		this.topRow.append(topCh);
		this.bottomRow.append(bottomCh);
	}
	
	/**
	 * Returns and {@code Alignment} object representing the current state.
	 * @return {@code Alignment} representing current state of build.
	 */
	public Alignment toAlignment() {
		return new Alignment(this.topRow.reverse().toString(), this.bottomRow.reverse().toString(), this.score);
	}
	
	/**
	 * Returns the top row of the alignment.
	 * @return {@code String} top row of the alignment. 
	 */
	public String getTopRow() {
		return this.topRow.toString();
	}
	
	/**
	 * Returns the bottom row of the alignment.
	 * @return {@code String} bottom row of the alignment.
	 */
	public String getBottomRow() {
		return this.bottomRow.toString();
	}
}
