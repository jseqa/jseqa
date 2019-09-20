package jseqa.fastq;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import jseqa.util.Interval;

/**
 * Utility class for several useful FASTA/FASTQ reading operations.
 *
 */
public class FastaUtils {

    /**
     * Read in FASTA {@link Entry}s into a {@code List}.
     * @param faFile {@code Path} of FASTA file.
     * @return {@code List<Entry>} of all entries of FASTA.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List<Entry> readFastaEntries(Path faFile) throws FileNotFoundException, IOException {
        try (FastaReader fr = new FastaReader(faFile)) {
            return StreamSupport.stream(fr.spliterator(), false)
                    .map(entry -> entry)
                    .collect(Collectors.toList());
        }
    }
    
    /**
     * Read a FASTA file's Ids into a {@code List}.
     * @param faFile {@code Path} of FASTA file.
     * @return {@code List<String>} all Ids, in order, of {@code faFile}.
     * @throws IOException 
     */
    public static List<String> readFastaIds(Path faFile) throws IOException {
        try (FastaReader fr = new FastaReader(faFile)) {
            return StreamSupport.stream(fr.spliterator(), false)
                    .map(entry -> entry.id)
                    .collect(Collectors.toList());
        }
    }
    
    /**
     * Read a FASTA file into a map with ID &rarr; sequence.
     * @param fr {@code FastaReader} to load
     * @return {@code Map<String,String>} of ID &rarr; sequence.
     */
    public static Map<String,String> readFastaIntoMap(FastaReader fr) {
        return StreamSupport.stream(fr.spliterator(), false)
                .collect(Collectors.toMap(Entry::id, Entry::sequence));
    }

    /**
     * Read a FASTA file into a map with ID &rarr; sequence.
     * @param faFile {@code Path} of FASTA file
     * @return {@code Map<String,String>} of ID &rarr; sequence.
     * @throws IOException
     */
    public static Map<String,String> readFastaIntoMap(Path faFile) throws IOException {
        try (FastaReader fr = new FastaReader(faFile)) {
            return StreamSupport.stream(fr.spliterator(), false)
                    .collect(Collectors.toMap(Entry::id, Entry::sequence));
        }
    }

    /**
     * Partition a FASTA file into equal sized partitions based on entry index. Returns a list of 
     * {@link #FastaReader} objects, one for each interval.
     * @param faFile {@code Path} of FASTA file to partition
     * @param numParts int number of partitions
     * @return stream of {@code IntervalFastaReaders} that iterate over non-overlapping entries.
     */
    public static Stream<IntervalFastaReader> partitionIntoIntervals(Path faFile, int numParts) {
        int numEntries = -1;
        try {
            numEntries = FastaReader.numEntries(faFile);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        int intval = (int)Math.ceil((double)numEntries / (double)numParts);

        return IntStream.iterate(0, i -> i + intval)
                .limit(numParts)
                .mapToObj(i -> {
                    try {
                        return new IntervalFastaReader(faFile, new Interval(i, i + intval));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    private FastaUtils() {}
}
