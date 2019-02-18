package jseqa.sequence;

import java.util.HashMap;
import java.util.Map;

/**
 * Amino acid property lookup table.
 *
 */
public final class AaResidueTable {

    /**
     * Get the weight of a single residue.
     * @param res {@code char} residue.
     * @return double weight of {@code res}.
     */
    public static double residueWeight(char res) {
        return WEIGHT_TABLE.get(res);
    }

    /**
     * Lookup for molecular weight, from: 
     * <a href="http://www.genome.jp/dbget-bin/www_bget?aaindex+FASG760101">Fasman, 1976</a>. 
     * These are monoisotopic masses.
     */
    public static final Map<Character,Double> WEIGHT_TABLE = 
            new HashMap<Character,Double>() {
        private static final long serialVersionUID = 1L; 
        {
            put('A', 71.03711);
            put('C', 103.00919);
            put('D', 115.02694);
            put('E', 129.04259);
            put('F', 147.06841);
            put('G', 57.02146);
            put('H', 137.05891);
            put('I', 113.08406);
            put('K', 128.09496);
            put('L', 113.08406);
            put('M', 131.04049);
            put('N', 114.04293);
            put('P', 97.05276);
            put('Q', 128.05858);
            put('R', 156.10111);
            put('S', 87.03203);
            put('T', 101.04768);
            put('U', 150.95363); // selenocystine
            put('V', 99.06841);
            put('W', 186.07931);
            put('Y', 163.06333);
        }
    };
}
