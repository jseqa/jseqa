package jseqa.fastq;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
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
     * Factory pattern for creating entry readers.
     */
    interface ReaderFactory<T> {
        /**
         * Creates an instance of reader from a file of sequences
         * @param seqFile
         * @return
         * @throws FileNotFoundException
         */
        T create(Path seqFile) throws IOException;
    }

    /**
     * Maximum sequence length from the maximum int entries in a fq file.
     * @param fqFile
     * @return maximum length or -1 if fails to read from file.
     * @throws IOException
     */
    public static int fastqMaxSequenceLength(Path fqFile) {
        return FastaUtils.estimateMaxSequenceLength(fqFile,
                FastqReader::new,
                Integer.MAX_VALUE);
    }


    /**
     * Estimates maximum sequence length from first 5000 entries in a fq file.
     * @param fqFile
     * @return maximum length or -1 if fails to read from file.
     * @throws IOException
     */
    public static int estimateFastqMaxSequenceLength(Path fqFile) {
        return FastaUtils.estimateMaxSequenceLength(fqFile,
                FastqReader::new,
                5000);
    }

    /**
     * Estimates maximum sequence length from a sequence file limited to the first few entries.
     * @param seqFile file with entries.
     * @param limit first few entries to screen.
     * @return maximum length or -1 if fails to read from file.
     */
    static <T extends AbstractSequenceReader & Iterable<? extends Entry>> int estimateMaxSequenceLength(Path seqFile,
                                                                                            ReaderFactory<T> readerFactory,
                                                                                            int limit) {
        try (T fr = readerFactory.create(seqFile)) {
            return StreamSupport.stream(fr.spliterator(), false)
                    .limit(limit)
                    .mapToInt(ent -> ent.sequence().length())
                    .max()
                    .getAsInt();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Partition a FASTA file into equal sized partitions based on entry index. Returns a list of 
     * {@link FastaReader} objects, one for each interval.
     * @param faFile {@code Path} of FASTA file to partition
     * @param numParts int number of partitions
     * @return stream of {@code IntervalFastaReaders} that iterate over non-overlapping entries.
     */
    public static Stream<IntervalFastaReader> partitionIntoIntervals(Path faFile, int numParts) {
        try {
            return FastaUtils.partitionReaderIntoIntervals((i) -> new IntervalFastaReader(faFile, i),
                    IntervalFastaReader.numEntries(faFile),
                    numParts);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Partition entry FASTQ file into equal sized partitions based on entry index. Returns a list of
     * {@link IntervalFastqReader} objects, one for each interval.
     * @param fqFile {@code Path} of FASTQ file to partition
     * @param numParts int number of partitions
     * @return stream of {@code IntervalFastqReaders} that iterate over non-overlapping entries.
     */
    public static Stream<IntervalFastqReader> partitionFastqIntoIntervals(Path fqFile, int numParts) {
        try {
            return FastaUtils.partitionReaderIntoIntervals((i) -> new IntervalFastqReader(fqFile, i),
                    FastqReader.numEntries(fqFile),
                    numParts);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Partition paired entry FASTQ file into equal sized partitions based on entry index. Returns a list of
     * {@link IntervalFastqReader} objects, one for each interval.
     * @param fq1File {@code Path} of R1 FASTQ file to partition
     * @param fq2File {@code Path} of R2 FASTQ file to partition
     * @param numParts int number of partitions
     * @return stream of {@code IntervalFastqReaders} that iterate over non-overlapping entries.
     */
    public static Stream<IntervalPairedFastqReader> partitionPairedFastqIntoIntervals(Path fq1File,
                                                                                Path fq2File,
                                                                                int numParts) {
        try {
            return FastaUtils.partitionReaderIntoIntervals((i) -> new IntervalPairedFastqReader(fq1File, fq2File, i),
                    FastqReader.numEntries(fq1File),
                    numParts);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Factory pattern for creating readers for specific intervals.
     */
    interface IntervalReaderFactory<T> {
        /**
         * Creates an instance of reader for a specific interval.
         * @param entInt
         * @return
         * @throws FileNotFoundException
         */
        T create(Interval entInt) throws FileNotFoundException;
    }

    /**
     * Partitions a multi-entry file generated from a factory into partitions where each partition
     *  has an equally sized range of consecutive entry indices.
     * @param factory
     * @param numEntries
     * @param numParts
     * @param <T> a reader for entries (i.e., IntervalFastqReader, IntervalFastaReader, IntervalPairedFastq).
     * @return
     */
    private static <T> Stream<T> partitionReaderIntoIntervals(IntervalReaderFactory<T> factory,
                                                              int numEntries,
                                                              int numParts) {

        final int intval = (int) Math.ceil((double)numEntries / (double)numParts);

        return IntStream.iterate(0, i -> i + intval)
                .limit(numParts)
                .mapToObj(i -> {
                    try {
                        return factory.create(new Interval(i, i + intval));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    private FastaUtils() {}
}
