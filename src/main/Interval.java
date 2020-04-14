package main;

public class Interval {
	public static final int AUG = 1;
	public static final int MAJPERF = 0;
	public static final int MIN = -1;
	public static final int DIM = -2;
	
	// Offsets from tonic
	private static final int[] defaultOffsets;
	static {
		defaultOffsets = new int[] {
			-100, // Doesn't make sense
			0, // Unison
			2, // Major 2nd
			4, // Major 3rd
			5, // Perfect 4th
			7, // Perfect 5th
			9, // Major 6th
			11, // Major 7th
			12 // Perfect 8th
		};
	}
	
	public static final Interval UNISON = new Interval(MAJPERF, 1);
	// 2nds
	public static final Interval d2 = new Interval(DIM, 2);
	public static final Interval m2 = new Interval(MIN, 2);
	public static final Interval M2 = new Interval(MAJPERF, 2);
	public static final Interval A2 = new Interval(AUG, 2);
	// 3rds
	public static final Interval d3 = new Interval(DIM, 3);
	public static final Interval m3 = new Interval(MIN, 3);
	public static final Interval M3 = new Interval(MAJPERF, 3);
	public static final Interval A3 = new Interval(AUG, 3);
	// 4ths
	public static final Interval d4 = new Interval(MIN, 4);
	public static final Interval P4 = new Interval(MAJPERF, 4);
	public static final Interval A4 = new Interval(AUG, 4);
	// 5ths
	public static final Interval d5 = new Interval(MIN, 5);
	public static final Interval P5 = new Interval(MAJPERF, 5);
	public static final Interval A5 = new Interval(AUG, 5);
	// 6ths
	public static final Interval d6 = new Interval(DIM, 6);
	public static final Interval m6 = new Interval(MIN, 6);
	public static final Interval M6 = new Interval(MAJPERF, 6);
	public static final Interval A6 = new Interval(AUG, 6);
	// 7ths
	public static final Interval d7 = new Interval(DIM, 7);
	public static final Interval m7 = new Interval(MIN, 7);
	public static final Interval M7 = new Interval(MAJPERF, 7);
	public static final Interval A7 = new Interval(AUG, 7);
	// 8ths
	public static final Interval d8 = new Interval(DIM, 8);
	public static final Interval P8 = new Interval(MAJPERF, 8);
	public static final Interval A8 = new Interval(AUG, 8);
	
	// The offset from major/perfect interval of this type
	private int quality;
	// 2nd, 3rd, Unison, etc
	private int interval;
	// The offset from a perfect/major interval of this type
	private int offset;
	
	public Interval(int quality, int interval) {
		this.quality = quality;
		this.interval = interval;

		this.offset = defaultOffset(interval) + quality;
	}
	
	public int getQuality() {
		return this.quality;
	}
	
	public int getInterval() {
		return this.interval;
	}
	
	// What is the offset for a Major/Perfect version of this interval?
	public static int defaultOffset(int interval) {
		if(interval <= 0) throw new IllegalArgumentException("Interval cannot be negative");
		interval--;
		int octaves = interval / 7;
		int remainder = interval % 7 + 1;
		return octaves * 12 + defaultOffsets[remainder];
	}
	
	// Is a given interval one that is Major/Minor or Perfect?
	private static boolean isPerfect(int interval) {
		return (interval % 7 == 4 || interval % 7 == 5 || interval % 7 == 1);
	}
	
	public Interval add(Interval other) {
		return Interval.add(this, other);
	}
	
	// Add two intervals together (preserves correct interval type and quality)
	public static Interval add(Interval i1, Interval i2) {
		int newOffset = i1.offset + i2.offset;
		int newInterval = i1.getInterval() + i2.getInterval() - 1;
		int defaultOffset = defaultOffset(newInterval);
		int newQuality = newOffset - defaultOffset;
		
		return new Interval(newQuality, newInterval);
	}
	
	public Interval sub(Interval other) {
		return Interval.sub(this, other);
	}
	
	// Subtracts intervals. Note that it attempts to force a positive interval, or if the intervals are the same,
	// attempts to force a positive offset (augmented rather than diminished)
	public static Interval sub(Interval one, Interval two) {
		Interval i1 = null, i2 = null;
		if(one.interval > two.interval) {
			i1 = one;
			i2 = two;
		}else if(one.interval < two.interval) {
			i1 = two;
			i2 = one;
		}else {
			if(two.offset > one.offset) {
				i1 = two;
				i2 = one;
			}else {
				i1 = one;
				i2 = two;
			}
		}
		int newOffset = i1.offset - i2.offset;
		int newInterval = i1.getInterval() - i2.getInterval() + 1;
		int defaultOffset = defaultOffset(newInterval);
		int newQuality = newOffset - defaultOffset;
		
		return new Interval(newQuality, newInterval);
	}
	
	public String toString() {
		String str = "";
		
		if(quality > AUG) str = "Augmented+" + (quality - AUG);
		if(quality == AUG) str = "Augmented";
		if(quality == MAJPERF) str = "Major/Perfect";
		if(isPerfect(this.interval)) {
			if(quality == MIN) str = "Diminished";
			if(quality < MIN) str = "Diminished-" + (MIN - quality);
		}else {
			if(quality == MIN) str = "Minor";
			if(quality == DIM) str = "Diminished";
			if(quality < DIM) str = "Diminished-" + (DIM - quality);
		}
		str += " " + interval;
		return str;
	}
	
	public boolean equals(Interval other) {
		return this.interval == other.interval && this.quality == other.quality;
	}
	
	// Basic testing module
	public static void main(String[] args) {
		Interval i1 = Interval.P4;
		Interval i2 = Interval.d5;
		System.out.println(i1.add(i2));
		Interval i3 = Interval.d3;
		Interval i4 = Interval.A7;
		Interval i5 = Interval.A2;
		System.out.println(i3.add(i4));
		System.out.println(i2.add(i3));
		System.out.println(i1.add(i4));
		
		System.out.println("SUBTRACTION");
		System.out.println(i1.add(i2.sub(i1)).equals(i2));
		System.out.println(i3.sub(i5));
		System.out.println(i3.add(i4.sub(i3)).equals(i4));
	}
}
