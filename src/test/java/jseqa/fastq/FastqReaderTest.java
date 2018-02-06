package jseqa.fastq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FastqReaderTest {

    URL sampleUrl = null;

    @Before
    public void init() {
        // sample.fq is from HOMER website
        sampleUrl = ClassLoader.getSystemResource("sample.fq");
    }

    @Test
    public void testReadGzip() throws FileNotFoundException {
        URL gzsampleUrl = ClassLoader.getSystemResource("sample.fq.gz");
        FastqReader fq = new FastqReader(Paths.get(gzsampleUrl.getPath()), FastqQualityEncoding.ILLUMINA13);
        int num = 0;
        for(QualityEntry entry : fq) {
            assertEquals(50, entry.sequence().length());
            assertEquals(50, entry.qualityString().length());
            double[] quals = entry.qualities();
            assertEquals(50, quals.length);

            assertEquals(40.0, quals[1], 0.000001);
            assertEquals(40.0, quals[2], 0.000001);
            assertEquals(40.0, quals[3], 0.000001);
            num++;
        }
        assertEquals(3, num);
    }

    @Test
    public void testSample() throws FileNotFoundException {
        FastqReader fq = new FastqReader(Paths.get(sampleUrl.getPath()), FastqQualityEncoding.ILLUMINA13);
        int num = 0;
        for(QualityEntry entry : fq) {
            assertEquals(50, entry.sequence().length());
            assertEquals(50, entry.qualityString().length());
            double[] quals = entry.qualities();
            assertEquals(50, quals.length);

            assertEquals(40.0, quals[1], 0.000001);
            assertEquals(40.0, quals[2], 0.000001);
            assertEquals(40.0, quals[3], 0.000001);
            num++;
        }
        assertEquals(3, num);
    }

    @Test
    public void testNumEntries() throws FileNotFoundException {
        assertEquals(3, FastqReader.numEntries(Paths.get(sampleUrl.getPath())));
    }

    @Test(expected = FileNotFoundException.class)
    public void testNumEntriesFail() throws FileNotFoundException {
        assertEquals(-1, FastqReader.numEntries(Paths.get("blahblahblah.fa")));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNext() throws IOException {
        String fakeSeq = "AAAAAAAAAA";
        String fakeQuals = "999999999";
        File tmpFile = null;
        try { 
            tmpFile = Files.createTempFile("tmp_fakes", ".fq").toFile();
            tmpFile.deleteOnExit();
            PrintWriter pout = new PrintWriter(tmpFile);
            pout.println("@fakeEntry");
            pout.println(fakeSeq);
            pout.println("+");
            pout.println(fakeQuals);
            pout.close();
        } catch (IOException exp) {
            exp.printStackTrace();
        }
        
        try (FastqReader fq = new FastqReader(tmpFile)) {
            QualityEntry ent = fq.nextEntry();
            assertTrue(ent != null);
        } catch (IOException e) {
            throw e;
        }
    }
}
