package jseqa.fastq;

/**
 * Different FASTQ encodings, as seen in the FASTQ 
 * <a href="https://en.wikipedia.org/wiki/FASTQ_format#Encoding">Wikipedia</a> entry.
 *
 */
public enum FastqQualityEncoding {
	/** Encodes from (0,93) using ASCII 33 to 126, raw are expected from (0,93) */
	SANGER(33),
	
	/** Encodes from (0,62) using ASCII 64 to 126, raw are expected from (0,40) */
	ILLUMINA13(64),
	
	/** Same as Illumina1.3, but raw range is (3,41) */
	ILLUMINA15(64),
	
	/** Returned to Sanger, Phred+33, raw range is (0,40) */
	ILLUMINA18(33);
	
	private final int offsetIdx;
	
	FastqQualityEncoding(int offset) {
		this.offsetIdx = offset;
	}
	
	public int offset() {
		return this.offsetIdx;
	}
	
	public int decode(int ch) {
		return (ch - this.offsetIdx);
	}
}
