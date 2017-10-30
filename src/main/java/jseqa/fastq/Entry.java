package jseqa.fastq;

/**
 * Entry from a FASTA file containing only ID and sequence.
 *
 */
public class Entry {

	/** ID found in first line of entry */
	protected String id;
	
	/** Sequence */
	protected String seq;
	
	/** Index of entry among entries in file */
	protected int idx;
	
	/**
	 * Constructor with default index.
	 * @param id {@code String} used as identifier.
	 * @param seq {@code String} of sequence.
	 */
	public Entry(String id, String seq) {
		this(id, seq, -1);
	}
	
	/**
	 * Constructor with index specified.
	 * @param id {@code String} used as identifier.
	 * @param seq {@code String} of sequence.
	 * @param idx int index.
	 */
	public Entry(String id, String seq, int idx) {
		this.id = id;
		this.seq = seq;
		this.idx = idx;
	}
	
	/**
	 * Gets the sequence as a {@code String}.
	 * @return {@code String} sequence.
	 */
	public String sequence() {
		return this.seq;
	}
	
	/**
	 * Gets the ID.
	 * @return {@code String} ID.
	 */
	public String id() {
		return this.id;
	}
	
	/**
	 * Gets the entry index;
	 * @return int index of entry.
	 */
	public int entryIdx() {
		return this.idx;
	}
	
	/**
	 * Output {@code QualityEntry} in FASTA format
	 * @return
	 */
	public String toFasta() {
		return ">" + this.id + "\n" + this.seq;
	}
}
