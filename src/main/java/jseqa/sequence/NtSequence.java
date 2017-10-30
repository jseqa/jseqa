package jseqa.sequence;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents a nucleotide sequence and allows for operations on a sequence.
 *
 */
public class NtSequence extends AbstractSequence {

	public NtSequence(String seq) {
		super(seq);
	}
	
	/**
	 * Translate the nucleotide sequence into the specified forward frame.
	 * @param frame int forward frame, 0, 1, or 2.
	 * @return {@code String} translated sequence in {@code frame}.
	 */
	public String translateFrame(int frame) {
		StringBuilder aaSeq = new StringBuilder(this.seq().length() / 3);
		IntStream.iterate(0, i -> i + 3)
				.limit(this.seq().length() / 3)
				.forEach(i -> {
					String codon = this.seq().substring(i, i+3);
					char res = CodonTable.translateCodon(codon);
					aaSeq.append(res);
				});
		return aaSeq.toString();
	}
	
	/**
	 * Translate sequence into all three forward frames.
	 * @return {@code String[]} forward frames
	 */
	public String[] threeFrameTranslate() {
		return new String[] {this.translateFrame(0), 
				this.translateFrame(1), 
				this.translateFrame(2)};
	}
	
	/**
	 * Reverse complement the sequence, e.g., ACGTTT &rarr; AAACGT.
	 * @return
	 */
	public NtSequence reverseComplement() {
		String compSeq = this.seq()
							 .chars()
							 .mapToObj(ch -> ""+NtTable.reverseNt((char)ch))
							 .collect(Collectors.joining());
		return new NtSequence(StringUtils.reverse(compSeq));
	}
	
	/**
	 * Return the {@code AaSequence} object of the translated nucleotide (in the first frame).
	 * @return {@code AaSequence} of nucleotide sequence translated in the first frame.
	 */
	public AaSequence toAaSequence() {
		return new AaSequence(this.translateFrame(0));
	}

}
