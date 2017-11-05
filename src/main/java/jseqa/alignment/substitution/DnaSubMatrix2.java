package jseqa.alignment.substitution;

@Deprecated
public class DnaSubMatrix2 extends AbstractNtSubstitutionMatrix {

	private int[] flatSparceMatrix;
	
	private char[] nucs = {'A', 'C', 'G', 'T', 'N'};
	
	public DnaSubMatrix2(int gapPenal, int gapExtendPenal) {
		super(gapPenal, gapExtendPenal);
		this.flatSparceMatrix = new int[128*128];
		this.initFlatSparceMatrix(5, -4, -2, -1);
	}
	
	protected void initFlatSparceMatrix(int match, int mismatch, int nMismatch, int nMatch) {
		for(int i = 0; i < nucs.length; i++) {
			for(int j = 0; j < nucs.length; j++) {
				int val = 0;
				if(nucs[i] == 'N' || nucs[j] == 'N') {
					if(nucs[i] == 'N' && nucs[j] == 'N') {
						val = nMatch; //-1;
					} else {
						val = nMismatch; //-2;
					}
				} else {
					if(nucs[i] == nucs[j]) {
						val = match; //5; 
					} else {
						val = mismatch; //-4; 
					}
				}
				this.flatSparceMatrix[nucs[i]*128 + nucs[j]] = val;
			}
		}
	}
	
	@Override
	public int score(char a, char b) {
		return this.flatSparceMatrix[a*128 + b];
	}
	
//	private int[] flatMatrix = {
//			5,-4,-4,-4,-2,
//			-4, 5,-4,-4,-2,
//			-4,-4, 5,-4,-2, 
//			-4,-4,-4, 5,-2,
//			-2,-2,-2,-2,-1};
//	
//	@Override
//	public int score(char a, char b) {
//		return this.flatMatrix[this.ntLookup[a]*5+this.ntLookup[b]];
//	}
}
