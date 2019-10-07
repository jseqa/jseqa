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
        IntStream.iterate(frame, i -> i + 3)
            .limit(this.seq().length() / 3)
            .filter(i -> (i + 3) <= this.seq().length())
            .forEach(i -> {
                String codon = this.seq().substring(i, i + 3);
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
                .mapToObj(ch -> "" + NtTable.reverseNt((char)ch))
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
    
    /**
     * Compute the melting temperature (Tm). Two different formulas are used: one for sequences 
     * less than 14, another for those larger.
     * @return double Tm of sequence.
     */
    public double meltingTemp() {
        double countA = (double) this.seq().chars().filter(val -> ((char)val) == 'A').count();
        double countC = (double) this.seq().chars().filter(val -> ((char)val) == 'C').count();
        double countG = (double) this.seq().chars().filter(val -> ((char)val) == 'G').count();
        double countT = (double) this.seq().chars().filter(val -> ((char)val) == 'T').count();
        
        if (this.seq().length() < 14) {
            // uses the Wallace rule https://www.ncbi.nlm.nih.gov/pubmed/158748
            return (2.0 * (countA + countT)) + (4.0 * (countG + countC));
        } else {
            return 64.9 + 41.0 * (countG + countC - 16.4) / (countA + countT + countG + countC);
        }
    }

}
