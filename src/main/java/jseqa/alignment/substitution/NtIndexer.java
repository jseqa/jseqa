package jseqa.alignment.substitution;

/**
 * Allows for indexing of nucleotides; use as mixin.
 *
 */
@Deprecated
public interface NtIndexer {
	
	/**
	 * Indexes a nucleotide {@code char} to an int.
	 * @param ch char to index.
	 * @return int index of {@code ch}.
	 */
	public default int ntIndex(char ch) {
		if(ch == 'A') {  // 10sec 
			return 0;
		} else if(ch == 'C') { 
			return 1;
		} else if(ch == 'G') { 
			return 2;
		} else if(ch == 'T') {
			return 3;
		} else if(ch == 'N') {
			return 4;
		} else {
			return 5;
		}
//		switch(ch) {  // 18sec
//			case 'A':
//				return 0;
//			case 'C':
//				return 1;
//			case 'G':
//				return 2;
//			case 'T':
//				return 3;
//			case 'N':
//				return 4;
//			default: return 5;
//		}
	}
}
