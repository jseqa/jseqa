package jseqa.alignment.substitution;

public class EditDistanceSubstitutionMatrix implements SubstitutionMatrix {
	
	public EditDistanceSubstitutionMatrix() {}
	
	@Override
	public int score(char a, char b) {
//		return ((a ^ b) > 0) ? -1 : 1;
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
