package jseqa.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple interval [a,b) that is non inclusive at the higher value.
 *
 */
public class Interval {

	private int start;
	
	private int end;
	
	/**
	 * Constructor that creates an interval [{@code s}, {@code e}).
	 * @param s int inclusive start of interval.
	 * @param e int exclusive end of interval.
	 */
	public Interval(int s, int e) {
		this.start = s;
		this.end = e;
	}
	
	public int start() {
		return this.start;
	}
	
	public int end() {
		return this.end;
	}
	
	public int length() {
		return (this.end - this.start);
	}
	
	/**
	 * Return an {@code Interval} that is shifted by {@code val}.
	 * @param val int to shift interval
	 * @return {@code Interval} shifted by {@code val}.
	 */
	public Interval shift(int val) {
		return new Interval(this.start+val, this.end+val);
	}
	
	public boolean equals(Object obj) {
		if(obj == this) { 
			return true;
		} else if(obj instanceof Interval) {
			Interval otherInt = (Interval)obj;
			return otherInt.start() == this.start && otherInt.end() == this.end;
		} else {
			return true;
		}
	}
	
	/**
	 * determins if val is in [s,e), i.e., s <= val < e
	 * @param val int value to check if is in interval
	 * @return true if s <= val < e, false otherwise
	 */
	public boolean contains(int val) {
		if(this.start <= val && val < this.end) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks if two intervals overlap, i.e., <br>
	 * <pre>{@code 
	 * this:   |---------|
	 * other:       |---------|
	 * }</pre>
	 * @param otherIntval
	 * @return
	 */
	public boolean overlaps(Interval otherIntval) {
		if(this.end <= otherIntval.start()) {
			return false;
		} else if(this.start >= otherIntval.end()) {
			return false;
		} else {
			return true;
		}
	}
	
	public String toString() {
		return "[" + this.start + "," + this.end + ")";
	}
	
	public String toRangeString() {
		return this.start + ".." + this.end;
	}
	
	
	private final static Pattern intvalRe = Pattern.compile("\\[(\\d+),(\\d+)\\)");
	
	public static Interval parseInterval(String intvalStr) {
		Matcher m = intvalRe.matcher(intvalStr);
		if(m.find()) {
			int strtIdx = Integer.parseInt(m.group(1));
			int endIdx = Integer.parseInt(m.group(2));
			return new Interval(strtIdx, endIdx);
		}
		return null;
	}
}
