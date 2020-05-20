package jseqa.sequence;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the canonical <a href="https://en.wikipedia.org/wiki/DNA_codon_table">codon table</a>.
 * <br><br>
 * Codon table from Wikipedia re-created below.
 * <br>
 * <table>
 *     <caption>Standard genetic code</caption>
 *     <tbody>
 * 	<tr>
 * 	    <th rowspan="2">1st<br/>base</th>
 * 	    <th colspan="8">2nd base</th>
 * 	    <th rowspan="2">3rd<br/>base</th>
 * 	</tr>
 * 	<tr>
 * 	    <th colspan="2" style="width:150px;">T</th>
 * 	    <th colspan="2" style="width:150px;">C</th>
 * 	    <th colspan="2" style="width:150px;">A</th>
 * 	    <th colspan="2" style="width:150px;">G</th>
 * 	</tr>
 * 	<tr>
 * 	    <th rowspan="4">T</th>
 * 	    <td>TTT</td>
 * 	    <td rowspan="2" style="background-color:#ffe75f">(Phe/F)Phenylalanine</td>
 * 	    <td>TCT</td>
 * 	    <td rowspan="4" style="background-color:#b3dec0">(Ser/S) Serine</td>
 * 	    <td>TAT</td>
 * 	    <td rowspan="2" style="background-color:#b3dec0">(Tyr/Y) Tyrosine</td>
 * 	    <td>TGT</td>
 * 	    <td rowspan="2" style="background-color:#b3dec0">(Cys/C) Cysteine</td>
 * 	    <th>T</th>
 * 	</tr>
 * 	<tr>
 * 	    <td>TTC</td>
 * 	    <td>TCC</td>
 * 	    <td>TAC</td>
 * 	    <td>TGC</td>
 * 	    <th>C</th>
 * 	</tr>
 * 	<tr>
 * 	    <td>TTA</td>
 * 	    <td rowspan="6" style="background-color:#ffe75f">(Leu/L) Leucine</td>
 * 	    <td>TCA</td>
 * 	    <td>TAA</td>
 * 	    <td style="background-color:#B0B0B0;">Stop (<i>Ochre</i>)</td>
 * 	    <td>TGA</td>
 * 	    <td style="background-color:#B0B0B0;">Stop (<i>Opal</i>)</td>
 * 	    <th>A</th>
 * 	</tr>
 * 	<tr>
 * 	    <td>TTG</td>
 * 	    <td>TCG</td>
 * 	    <td>TAG</td>
 * 	    <td style="background-color:#B0B0B0;">Stop</td>
 * 	    <td>TGG</td>
 * 	    <td style="background-color:#ffe75f;">(Trp/W)Tryptophan</td>
 * 	    <th>G</th>
 * 	</tr>
 * 	<tr>
 * 	    <th rowspan="4">C</th>
 * 	    <td>CTT</td>
 * 	    <td>CCT</td>
 * 	    <td rowspan="4" style="background-color:#ffe75f">(Pro/P) Proline</td>
 * 	    <td>CAT</td>
 * 	    <td rowspan="2" style="background-color:#bbbfe0">(His/H) Histidine</td>
 * 	    <td>CGT</td>
 * 	    <td rowspan="4" style="background-color:#bbbfe0">(Arg/R) Arginine</td>
 * 	    <th>T</th>
 * 	</tr>
 * 	<tr>
 * 	    <td>CTC</td>
 * 	    <td>CCC</td>
 * 	    <td>CAC</td>
 * 	    <td>CGC</td>
 * 	    <th>C</th>
 * 	</tr>
 * 	<tr>
 * 	    <td>CTA</td>
 * 	    <td>CCA</td>
 * 	    <td>CAA</td>
 * 	    <td rowspan="2" style="background-color:#b3dec0">(Gln/Q) Glutamine</td>
 * 	    <td>CGA</td>
 * 	    <th>A</th>
 * 	</tr>
 * 	<tr>
 * 	    <td>CTG</td>
 * 	    <td>CCG</td>
 * 	    <td>CAG</td>
 * 	    <td>CGG</td>
 * 	    <th>G</th>
 * 	</tr>
 * 	<tr>
 * 	    <th rowspan="4">A</th>
 * 	    <td>ATT</td>
 * 	    <td rowspan="3" style="background-color:#ffe75f">(Ile/I) Isoleucine</td>
 * 	    <td>ACT</td>
 * 	    <td rowspan="4" style="background-color:#b3dec0">(Thr/T) Threonine</td>
 * 	    <td>AAT</td>
 * 	    <td rowspan="2" style="background-color:#b3dec0">(Asn/N) Asparagine</td>
 * 	    <td>AGT</td>
 * 	    <td rowspan="2" style="background-color:#b3dec0">(Ser/S) Serine</td>
 * 	    <th>T</th>
 * 	</tr>
 * 	<tr>
 * 	    <td>ATC</td>
 * 	    <td>ACC</td>
 * 	    <td>AAC</td>
 * 	    <td>AGC</td>
 * 	    <th>C</th>
 * 	</tr>
 * 	<tr>
 * 	    <td>ATA</td>
 * 	    <td>ACA</td>
 * 	    <td>AAA</td>
 * 	    <td rowspan="2" style="background-color:#bbbfe0">(Lys/K) Lysine</td>
 * 	    <td>AGA</td>
 * 	    <td rowspan="2" style="background-color:#bbbfe0">(Arg/R) Arginine</td>
 * 	    <th>A</th>
 * 	</tr>
 * 	<tr>
 * 	    <td>ATG</td>
 * 	    <td style="background-color:#ffe75f;">(Met/M) Methionine</td>
 * 	    <td>ACG</td>
 * 	    <td>AAG</td>
 * 	    <td>AGG</td>
 * 	    <th>G</th>
 * 	</tr>
 * 	<tr>
 * 	    <th rowspan="4">G</th>
 * 	    <td>GTT</td>
 * 	    <td rowspan="4" style="background-color:#ffe75f">(Val/V) Valine</td>
 * 	    <td>GCT</td>
 * 	    <td rowspan="4" style="background-color:#ffe75f">(Ala/A) Alanine</td>
 * 	    <td>GAT</td>
 * 	    <td rowspan="2" style="background-color:#f8b7d3">(Asp/D) Aspartic acid</td>
 * 	    <td>GGT</td>
 * 	    <td rowspan="4" style="background-color:#ffe75f">(Gly/G) Glycine</td>
 * 	    <th>T</th>
 * 	</tr>
 * 	<tr>
 * 	    <td>GTC</td>
 * 	    <td>GCC</td>
 * 	    <td>GAC</td>
 * 	    <td>GGC</td>
 * 	    <th>C</th>
 * 	</tr>
 * 	<tr>
 * 	    <td>GTA</td>
 * 	    <td>GCA</td>
 * 	    <td>GAA</td>
 * 	    <td rowspan="2" style="background-color:#f8b7d3">(Glu/E) Glutamic acid</td>
 * 	    <td>GGA</td>
 * 	    <th>A</th>
 * 	</tr>
 * 	<tr>
 * 	    <td>GTG</td>
 * 	    <td>GCG</td>
 * 	    <td>GAG</td>
 * 	    <td>GGG</td>
 * 	    <th>G</th>
 * 	</tr>
 *     </tbody>
 * </table>
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
            
            put("TCA", 'S');
            put("TCC", 'S');
            put("TCG", 'S');
            put("TCT", 'S');
            
            put("TAA", STOP_CH);
            put("TAC", 'Y');
            put("TAG", STOP_CH);
            put("TAT", 'Y');
            
            put("TGA", STOP_CH);
            put("TGC", 'C');
            put("TGG", 'W');
            put("TGT", 'C');
            
            put("CTA", 'L');
            put("CTC", 'L');
            put("CTG", 'L');
            put("CTT", 'L');
            
            put("CCA", 'P');
            put("CCC", 'P');
            put("CCG", 'P');
            put("CCT", 'P');
            
            put("CAA", 'Q');
            put("CAC", 'H');
            put("CAG", 'Q');
            put("CAT", 'H');
            
            put("CGA", 'R');
            put("CGC", 'R');
            put("CGG", 'R');
            put("CGT", 'R');
            
            put("ATA", 'I');
            put("ATC", 'I');
            put("ATG", 'M');
            put("ATT", 'I');
            
            put("ACA", 'T');
            put("ACC", 'T');
            put("ACG", 'T');
            put("ACT", 'T');
            
            put("AAA", 'K');
            put("AAC", 'N');
            put("AAG", 'K');
            put("AAT", 'N');
            
            put("AGA", 'R');
            put("AGC", 'S');
            put("AGG", 'R');
            put("AGT", 'S');
            
            put("GTA", 'V');
            put("GTC", 'V');
            put("GTG", 'V');
            put("GTT", 'V');
            
            put("GCA", 'A');
            put("GCC", 'A');
            put("GCG", 'A');
            put("GCT", 'A');
            
            put("GAA", 'E');
            put("GAC", 'D');
            put("GAG", 'E');
            put("GAT", 'D');
            
            put("GGA", 'G');
            put("GGC", 'G');
            put("GGG", 'G');
            put("GGT", 'G');
        }
    };

}
