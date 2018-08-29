package jseqa.alignment.substitution;

import java.util.HashMap;
import java.util.Map;

import jseqa.util.Pair;

public class CustomAaSubstitutionMatrix extends AbstractSubstitutionMatrix {

    private int[][] matrix;

    private static final Map<Character,Integer> resLookup;
    
    private final int MIN_VALUE = -100000;
    
    static {
        resLookup = new HashMap<Character,Integer>();
        char res[] = {'A', 'R', 'N', 'D', 'C', 'Q', 'E', 'G', 'H', 'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y', 'V', 'B', 'Z', 'X', '*'};
        for (int i = 0; i < res.length; i++) {
            resLookup.put(res[i], i);
        }
    }
    
    /**
     * Constructor that creates a substitution matrix based on the type specified, with gap open/extend penalties.
     * @param bmat {@code BlosumMatrices} type describing which matrix to use.
     * @param gapopen int gap open penalty
     * @param gapextend int gap extend penalty (only used if with an affine alignment).
     */
    public CustomAaSubstitutionMatrix(Map<Pair<Character,Character>,Integer> pairs, int gapopen, int gapextend) {
        super(gapopen, gapextend);
        this.matrix = new int[resLookup.size()][resLookup.size()];
        for (int i = 0; i < resLookup.size(); i++) {
            for (int j = 0; j < resLookup.size(); j++) {
                if (i == j) {
                    this.matrix[i][j] = 1;
                } else {
                    this.matrix[i][j] = MIN_VALUE;
                }
            }
        }
        for (Pair<Character,Character> pair : pairs.keySet()) {
            int value = pairs.get(pair);
            this.matrix[resLookup.get(pair.first())][resLookup.get(pair.second())] = value;
        }
    }
    
    @Override
    public int score(char a, char b) {
        return this.matrix[resLookup.get(a)][resLookup.get(b)];
    }

}
