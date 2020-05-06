package jseqa.fastq;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class QualityEntryTest {

    private static final double EPSILON = 0.00000001;

    QualityEntry qe;
    String ilmn13Range = "@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefgh";
    String seq;

    @Before
    public void init() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ilmn13Range.length(); i++) {
            sb.append('A');
        }
        seq = sb.toString();
        qe = new QualityEntry("id1", seq, ilmn13Range, FastqQualityEncoding.ILLUMINA13);
    }

    @Test
    public void testConstructors() {
        QualityEntry lclQe = new QualityEntry(qe.id, qe.sequence(), qe.qualityString());
        double[] decode1 = lclQe.qualities();
        double[] decode2 = lclQe.qualities(FastqQualityEncoding.ILLUMINA18);
        // check default encoding used
        assertEquals(decode1.length, decode2.length);
        for (int i = 0; i < decode1.length; i++) {
            assertEquals(decode1[i], decode2[i], 0.000001);
        }
    }

    @Test
    public void testToFasta() {
        String expEnt = ">id1\n" + seq;
        assertEquals(expEnt, qe.toFasta());
    }

    @Test
    public void testQualities() {
        double[] quals = qe.qualities();
        double[] expQuals = QualityDecoder.decodeQualities(ilmn13Range, 
                                                           FastqQualityEncoding.ILLUMINA13);
        for (int i = 0; i < quals.length; i++) {
            assertEquals(expQuals[i], quals[i], EPSILON);
        }
    }

    @Test
    public void testToFastq() {
        String expEnt = "@id1\n" + seq + "\n+\n" + this.ilmn13Range;
        assertEquals(expEnt, qe.toFastq());
    }
    
    @Test
    public void testGenericQuality() {
        double[] quals = qe.qualities();
        double[] altQuals = qe.qualities(FastqQualityEncoding.ILLUMINA13);
        for (int i = 0; i < quals.length; i++) {
            assertEquals(quals[i], altQuals[i], EPSILON);
        }
    }

}
