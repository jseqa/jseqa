package jseqa.alignment;

import jseqa.alignment.substitution.SubstitutionMatrix;

/**
 * Utility methods for performing banded alignments. See
 * {@link BandedMatrixFiller} for an explanation.
 *
 */
public class BandedAlignments {

    /**
     * Globally align two sequences within {@code bandwidth}.
     * @param seqA {@code String} first sequence to align.
     * @param seqB {@code String} second sequence to align.
     * @param scoreMat {@code ScoringMatrix} used to score match/mismatch/indel.
     * @param bandwidth int distance from diagonal to fill in values of matrix.
     * @return Global {@code Alignment} of two sequences within {@code bandwidth}.
     */
    public static Alignment globalBasic(String seqA, 
            String seqB, 
            SubstitutionMatrix scoreMat,
            int bandwidth) {
        return BandedAlignments.globalBasic(seqA, 
                seqB, 
                new AlignmentMatrixScore(seqA.length() + 5, seqB.length() + 5), 
                new AlignmentMatrixBacktrack(seqA.length() + 5, seqB.length() + 5), 
                scoreMat, 
                bandwidth);
    }

    /**
     * Globally align two sequences within {@code bandwidth}.
     * @param seqA {@code String} first sequence to align.
     * @param seqB {@code String} second sequence to align.
     * @param alignScoreMat
     * @param alignPtrMat
     * @param scoreMat {@code ScoringMatrix} used to score match/mismatch/indel.
     * @param bandwidth int distance from diagonal to fill in values of matrix.
     * @return Global {@code Alignment} of two sequences within {@code bandwidth}.
     */
    public static Alignment globalBasic(String seqA, 
            String seqB, 
            AlignmentMatrixScore alignScoreMat,
            AlignmentMatrixBacktrack alignPtrMat,
            SubstitutionMatrix scoreMat,
            int bandwidth) {
        GlobalAlignment galn = new GlobalAlignment(seqA, seqB, scoreMat);
        validateGlobalBand(galn, seqA, seqB, bandwidth);
        galn.setAlignmentMatrix(alignScoreMat, alignPtrMat);
        galn.initMatrix();
        galn.bandedFillMatrix(bandwidth);
        return galn.backtrack(bandwidth);
    }

    /**
     * Globally align two sequences within {@code bandwidth} using affine gap penalties.
     * @param seqA {@code String} first sequence to align.
     * @param seqB {@code String} second sequence to align.
     * @param scoreMat {@code ScoringMatrix} used to score match/mismatch/indel.
     * @param bandwidth int distance from diagonal to fill in values of matrix.
     * @return Global {@code Alignment} of two sequences within {@code bandwidth}
     * with affine gap penalties.
     */
    public static Alignment affineGlobalNt(String seqA, 
            String seqB, 
            SubstitutionMatrix scoreMat,
            int bandwidth) {
        return BandedAlignments.affineGlobal(seqA, 
                seqB, 
                new AffineAlignmentMatrixScore(seqA.length() + 5, seqB.length() + 5),
                new AffineAlignmentMatrixBacktrack(seqA.length() + 5, seqB.length() + 5),
                scoreMat, 
                bandwidth);
    }

    /**
     * Globally align two sequences within {@code bandwidth} using affine gap penalties.
     * @param seqA {@code String} first sequence to align.
     * @param seqB {@code String} second sequence to align.
     * @param alignScoreMat
     * @param alignPtrMat
     * @param scoreMat {@code ScoringMatrix} used to score match/mismatch/indel.
     * @param bandwidth int distance from diagonal to fill in values of matrix.
     * @return Global {@code Alignment} of two sequences within {@code bandwidth}
     * with affine gap penalties.
     */
    public static Alignment affineGlobal(String seqA, 
            String seqB, 
            AffineAlignmentMatrixScore alignScoreMat,
            AffineAlignmentMatrixBacktrack alignPtrMat,
            SubstitutionMatrix scoreMat,
            int bandwidth) {
        AffineGlobalAlignment galn = new AffineGlobalAlignment(seqA, seqB, scoreMat);
        validateGlobalBand(galn, seqA, seqB, bandwidth);
        galn.setAlignmentMatrix(alignScoreMat, alignPtrMat);
        galn.initMatrix();
        galn.bandedFillMatrix(bandwidth);
        return galn.backtrack(bandwidth);
    }

