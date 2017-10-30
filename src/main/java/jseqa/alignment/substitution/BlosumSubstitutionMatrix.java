package jseqa.alignment.substitution;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Reads a BLOSUM substitution matrix from a resource file during object construction, instantiating the substitution
 * matrix.<br>
 * See {@see BlosumMatrices} for details and different matrices. 
 *
 */
public class BlosumSubstitutionMatrix extends AbstractSubstitutionMatrix {
	
	private int[][] matrix;
	
	private BlosumMatrices blosumMat;
	
	private static final Map<Character,Integer> resLookup;
	static {
		resLookup = new HashMap<Character,Integer>();
		char res[] = {'A', 'R', 'N', 'D', 'C', 'Q', 'E', 'G', 'H', 'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y', 'V', 'B', 'Z', 'X', '*'};
		for(int i = 0; i < res.length; i++) {
			resLookup.put(res[i], i);
		}
	}
	
	public static final BlosumSubstitutionMatrix BLOSUM50 = new BlosumSubstitutionMatrix(BlosumMatrices.BLOSUM50, -5, -1);
	
	public static final BlosumSubstitutionMatrix BLOSUM62 = new BlosumSubstitutionMatrix(BlosumMatrices.BLOSUM62, -5, -1);
	
	public static final BlosumSubstitutionMatrix BLOSUM80 = new BlosumSubstitutionMatrix(BlosumMatrices.BLOSUM80, -5, -1);
	
	public static final BlosumSubstitutionMatrix BLOSUM90 = new BlosumSubstitutionMatrix(BlosumMatrices.BLOSUM90, -5, -1);
	
	/**
	 * Constructor that creates a substitution matrix based on the type specified, with gap open/extend penalties.
	 * @param bmat {@code BlosumMatrices} type describing which matrix to use.
	 * @param gapopen int gap open penalty
	 * @param gapextend int gap extend penalty (only used if with an affine alignment).
	 */
	public BlosumSubstitutionMatrix(BlosumMatrices bmat, int gapopen, int gapextend) {
		super(gapopen, gapextend);
		this.blosumMat = bmat;
		this.matrix = new int[resLookup.size()][resLookup.size()];
		this.readFromResource();
	}
	
	/** {@inheritDoc} */
	@Override
	public int score(char a, char b) {
		return this.matrix[this.getResidueIdx(a)][this.getResidueIdx(b)];
	}

	private void readFromResource() {
		InputStream in = getClass().getResourceAsStream("/blosum/BLOSUM" + this.blosumMat.getBlosumNum() +  ".txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		reader.lines()
			  .filter(l -> (l.charAt(0) != '#' && l.charAt(0) != ' '))
			  .forEach(l -> {
				  String[] row = l.split("\\s+");
				  char rowRes = row[0].charAt(0);
				  for(int j = 1; j < row.length; j++) {
					  int val = Integer.parseInt(row[j]);
					  this.matrix[this.getResidueIdx(rowRes)][j-1] = val;
				  }
			  });
	}
	
	private int getResidueIdx(char res) {
		return resLookup.get(res);
	}
}
