package jseqa.sequence;

/**
 * Masses of elements, both monoisotopic and average. Taken from 
 * <a href="http://www.unimod.org/masses.html">Unimod</a>.
 *
 */
public enum ElementMasses {
    /** Hydrogen. */
    H(1.007825035, 1.00794),
    
    /** Carbon. */
    C(12.0, 12.0107),
    
    /** Nitrogen. */
    N(14.003074, 14.0067),
    
    /** Oxygen. */
    O(15.99491463, 15.9994);
    
    private final double monoisotopicMass;
    private final double averageMass;
    
    ElementMasses(double monoMass, double aveMass) {
        this.monoisotopicMass = monoMass;
        this.averageMass = aveMass;
    }
    
    public double monoisotopicMass() {
        return this.monoisotopicMass;
    }
    
    public double averageMass() {
        return this.averageMass;
    }
}
