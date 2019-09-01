package jseqa.alignmentfree;

import static org.junit.Assert.*;

import org.junit.Test;

public class SimilarityMeasuresTest {

    private static final double DELTA = 0.000001;

    private double[] a = {1.0, 1.0, 0.0};
    private double[] b = {0.0, 0.0, 1.0};

    @Test
    public void testDotProduct() {
        assertEquals(0.0, SimilarityMeasures.dotProduct(a, b), DELTA);
        assertEquals(2.0, SimilarityMeasures.dotProduct(a, a), DELTA);
        assertEquals(1.0, SimilarityMeasures.dotProduct(b, b), DELTA);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDotProductInvalid() {
        double[] c = {1.0, 0.0};
        SimilarityMeasures.dotProduct(a, c);
    }

    @Test
    public void testCosine() {
        assertEquals(0.0, SimilarityMeasures.cosine(a, b), DELTA);
        assertEquals(1.0, SimilarityMeasures.cosine(a, a), DELTA);
        assertEquals(1.0, SimilarityMeasures.cosine(b, b), DELTA);

        double[] a2 = {1.0, 2.0};
        double[] b2 = {2.0, 3.0};
        assertEquals(0.99227787, SimilarityMeasures.cosine(a2, b2), DELTA);
    }

    @Test
    public void testEntropy() {
        double[] unif = {0.5, 0.5};
        assertEquals(1.0, SimilarityMeasures.entropy(unif), DELTA);
    }
    
    @Test
    public void testKlDivergence() {
        double[] p = {0.36, 0.48, 0.16};
        double[] q = {1.0/3.0, 1.0/3.0, 1.0/3.0};
        assertEquals(0.0852996, SimilarityMeasures.kullbackLeiblerLn(p, q), DELTA);
        assertEquals(0.09745501, SimilarityMeasures.kullbackLeiblerLn(q, p), DELTA);
        
        assertEquals(0.1230613, SimilarityMeasures.kullbackLeibler(p, q), DELTA);
        assertEquals(0.1405979, SimilarityMeasures.kullbackLeibler(q, p), DELTA);
    }

}
