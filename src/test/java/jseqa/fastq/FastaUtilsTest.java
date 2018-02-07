package jseqa.fastq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class FastaUtilsTest {

	Path faFile = null;
	
	@Before
	public void init() {
		URL fa_ighd = ClassLoader.getSystemResource("rabbit_ighd.fa");
		faFile = Paths.get(fa_ighd.getPath());
	}
	
	@Test
	public void testReadFastaIntoMap() throws IOException {
		Map<String,String> ighd = FastaUtils.readFastaIntoMap(faFile);
		assertEquals(11, ighd.size());
		assertTrue(ighd.containsKey("IGHD6-1*01"));
	}

	@Test
	public void testPartitionIntoIntervals() {
		List<FastaReader> fas = FastaUtils.partitionIntoIntervals(faFile, 2).collect(Collectors.toList());
		Map<String,String> part1 = FastaUtils.readFastaIntoMap(fas.get(0));
		Map<String,String> part2 = FastaUtils.readFastaIntoMap(fas.get(1));
		
		assertEquals(6, part1.size());
		assertEquals(5, part2.size());
		
		Set<String> keys = new HashSet<String>();
		keys.addAll(part1.keySet());
		keys.addAll(part2.keySet());
		assertEquals(11, keys.size());
	}

	@Test
	public void testReadIds() throws IOException {
	    List<String> rabD = FastaUtils.readFastaIds(faFile);
	    assertEquals(11, rabD.size());
	    assertEquals("IGHD1-1*01", rabD.get(0));
	    assertEquals("IGHD2-1*01", rabD.get(1));
	    assertEquals("IGHD8-1*01", rabD.get(10));
	}
}
