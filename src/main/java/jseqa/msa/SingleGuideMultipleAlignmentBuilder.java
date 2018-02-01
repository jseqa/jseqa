package jseqa.msa;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import jseqa.alignment.Alignment;

/**
 * Builds a multiple alignment one sequence at a time, using a single guide sequence as
 * a reference point. Indels are inserted as needed; all pairwise alignments are with
 * respect to this same sequence - a star alignment.
 *
 */
public class SingleGuideMultipleAlignmentBuilder implements MultipleAlignmentBuilder {

    private List<StringBuilder> rows;

    /**
     * Constructor that initializes the builder with the top row being the guide sequence.
     * @param initAln
     */
    public SingleGuideMultipleAlignmentBuilder(Alignment initAln) {
        this.rows = new LinkedList<StringBuilder>();
        this.rows.add(new StringBuilder(initAln.top()));
        this.rows.add(new StringBuilder(initAln.bottom()));
    }

    /**
     * Add an {@code Alignment} with the top row as the guide sequence.
     * @param aln
     */
    public void add(Alignment aln) {
        this.add(aln.top(), aln.bottom());
    }

    /**
     * Add a sequence from a pairwise alignment, where {@code topRow} is the guide sequence.
     * @param topRow
     * @param newRow
     */
    public void add(String topRow, String newRow) {
        StringBuilder rStr = new StringBuilder();
        int msaIdx = 0;
        for (int i = 0; i < newRow.length(); i++) {
            if (topRow.charAt(i) != '-' && newRow.charAt(i) != '-') {
                if (rows.get(0).charAt(msaIdx) == '-') {
                    int j = i;
                    while (rows.get(0).charAt(msaIdx) == '-') {
                        rStr.append('-');
                        msaIdx++;
                    }
                    rStr.append(newRow.charAt(i));
                } else {
                    rStr.append(newRow.charAt(i));
                    msaIdx++;
                }

            } else if (topRow.charAt(i) != '-' && newRow.charAt(i) == '-') {
                rStr.append(newRow.charAt(i));
                msaIdx++;
            } else if (topRow.charAt(i) == '-' 
                       && newRow.charAt(i) != '-' 
                       && i == (this.rows.get(0).length() - 1) ) {
                // don't add new row if not at the end of the alignment yet
                rStr.append(newRow.charAt(i));
                msaIdx++;
            } else {
                rStr.append(newRow.charAt(i));
                // make new column in existing rows and add indel
                int currCol = rStr.length() - 1;
                for (int j = 0; j < this.rows.size(); j++) {
                    this.rows.get(j).insert(currCol, '-');
                }
                msaIdx++;
            }
        }
        if (rStr.length() < this.rows.get(0).length()) {
            for (int i = rStr.length(); i < this.rows.get(0).length(); i++) {
                rStr.append('-');
            }
        }
        this.rows.add(rStr);
    }

    /**
     * Outputs to a {@code MultipleAlignment}.
     * @return {@code MultipleAlignment} of current state.
     */
    public MultipleAlignment toMultipleAlignment() {
        List<String> currRows = this.rows.stream()
                .map(r -> r.toString())
                .collect(Collectors.toList());
        return new MultipleAlignment(currRows);
    }
}
