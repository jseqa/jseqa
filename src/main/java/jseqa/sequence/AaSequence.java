package jseqa.sequence;

/**
 * Represents an amino acid sequence and allows for operations on residues.
 *
 */
public class AaSequence extends AbstractSequence {

    public AaSequence(String seq) {
        super(seq);
    }

    public boolean containsStop() {
        return this.seq().contains("*");
    }

    /**
     * Computes the molecular weight of the sequence of residues, computed by the sum of
     * all amino acids plus H2O. 
     * @return double weight of seq.
     */
    public double molecularWeight() {
        return this.seq().chars()
                .mapToDouble(ch -> AaResidueTable.residueWeight((char)ch))
                .sum()
                + (ElementMasses.H.monoisotopicMass()*2.0 + ElementMasses.O.monoisotopicMass());
    }

}
