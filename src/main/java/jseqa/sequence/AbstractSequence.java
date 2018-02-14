package jseqa.sequence;

/**
 * Represents a molecular sequence.
 */
public class AbstractSequence {

    private String seq;

    /**
     * Constructor.
     * @param seq {@code String} sequence to represent.
     */
    public AbstractSequence(String seq) {
        this.seq = seq;
    }

    /**
     * Get the sequence.
     * @return {@code String} sequence.
     */
    public String seq() {
        return this.seq;
    }
}
