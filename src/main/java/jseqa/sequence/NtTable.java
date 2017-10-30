package jseqa.sequence;

import java.util.Map;
import java.util.HashMap;

/**
 * Nucleotide property lookup
 *
 */
public final class NtTable {

	public static char reverseNt(char nt) {
		if(!REVERSE_TABLE.containsKey(nt)) {
			throw new IllegalArgumentException(nt + " is not a nucleotide");
		}
		return REVERSE_TABLE.get(nt);
	}
	
	public static final Map<Character,Character> REVERSE_TABLE = 
			new HashMap<Character,Character>(){
				private static final long serialVersionUID = 1L; 
				{
					put('A', 'T');
					put('C', 'G');
					put('G', 'C');
					put('T', 'A');
				}
	};
	
	private NtTable() {}
}
