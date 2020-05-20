package jseqa.alignment.substitution;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * A substitution matrix for <a href="https://en.wikipedia.org/wiki/Nucleic_acid_notation">IUPAC nucletide codes</a>.
 * Substitution matrix taken from NUC4.4 at
 * <a href="ftp://ftp.ncbi.nih.gov/blast/matrices/">ftp://ftp.ncbi.nih.gov/blast/matrices/</a>.
 * IUPAC table below re-created from Wikipedia page.
 * <br><br>
 * <table>
 *     <tr>
 * 	<th rowspan="2">Description</th>
 * 	<th rowspan="2">Symbol</th>
 * 	<th colspan="5">Bases represented</th>
 *     <tr>
 * 	<th>#</th>
 * 	<th>A</th>
 * 	<th>C</th>
 * 	<th>G</th>
 * 	<th>T</th>
 *     </tr>
 *     <tr>
 * 	<td style="text-align:left;">Adenine</td>
 * 	<td><b>A</b></td>
 * 	<td rowspan="5">1</td>
 * 	<td>A</td>
 * 	<td></td>
 * 	<td></td>
 * 	<td></td>
 *     </tr>
 *     <tr>
 * 	<td style="text-align:left;">Cytosine</td>
 * 	<td><b>C</b></td>
 * 	<td></td>
 * 	<td>C</td>
 * 	<td></td>
 * 	<td></td>
 *     </tr>
 *     <tr>
 * 	<td style="text-align:left;">Guanine</td>
 * 	<td><b>G</b></td>
 * 	<td></td>
 * 	<td></td>
 * 	<td>G</td>
 * 	<td></td>
 *     </tr>
 *     <tr>
 * 	<td style="text-align:left;">Thymine</td>
 * 	<td><b>T</b></td>
 * 	<td></td>
 * 	<td></td>
 * 	<td></td>
 * 	<td>T</td>
 *     </tr>
 *     <tr>
 * 	<td style="text-align:left;">Uracil</td>
 * 	<td><b>U</b></td>
 * 	<td></td>
 * 	<td></td>
 * 	<td></td>
 * 	<td>U</td>
 *     </tr>
 *     <tr bgcolor="#e8e8e8">
 * 	<td style="text-align:left;">Weak</td>
 * 	<td><b>W</b>
 * 	</td>
 * 	<td rowspan="6">2
 * 	</td>
 * 	<td>A</td>
 * 	<td></td>
 * 	<td></td>
 * 	<td>T</td>
 *     </tr>
 *     <tr bgcolor="#e8e8e8">
 * 	<td style="text-align:left;">Strong</td>
 * 	<td><b>S</b></td>
 * 	<td></td>
 * 	<td>C</td>
 * 	<td>G</td>
 * 	<td></td>
 *     </tr>
 *     <tr bgcolor="#e8e8e8">
 * 	<td style="text-align:left;">A<i>m</i>ino</td>
 * 	<td><b>M</b></td>
 * 	<td>A</td>
 * 	<td>C</td>
 * 	<td></td>
 * 	<td></td>
 *     </tr>
 *     <tr bgcolor="#e8e8e8">
 * 	<td style="text-align:left;">Keto</td>
 * 	<td><b>K</b></td>
 * 	<td></td>
 * 	<td></td>
 * 	<td>G</td>
 * 	<td>T</td>
 *     </tr>
 *     <tr bgcolor="#e8e8e8">
 * 	<td style="text-align:left;">Pu<i>r</i>ine</td>
 * 	<td><b>R</b></td>
 * 	<td>A</td>
 * 	<td></td>
 * 	<td>G</td>
 * 	<td></td>
 *     </tr>
 *     <tr bgcolor="#e8e8e8">
 * 	<td style="text-align:left;">P<i>y</i>rimidine</td>
 * 	<td><b>Y</b></td>
 * 	<td></td>
 * 	<td>C</td>
 * 	<td></td>
 * 	<td>T</td>
 *     </tr>
 *     <tr>
 * 	<td style="text-align:left;">Not A</td>
 * 	<td><b>B</b>
 * 	</td><td rowspan="4">3</td>
 * 	<td></td>
 * 	<td>C</td>
 * 	<td>G</td>
 * 	<td>T</td>
 *     </tr>
 *     <tr>
 * 	<td style="text-align:left;">Not C</td>
 * 	<td><b>D</b></td>
 * 	<td>A</td>
 * 	<td></td>
 * 	<td>G</td>
 * 	<td>T</td>
 *     </tr>
 *     <tr>
 * 	<td style="text-align:left;">Not G</td>
 * 	<td><b>H</b></td>
 * 	<td>A</td>
 * 	<td>C</td>
 * 	<td></td>
 * 	<td>T</td>
 *     </tr>
 *     <tr>
 * 	<td align="left">Not T</td>
 * 	<td><b>V</b></td>
 * 	<td>A</td>
 * 	<td>C</td>
 * 	<td>G</td>
 * 	<td></td>
 *     </tr>
 *     <tr bgcolor="#e8e8e8">
 * 	<td style="text-align:left;">A<i>n</i>y one base
 * 	</td>
 * 	<td><b>N</b></td>
 * 	<td>4</td>
 * 	<td>A</td>
 * 	<td>C</td>
 * 	<td>G</td>
 * 	<td>T</td>
 *     </tr>
 *     <tr>
 * 	<td style="text-align:left;">Zero</td>
 * 	<td><b>Z</b></td>
 * 	<td>0</td>
 * 	<td></td>
 * 	<td></td>
 * 	<td></td>
 * 	<td></td>
 *     </tr>
 * </table>
 */
public class IupacNtSubstitutionMatrix extends AbstractSubstitutionMatrix {

    private int[][] matrix;

    private BlosumMatrices blosumMat;

    private static final Map<Character,Integer> resLookup;

    static {
        resLookup = new HashMap<Character,Integer>();
        char res[] = {'A', 'T', 'G', 'C', 'S', 'W', 'R', 'Y', 'K', 'M', 'B', 'V', 'H', 'D', 'N'};
        for (int i = 0; i < res.length; i++) {
            resLookup.put(res[i], i);
        }
    }

    public IupacNtSubstitutionMatrix() {
        this(-15, -1);
    }

    public IupacNtSubstitutionMatrix(int gapopen, int gapextend) {
        super(gapopen, gapextend);
        this.matrix = new int[resLookup.size()][resLookup.size()];
        this.readFromResource();
    }

    @Override
    public int score(char a, char b) {
        return this.matrix[this.getResidueIdx(a)][this.getResidueIdx(b)];
    }

    private int getResidueIdx(char res) {
        return resLookup.get(res);
    }

    private void readFromResource() {
        InputStream in = getClass().getResourceAsStream("/iupac/IUPAC_matrix.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        reader.lines()
                .filter(l -> (l.charAt(0) != '#' && l.charAt(0) != ' '))
                .forEach(l -> {
                    String[] row = l.split("\\s+");
                    char rowRes = row[0].charAt(0);
                    for (int j = 1; j < row.length; j++) {
                        int val = Integer.parseInt(row[j]);
                        this.matrix[this.getResidueIdx(rowRes)][j - 1] = val;
                    }
                });
    }

}
