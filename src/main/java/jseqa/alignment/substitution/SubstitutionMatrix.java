package jseqa.alignment.substitution;

/**
 * Represents a scoring matrix for sequence match/mismatches, as well basic/affine gap penalties.
 *
 */
public interface SubstitutionMatrix {

	/**
	 * Scores the match/mismatch of a &rarr; b.
	 * @param a char transitioning from.
	 * @param b char transitioning to.
	 * @return int score of comparing a to b
	 */
	public int score(char a, char b);
	
	/**
	 * Returns a gap penalty (interpreted as gap open for affine penalties).
	 * @return int gap penalty
	 */
	public int gapPenalty();
	
	/**
	 * Returns the gap extension penalty, used only for affine penalties.
	 * @return int gap extension penalty
	 */
	public int gapExtensionPenalty();
}
