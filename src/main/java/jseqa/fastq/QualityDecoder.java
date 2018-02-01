package jseqa.fastq;

/**
 * Decodes a FASTQ quality value string.
 *
 */
public class QualityDecoder {

    private FastqQualityEncoding encoding;

    public QualityDecoder(FastqQualityEncoding encoding) {
        this.encoding = encoding;
    }

    /**
     * Decode quality string to double[] array.
     * @return
     */
    public double[] decode(String qualities) {
        return QualityDecoder.decodeQualities(qualities, this.encoding);
    }

    /**
     * Decodes a string of quality values using the provided encoding type.
     * @param quals {@code String} of FASTQ quality values
     * @param encoding {@code FastqQualityEncoding} to use to decode.
     * @return
     */
    public static double[] decodeQualities(String quals, FastqQualityEncoding encoding) {
        return quals.chars()
                .mapToDouble(ch -> encoding.decode(ch))
                .toArray();
    }
}
