package jseqa.fastq;

/**
 * Entry from a FASTQ file containing quality values
 *
 */
public class QualityEntry extends Entry {
	
	/** Quality values */
	protected String quals;
	
	/** Default encoding is set to Illumina 1.8 */
	protected static final FastqQualityEncoding DEFAULT_ENCODING = FastqQualityEncoding.ILLUMINA18;
	
	/** Encoding type used for quality values */
	protected FastqQualityEncoding encoding; 
	
	public QualityEntry(String id, String seq, String quals) {
		this(id, seq, quals, -1, DEFAULT_ENCODING);
	}
	
	public QualityEntry(String id, String seq, String quals, FastqQualityEncoding enc) {
		this(id, seq, quals, -1, enc);
	}
	
	public QualityEntry(String id, String seq, String quals, int idx, FastqQualityEncoding enc) {
		super(id, seq, idx);
		this.quals = quals;
		this.encoding = enc;
	}
	
	/**
	 * Return the quality values as a {@code String}
	 * @return
	 */
	public String qualityString() {
		return this.quals;
	}
	
	/**
	 * Decodes the quality values using the encoding specified in the constructor, if not, default is used.
	 * @return {@code double[]} quality values encoded in string.
	 */
	public double[] qualities() {
		return QualityDecoder.decodeQualities(this.quals, this.encoding);
	}
	
	/**
	 * Decodes the quality values using {@code encoding}
	 * @param encoding {@code FastqQualityEncoding} denoting the encoding
	 * @return {@code double[]} quality values encoded in string.
	 */
	public double[] qualities(FastqQualityEncoding encoding) {
		return QualityDecoder.decodeQualities(this.quals, encoding);
	}
	
	/**
	 * Output {@code QualityEntry} in FASTA format
	 */
	public String toFasta() {
		return ">" + this.id + "\n" + this.seq;
	}
	
	/**
	 * Output {@code QualityEntry} in FASTQ format
	 * @return
	 */
	public String toFastq() {
		return "@" + this.id + "\n" + this.seq + "\n+\n" + this.quals;
	}
}
