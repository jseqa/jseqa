package jseqa.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntervalTest {

	@Test
	public void testParseInterval() {
		Interval ival = new Interval(10, 21);
		Interval ival2 = Interval.parseInterval(ival.toString());
		assertEquals(ival.start(), ival2.start());
		assertEquals(ival.end(), ival2.end());
	}

}
