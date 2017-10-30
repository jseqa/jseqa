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
	 * lookup for molecular weight, originally from: 
	 * <a href="http://www.genome.jp/dbget-bin/www_bget?aaindex+FASG760101">Fasman, 1976</a>.
	 */
	public static final Map<Character,Double> WEIGHT_TABLE = 
			new HashMap<Character,Double>(){
				private static final long serialVersionUID = 1L; 
				{
					put('A', 89.09);
					put('C', 121.15);
					put('D', 133.10);
					put('E', 147.13);
					put('F', 165.19);
					put('G', 75.07);
					put('H', 155.16);
					put('I', 131.17);
					put('K', 146.19);
					put('L', 131.17);
					put('M', 149.21);
					put('N', 132.12);
					put('P', 115.13);
					put('Q', 146.15);
					put('R', 174.20);
					put('S', 105.09);
					put('T', 119.12);
					put('U', 168.06);
					put('V', 117.15);
					put('W', 204.23);
					put('Y', 181.19);
				}
	};
}
