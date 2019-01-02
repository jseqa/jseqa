package jseqa.fastq;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.Before;
import org.junit.Test;

public class FastaReaderTest {

    URL sampleUrl = null;

    @Before
    public void init() {
        sampleUrl = ClassLoader.getSystemResource("sample.fa");
    }

    @Test
    public void test() throws IOException {
        try (FastaReader fa = new FastaReader(Paths.get(sampleUrl.getPath()))) {
            int num = 0;
            for (Entry entry : fa) {
                assertEquals(50, entry.sequence().length());
                num++;
            }
            assertEquals(3, num);
        }
    }
    
    @Test
    public void testSingleEntryFasta() throws IOException {
        File singleEntryFa = Files.createTempFile("test_single_entry", ".fa").toFile();
        singleEntryFa.deleteOnExit();
        Entry testEnt = new Entry("id1", "ACGTACGTACGTACGT");
        PrintWriter pout = new PrintWriter(singleEntryFa);
        pout.println(testEnt.toFasta());
        pout.close();
        
        FastaReader fa = new FastaReader(singleEntryFa);
        List<Entry> entries = StreamSupport.stream(fa.spliterator(), false).collect(Collectors.toList());
        assertEquals(1, entries.size());
        assertEquals("id1", entries.get(0).id());
        assertEquals("ACGTACGTACGTACGT", entries.get(0).sequence());
    }

    @Test
    public void testNumEntries() throws FileNotFoundException {
        assertEquals(3, FastaReader.numEntries(Paths.get(sampleUrl.getPath())));
    }

    @Test(expected = FileNotFoundException.class)
    public void testNumEntriesFail() throws FileNotFoundException {
        assertEquals(-1, FastaReader.numEntries(Paths.get("blahblahblah.fa")));
    }
}
