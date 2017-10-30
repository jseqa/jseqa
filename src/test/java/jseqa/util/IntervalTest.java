package jseqa.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntervalTest {

	@Test
	public void testParseInterval() {
		Interval ival = new Interval(10, 21);
		Interval ival2 = Interval.parseInterval(ival.toString());
		assertEquals(ival.start(), ival2.start());
		assertEquals(ival.end(), ival2.end());
	}

	@Test
	public void testOverlaps() {
		Interval i1 = new Interval(1,10);
		Interval i2 = new Interval(2,9);
		Interval i3 = new Interval(5,15);
		Interval i4 = new Interval(20,35);
		
		assertEquals(true, i1.overlaps(i2));
		assertEquals(true, i1.overlaps(i3));
		assertEquals(false, i1.overlaps(i4));
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
}
