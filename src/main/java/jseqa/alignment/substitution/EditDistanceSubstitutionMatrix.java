package jseqa.alignment.substitution;

/**
 * Substitution matrix that scores for edit distance: +1 for match, -1 for mismatch/indel. 
 *
 */
public class EditDistanceSubstitutionMatrix implements SubstitutionMatrix {
	
	public EditDistanceSubstitutionMatrix() {}
	
	@Override
	public int score(char a, char b) {
		return ((a != b) ? -1 : 1);
	}

	@Override
	public int gapPenalty() {
		return -1;
	}

	@Override
	public int gapExtensionPenalty() {
		return -1;
	}

}
