package jseqa.alignment.substitution;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import jseqa.util.Pair;

public class CustomAaSubstitutionMatrixTest {

    @Test
    public void testCustomAaSubstitutionMatrix() {
        Map<Pair<Character,Character>,Integer> pairs = new HashMap<Pair<Character,Character>,Integer>();
        char[] alphabet = {'A', 'C', 'D', 'E', 'F'};
        for (int i = 0; i < alphabet.length; i++) {
            for (int j = 0; j < alphabet.length; j++) {
                int val = (i == j ? 10 : -4);
                pairs.put(new Pair<Character,Character>(alphabet[i], alphabet[j]), val);
            }
        }
        CustomAaSubstitutionMatrix cmat = new CustomAaSubstitutionMatrix(pairs, -4, -2);
        
        for (int i = 0; i < alphabet.length; i++) {
            for (int j = 0; j < alphabet.length; j++) {
                int val = (i == j ? 10 : -4);
                assertEquals(val, cmat.score(alphabet[i], alphabet[j]));
            }
        }
    }

}
