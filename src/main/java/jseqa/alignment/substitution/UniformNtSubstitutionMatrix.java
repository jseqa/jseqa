package jseqa.alignment.substitution;

/**
 * Uniformly penalizes all mismatches with -1, and matches with +1.
 *
 */
public class UniformNtSubstitutionMatrix extends AbstractSubstitutionMatrix implements NtIndexer {

	private int[][] matrix = { 
			{ 1,-1,-1,-1,-2}, 
			{-1, 1,-1,-1,-2},
			{-1,-1, 1,-1,-2}, 
			{-1,-1,-1, 1,-2},
			{-2,-2,-2,-2,-1}
			};
	
	@Override
	public int score(char a, char b) {
		int i = this.ntIndex(a);
		int j = this.ntIndex(b);
		return this.matrix[i][j];
	}

}
