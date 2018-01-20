package jseqa.alignment.substitution;

/**
 * Substitution matrix for nucleotides, where:
 * <ul>
 * 	<li>Matches: +5</li>
 * 	<li>Mismatches: -4</li>
 * 	<li>N mismatch: -2</li>
 * 	<li>N match: -1</li>
 * </ul>
 *
 */
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
