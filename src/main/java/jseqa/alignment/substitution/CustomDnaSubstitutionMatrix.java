package jseqa.alignment.substitution;

/**
 * Creates a custom scoring matrix for nucleotides, where the diagonal (i.e., matches) are all one value, and 
 * off-diagonals (i.e., mismatches) are all another, user-defined, value.
 *
 */
public class CustomDnaSubstitutionMatrix extends AbstractSubstitutionMatrix implements NtIndexer {
	
	private int match;
	
	private int mismatch;
	
//	private int[][] mat = { {5,-4,-4,-4,-2}, 
//							{-4,5,-4,-4,-2},
//							{-4,-4,5,-4,-2}, 
//							{-4,-4,-4,5,-2},
//							{-2,-2,-2,-2,-1}
//							};
	private int[] mat = { 5,-4,-4,-4,-2, 
						 -4,5,-4,-4,-2,
						 -4,-4,5,-4,-2, 
						 -4,-4,-4,5,-2,
						 -2,-2,-2,-2,-1
						};
	
	public CustomDnaSubstitutionMatrix() {}
	
	public CustomDnaSubstitutionMatrix(int gappenalty, int extensionPenalty) {
		super(gappenalty, extensionPenalty);
	}

	public CustomDnaSubstitutionMatrix(int match, int mismatch, int gap, int extension) {
		super(gap, extension);
		this.match = match;
		this.mismatch = mismatch;
		this.initMatrix();
	}
	
	private void initMatrix() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(i == j) {
//					this.mat[i][j] = this.match;
					this.mat[i*5+j] = this.match;
				} else {
//					this.mat[i][j] = this.mismatch;
					this.mat[i*5+j] = this.mismatch;
				}
			}
		}
	}
	
	@Override
	public int score(char a, char b) {
//		return mat[this.ntIndex(a)][this.ntIndex(b)];
		return mat[this.ntIndex(a)*5+this.ntIndex(b)];
	}
	
}
