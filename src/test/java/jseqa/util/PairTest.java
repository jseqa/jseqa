package jseqa.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class PairTest {

	Pair<String,String> s1 = new Pair<String,String>("ABC", "XYZ");
	Pair<String,String> s2 = new Pair<String,String>("ABC", "XYZ");
	Pair<String,String> s3 = new Pair<String,String>("ABC", "XYY");
	
	Pair<Integer,Integer> i1 = new Pair<Integer,Integer>(10, 20);
	Pair<Integer,Integer> i2 = new Pair<Integer,Integer>(10, 20);
	Pair<Integer,Integer> i3 = new Pair<Integer,Integer>(5, 20);
	
	@Test
	public void testEqualsObject() {
		assertEquals(s1, s2);
		assertNotEquals(s1, s3);
		
		assertEquals(s1, s2);
		assertNotEquals(s1, s3);
		
		assertEquals(s1, s1);
		assertEquals(s2, s2);
		
		assertNotEquals(s1, "test");
		
		assertEquals(0, s1.compareTo(s2));
	}

	@Test
	public void testHash() {
		Set<Pair<String,String>> stringHash = new HashSet<Pair<String,String>>();
		stringHash.add(s1);
		stringHash.add(s2);
		stringHash.add(s3);
		assertEquals(2, stringHash.size());
		
		for(int i = 0; i < 100; i++) {
			stringHash.add(s2);
		}
		assertEquals(2, stringHash.size());
	}

	@Test
	public void testGets() {
	    assertEquals("ABC", s1.first());
	    assertEquals("XYZ", s1.second());
	    
	    assertEquals(s1.key(), s1.first());
	    assertEquals(s1.value(), s1.second());
	}
	
	@Test
	public void testToString() {
	    assertEquals("[ABC:XYZ]", s1.toString());
	    assertEquals("[10:20]", i1.toString());
	}
}
