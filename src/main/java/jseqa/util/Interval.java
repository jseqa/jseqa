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

    /**
     * Start value of interval.
     * @return interval start.
     */
    public int start() {
        return this.start;
    }

    /**
     * End value of interval.
     * @return interval end.
     */
    public int end() {
        return this.end;
    }

    /**
     * Length of interval.
     * @return int length of interval.
     */
    public int length() {
        return (this.end - this.start);
    }

    /**
     * Return an {@code Interval} that is shifted by {@code val}.
     * @param val int to shift interval
     * @return {@code Interval} shifted by {@code val}.
     */
    public Interval shift(int val) {
        return new Interval(this.start + val, this.end + val);
    }

    /**
     * Equality comparison of {@code Interval} are true iff both values are equal.
     */
    public boolean equals(Object obj) {
        if (obj == this) { 
            return true;
        } else if (obj instanceof Interval) {
            Interval otherInt = (Interval)obj;
            return otherInt.start() == this.start && otherInt.end() == this.end;
        } else {
            return false;
        }
    }

    /**
     * Determines if val is in [s,e), i.e., \(s \leq val < e\).
     * @param val int value to check if is in interval
     * @return true if \(s \leq val < e\), false otherwise
     */
    public boolean contains(int val) {
        if (this.start <= val && val < this.end) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if two intervals overlap, e.g., <br>
     * <pre>{@code 
     * this:   |---------|
     * other:       |---------|
     * }</pre>
     * @param otherIntval {@code Interval} to determine if overlaps with self.
     * @return true if intervals overlap, false otherwise.
     */
    public boolean overlaps(Interval otherIntval) {
        if (this.end <= otherIntval.start()) {
            return false;
        } else if (this.start >= otherIntval.end()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Computes the {@code Interval} of overlap between {@code this} and {@code otherIntval}.
     * <br>
     * For example, the overlapping interval between [1,10) and [5,15) is [5,10].
     * @param otherIntval
     * @return {@code Interval} covering the overlap, or null, if no overlap is present.
     */
    public Interval overlappingInterval(Interval otherIntval) {
        if (this.end <= otherIntval.start()) {
            return null;
        } else if (this.start >= otherIntval.end()) {
            return null;
        } else {
            int maxStart = Math.max(this.start, otherIntval.start());
            int minEnd = Math.min(this.end, otherIntval.end());
            return new Interval(maxStart, minEnd);
        }
    }
    
    /**
     * Computes the union of interval {@code this} and {@code otherIntval}. It is computed
     * by [min(A,B), max(A,B)] for two intervals A and B.
     * @param otherIntval
     * @return {@code Interval} of union.
     */
    public Interval unionInterval(Interval otherIntval) {
        int minStart = Math.min(this.start, otherIntval.start());
        int maxEnd = Math.max(this.end, otherIntval.end());
        return new Interval(minStart, maxEnd);
    }
    
    /**
     * Outputs interval in {@code String} in format [x,y), as would be 
     * parsable from {@link Interval#parseInterval}.
     */
    public String toString() {
        return "[" + this.start + "," + this.end + ")";
    }

    /**
     * Outputs interval as {@code String} in format x..y
     * @return {@code String} of interval range.
     */
    public String toRangeString() {
        return this.start + ".." + this.end;
    }


    private static final Pattern intvalRe = Pattern.compile("\\[(\\d+),(\\d+)\\)");

    /**
     * Parses a {@code String} returning an {@code Interval} object if it matches the pattern: [x,y)
     * where {@code x} and {@code y} are integers.
     * @param intvalStr {@code String} to parse as an {@code Interval}.
     * @return {@code Interval} if could parse the input {@code String}.
     */
    public static Interval parseInterval(String intvalStr) {
        Matcher m = intvalRe.matcher(intvalStr);
        if (m.find()) {
            int strtIdx = Integer.parseInt(m.group(1));
            int endIdx = Integer.parseInt(m.group(2));
            return new Interval(strtIdx, endIdx);
        }
        return null;
    }
}
