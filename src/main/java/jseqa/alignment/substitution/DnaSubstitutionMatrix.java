package jseqa.alignment.substitution;

public class DnaSubstitutionMatrix extends AbstractNtSubstitutionMatrix {
	
	public DnaSubstitutionMatrix() {
		super();
	}
	
	public DnaSubstitutionMatrix(int gapPenal) {
		this(gapPenal, gapPenal);
	}
	
	public DnaSubstitutionMatrix(int gapPenal, int gapExtendPenal) {
		super(gapPenal, gapExtendPenal);
	}
}
