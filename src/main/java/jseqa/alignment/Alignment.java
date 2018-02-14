package jseqa.alignment;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * An immutable alignment between two sequences. Use {@see AlignmentBuilder} to build an alignment.
 *
 */
public final class Alignment {

    private final String topRow;

    private final String bottomRow;

    private final int score;

    /**
     * Constructor taking only the alignment itself, will use -1 for score.
     * @param toprow {@code String} top row of alignment.
     * @param bottomrow {@code String} bottom row of alignment.
     */
    public Alignment(String toprow, String bottomrow) {
        this(toprow, bottomrow, -1);
    }

    /**
     * Constructor taking both rows and the overall alignment score.
     * @param toprow {@code String} top row of alignment.
     * @param bottomrow {@code String} bottom row of alignment.
     * @param score int alignment score.
     */
    public Alignment(String toprow, String bottomrow, int score) {
        if (toprow.length() != bottomrow.length()) {
            throw new IllegalArgumentException("Alignment rows are not the same length!");
        }
        this.topRow = toprow;
        this.bottomRow = bottomrow;
        this.score = score;
    }

    /**
     * Get the top row of the alignment.
     * @return {@code String} of top row.
     */
    public String top() {
        return this.topRow;
    }

    /**
     * Get the bottom row of the alignment.
     * @return {@code String} of bottom row.
     */
    public String bottom() {
        return this.bottomRow;
    }

    /**
     * Get the score of the alignment.
     * @return int score of the alignment.
     */
    public int score() {
        return this.score;
    }

    /**
     * Get the number of columns in the alignment.
     * @return int number of columns.
     */
    public int numColumns() {
        return this.topRow.length();
    }

    /**
     * Computes the number of matching columns.
     * @return int number of columns that match.
     */
    public int numMatches() {
        return (int)IntStream.range(0, this.topRow.length())
                .boxed()
                .filter(i -> this.topRow.charAt(i) == this.bottomRow.charAt(i))
                .count();
    }

    /**
     * Computes the percent of matching columns.
     * @return double percent of columns that match.
     */
    public double percentMatch() {
        double num = (double)this.numMatches();
        double denom = (double)this.topRow.length();
        return (num / denom);
    }

    /**
     * {@inheritDoc}
     * <br>
     * Outputs 80 column alignment in pretty-print.
     */
    @Override
    public String toString() {
        return this.toString(80);
    }

    /**
     * Compiles a pretty-print {@code String} representation of the alignment with 
     * a maximum column width, positions past this wrap to a newline.
     * @param colWidth int maximum column width 
     * @return {@code String} of pretty-print alignment.
     */
    public String toString(int colWidth) {
        StringBuilder aln = new StringBuilder();
        String midRow = IntStream.range(0, this.topRow.length())
                .mapToObj(i -> (this.topRow.charAt(i) == this.bottomRow.charAt(i) ? "|" : "."))
                .collect(Collectors.joining());
        for (int i = 0; i < this.topRow.length(); i += colWidth) {
            if (i > 0) {
                aln.append("\n");
            }
            int lclWidth = ((i + colWidth) < this.topRow.length()) ? colWidth : (this.topRow.length()-i); 
            aln.append(this.topRow.substring(i, i + lclWidth) + "\n");
            aln.append(midRow.substring(i, i + lclWidth) + "\n");
            aln.append(this.bottomRow.substring(i, i + lclWidth) + "\n");
        }
        return aln.toString() + "\nscore:\t" + this.score();
    }
}