    /**
     * Locally align two sequences within {@code bandwidth} using affine gap penalties.
     * @param seqA {@code String} first sequence to align.
     * @param seqB {@code String} second sequence to align.
     * @param scoreMat substitution matrix used to score the alignment.
     * @param bandwidth distance from the diagonal to fill.
     * @return Local alignment constrained to the requested band.
     */
    public static Alignment affineLocalNt(String seqA,
            String seqB,
            SubstitutionMatrix scoreMat,
            int bandwidth) {
        return BandedAlignments.affineLocal(seqA,
                seqB,
                new AffineAlignmentMatrixScore(seqA.length() + 5, seqB.length() + 5),
                new AffineAlignmentMatrixBacktrack(seqA.length() + 5, seqB.length() + 5),
                scoreMat,
                bandwidth);
    }

    /**
     * Locally align two sequences within {@code bandwidth} using affine gap penalties.
     * @param seqA {@code String} first sequence to align.
     * @param seqB {@code String} second sequence to align.
     * @param alignScoreMat score matrices used as working storage.
     * @param alignPtrMat deprecated pointer matrices retained for API compatibility.
     * @param scoreMat substitution matrix used to score the alignment.
     * @param bandwidth distance from the diagonal to fill.
     * @return Local alignment constrained to the requested band.
     */
    public static Alignment affineLocal(String seqA,
            String seqB,
            AffineAlignmentMatrixScore alignScoreMat,
            AffineAlignmentMatrixBacktrack alignPtrMat,
            SubstitutionMatrix scoreMat,
            int bandwidth) {
        AffineLocalAlignment laln = new AffineLocalAlignment(seqA, seqB, scoreMat);
        laln.setAlignmentMatrix(alignScoreMat, alignPtrMat);
        laln.initMatrix();
        laln.bandedFillMatrix(bandwidth);
        return laln.backtrack(bandwidth);
    }
    
    /**
     * Locally align two sequences within {@code bandwidth}.
     * @param seqA {@code String} first sequence to align.
     * @param seqB {@code String} second sequence to align.
     * @param scoreMat {@code ScoringMatrix} used to score match/mismatch/indel.
     * @param bandwidth int distance from diagonal to fill in values of matrix.
     * @return Local {@code Alignment} of two sequences within {@code bandwidth}.
     */
    public static Alignment localBasic(String seqA, 
            String seqB, 
            SubstitutionMatrix scoreMat,
            int bandwidth) {
        return BandedAlignments.localBasic(seqA, 
                seqB,
                new AlignmentMatrixScore(seqA.length() + 5, seqB.length() + 5),
                new AlignmentMatrixBacktrack(seqA.length() + 5, seqB.length() + 5),
                scoreMat, 
                bandwidth);	
    }

    /**
     * Locally align two sequences within {@code bandwidth}.
     * @param seqA {@code String} first sequence to align.
     * @param seqB {@code String} second sequence to align.
     * @param alignScoreMat
     * @param alignPtrMat
     * @param scoreMat {@code ScoringMatrix} used to score match/mismatch/indel.
     * @param bandwidth int distance from diagonal to fill in values of matrix.
     * @return Local {@code Alignment} of two sequences within {@code bandwidth}.
     */
    public static Alignment localBasic(String seqA, 
            String seqB, 
            AlignmentMatrixScore alignScoreMat,
            AlignmentMatrixBacktrack alignPtrMat,
            SubstitutionMatrix scoreMat,
            int bandwidth) {
        LocalAlignment laln = new LocalAlignment(seqA, seqB, scoreMat);
        laln.setAlignmentMatrix(alignScoreMat, alignPtrMat);
        laln.initMatrix();
        laln.bandedFillMatrix(bandwidth);
        return laln.backtrack(bandwidth);
    }
    
    private static void validateGlobalBand(BandedMatrixFiller bandedAlignment,
            String seqA,
            String seqB,
            int bandwidth) {
        if (bandwidth < 1) {
            throw new IllegalArgumentException("Bandwidth must be at least 1");
        }
        if (!seqA.isEmpty() && !seqB.isEmpty()
                && !bandedAlignment.isInsideBand(seqA.length(), seqB.length(),
                        seqB.length() + 1, bandwidth)) {
            throw new IllegalArgumentException("Band does not contain the global alignment terminal cell");
        }
    }

    private BandedAlignments() {}
}
