package jseqa.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class IntervalTest {

	Interval val1;
	Interval val2;
	
	@Before
	public void init() {
		val1 = new Interval(1,10);
		val2 = new Interval(5,15);
	}
	
	@Test
	public void testParseInterval() {
		Interval ival = new Interval(10, 21);
		Interval ival2 = Interval.parseInterval(ival.toString());
		assertEquals(ival.start(), ival2.start());
		assertEquals(ival.end(), ival2.end());
		
		assertEquals(null, Interval.parseInterval("blahblah"));
		assertEquals(null, Interval.parseInterval("9..asdf"));
		assertEquals(new Interval(9,16), Interval.parseInterval("[9,16)"));
		assertEquals(null, Interval.parseInterval("9..2"));
	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void testInvalidArguments() {
//		new Interval(5, 1);
//	}

	@Test
	public void testOverlaps() {
		Interval i1 = new Interval(1,10);
		Interval i2 = new Interval(2,9);
		Interval i3 = new Interval(5,15);
		Interval i4 = new Interval(20,35);
		
		assertEquals(true, i1.overlaps(i2));
		assertEquals(true, i1.overlaps(i3));
		assertEquals(false, i1.overlaps(i4));
		
		assertEquals(false, i4.overlaps(i1));
	}

	@Test
	public void testContains() {
		Interval i1 = new Interval(1,10);
		
		for(int i = 1; i < 10; i++) {
			assertEquals(true, i1.contains(i));
		}
		for(int i = 10; i < 20; i++) {
			assertEquals(false, i1.contains(i));
		}
		for(int i = -10; i < 1; i++) {
			assertEquals(false, i1.contains(i));
		}
	}
	
	@Test
	public void testEquals() {
		Interval i1 = new Interval(1,10);
		Interval i2 = new Interval(2,9);
		Interval i3 = new Interval(1,10);
		assertEquals(true, i1.equals(i1));
		//
		assertEquals(false, i1.equals(i2));
		assertEquals(true, i1.equals(i3));
		assertEquals(false, i2.equals(i3));
	}
	
	@Test
	public void testLength() {
		assertEquals(9, val1.length());
		assertEquals(10, val2.length());
	}
	
	@Test
	public void testShift() {
		Interval val1Shift1 = val1.shift(1);
		assertEquals(false, val1.equals(val1Shift1));
		
		Interval val1Shift4 = val2.shift(4);
		assertEquals(false, val2.equals(val1Shift4));
		assertEquals(10, val1Shift4.length());
	}
	
	@Test
	public void testOverlappingInterval() {
	    Interval olap = val1.overlappingInterval(val2);
	    assertEquals(5, olap.start());
	    assertEquals(10, olap.end());
	    
	    Interval disjointIntval = new Interval(20, 50);
	    assertEquals(null, val1.overlappingInterval(disjointIntval));
	}
	
	@Test
    public void testUnionInterval() {
	    Interval olap = val1.unionInterval(val2);
        assertEquals(1, olap.start());
        assertEquals(15, olap.end());
        
        Interval selfOlap = val1.unionInterval(val1);
        assertEquals(1, selfOlap.start());
        assertEquals(10, selfOlap.end());
    }
}
