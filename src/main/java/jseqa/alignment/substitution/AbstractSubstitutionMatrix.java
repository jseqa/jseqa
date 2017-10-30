package jseqa.alignment.substitution;

/**
 * Skeletal implementation of a scoring matrix, only contains gap info.
 *
 */
public abstract class AbstractSubstitutionMatrix implements SubstitutionMatrix {
	
	protected int gapPenalty;
	
	protected int gapExtPenalty;
	
	public AbstractSubstitutionMatrix(int gapPenal, int gapExtendPenal) {
		this.gapPenalty = gapPenal;
		this.gapExtPenalty = gapExtendPenal;
	}
	
	public AbstractSubstitutionMatrix() {
		this(-5, -1);
	}
	
	public abstract int score(char a, char b);

	public int gapPenalty() {
		return this.gapPenalty;
	}
	
	public int gapExtensionPenalty() {
		return this.gapExtPenalty;
	}
}
