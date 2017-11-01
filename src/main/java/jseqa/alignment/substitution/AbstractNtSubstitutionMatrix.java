package jseqa.alignment.substitution;

/**
 * Abstract class representing a nucleotide substitution matrix. Uses a sparse, flat, matrix to access the 
 * substitution values.
 *
 */
abstract public class AbstractNtSubstitutionMatrix extends AbstractSubstitutionMatrix {

//	/** Sparse vector for looking up nucleotide char to index */
//	protected int[] ntLookup;
	
	private int[] flatSparceMatrix;
	
	private char[] nucs = {'A', 'C', 'G', 'T', 'N'};
	
	public AbstractNtSubstitutionMatrix() {
		this(-5, -1);
	}
	
	public AbstractNtSubstitutionMatrix(int gapPenal, int gapExtendPenal) {
		super(gapPenal, gapExtendPenal);
//		this.ntLookup = new int[128];
//		this.ntLookup['A'] = 0;
//		this.ntLookup['C'] = 1;
//		this.ntLookup['G'] = 2;
//		this.ntLookup['T'] = 3;
//		this.ntLookup['N'] = 4;
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
}
