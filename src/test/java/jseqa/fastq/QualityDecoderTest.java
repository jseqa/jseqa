package jseqa.fastq;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QualityDecoderTest {

	@Test
	public void testDecodeQualitiesIlmn18() {
		String ilmn18Range = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHI";
		double[] q18 = QualityDecoder.decodeQualities(ilmn18Range, FastqQualityEncoding.ILLUMINA18);
		for(int i = 0; i < q18.length; i++) {
			assertEquals((double)i, q18[i], 0.00000001);
		}
	}

	@Test
	public void testDecodeQualitiesIlmn13() {
		String ilmn13Range = "@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefgh";
		double[] q13 = QualityDecoder.decodeQualities(ilmn13Range, FastqQualityEncoding.ILLUMINA13);
		for(int i = 0; i < q13.length; i++) {
			assertEquals((double)i, q13[i], 0.00000001);
		}
	}
	
}
