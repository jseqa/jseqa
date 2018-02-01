package jseqa.sequence;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the canonical codon table.
 *
 */
public final class CodonTable {

    public static char STOP_CH = '*';

    /**
     * Translate a single codon to an amino acid residue.
     * @param codon {@code String} 3-mer representing codon.
     * @return 
     */
    public static char translateCodon(String codon) {
        if (codon.length() != 3) {
            throw new IllegalArgumentException(codon + " length is != 3");
        }
        if (!CODON_TABLE.containsKey(codon)) {
            throw new IllegalArgumentException(codon + " is not a valid codon");
        }
        return CODON_TABLE.get(codon);
    }

    /**
     * Map from codon to amino acid.
     */
    public static final Map<CharSequence,Character> CODON_TABLE = 
            new HashMap<CharSequence,Character>() {
        private static final long serialVersionUID = 1L; 
        {
            put("TTA", 'L');
            put("TTC", 'F');
            put("TTG", 'L');
            put("TTT", 'F');
            //
            put("TCA", 'S');
            put("TCC", 'S');
            put("TCG", 'S');
            put("TCT", 'S');
            //
            put("TAA", STOP_CH);
            put("TAC", 'Y');
            put("TAG", STOP_CH);
            put("TAT", 'Y');
            //
            put("TGA", STOP_CH);
            put("TGC", 'C');
            put("TGG", 'W');
            put("TGT", 'C');
            //
            put("CTA", 'L');
            put("CTC", 'L');
            put("CTG", 'L');
            put("CTT", 'L');
            //
            put("CCA", 'P');
            put("CCC", 'P');
            put("CCG", 'P');
            put("CCT", 'P');
            //
            put("CAA", 'Q');
            put("CAC", 'H');
            put("CAG", 'Q');
            put("CAT", 'H');
            //
            put("CGA", 'R');
            put("CGC", 'R');
            put("CGG", 'R');
            put("CGT", 'R');
            //
            put("ATA", 'I');
            put("ATC", 'I');
            put("ATG", 'M');
            put("ATT", 'I');
            //
            put("ACA", 'T');
            put("ACC", 'T');
            put("ACG", 'T');
            put("ACT", 'T');
            //
            put("AAA", 'K');
            put("AAC", 'N');
            put("AAG", 'K');
            put("AAT", 'N');
            //
            put("AGA", 'R');
            put("AGC", 'S');
            put("AGG", 'R');
            put("AGT", 'S');
            //
            put("GTA", 'V');
            put("GTC", 'V');
            put("GTG", 'V');
            put("GTT", 'V');
            //
            put("GCA", 'A');
            put("GCC", 'A');
            put("GCG", 'A');
            put("GCT", 'A');
            //
            put("GAA", 'E');
            put("GAC", 'D');
            put("GAG", 'E');
            put("GAT", 'D');
            //
            put("GGA", 'G');
            put("GGC", 'G');
            put("GGG", 'G');
            put("GGT", 'G');
        }
    };

}
