package com.charliepianist.main;

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
			11 // Major 7th
		};
	}
	
	public static final int OCTAVE_STEPS = 12;
	
	public static final Interval d1 = new Interval(MIN, 1);
	public static final Interval UNISON = new Interval(MAJPERF, 1);
	public static final Interval A1 = new Interval(AUG, 1);
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
	public static final Interval OCTAVE = P8;
	public static final Interval A8 = new Interval(AUG, 8);
	// Octaves
	public static final Interval TWO_OCTAVES = new Interval(MAJPERF, 15);
	public static final Interval THREE_OCTAVES = new Interval(MAJPERF, 22);
	
	// The offset from major/perfect interval of this type
	private final int quality;
	// 2nd, 3rd, Unison, etc
	private final int interval;
	// The actual offset from unison (i.e. Perfect 4 has offset 5)
	private final int offset;
	
	public Interval(int quality, int interval) {
		if(interval < 1) throw new IllegalArgumentException("Interval cannot be less than 1.");
		
		this.quality = quality;
		this.interval = interval;

		this.offset = defaultOffset(interval) + quality;
	}
	
	public Interval(Interval other) {
		this(other.quality, other.interval);
	}
	
	public int getQuality() {
		return this.quality;
	}
	
	public int getInterval() {
		return this.interval;
	}
	
	public int getOffset() {
		return this.offset;
	}
	
	// What is the offset for a Major/Perfect version of this interval?
	public static int defaultOffset(int interval) {
		if(interval <= 0) throw new IllegalArgumentException("Interval cannot be negative");
		interval--;
		int octaves = interval / 7;
		int remainder = interval % 7 + 1;
		return octaves * OCTAVE_STEPS + defaultOffsets[remainder];
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
	
	// Checks if this interval is less than the other in pure offset value.
	public boolean lt(Interval other) {
		return this.getOffset() < other.getOffset();
	}
	
	// Checks if this interval is less than or equal to the other in pure offset value.
	public boolean leq(Interval other) {
		return this.getOffset() <= other.getOffset();
	}
	
	// Checks if this interval is greater than the other in pure offset value.
	public boolean gt(Interval other) {
		return this.getOffset() > other.getOffset();
	}
	
	// Checks if this interval is greater than or equal to the other in pure offset value.
	public boolean geq(Interval other) {
		return this.getOffset() >= other.getOffset();
	}
	
	public Interval normalize() {
		return Interval.normalize(this);
	}
	
	// Brings interval down to (strictly) less than an octave
	public static Interval normalize(Interval i) {
		while(i.geq(Interval.OCTAVE)) {
			i = i.sub(Interval.OCTAVE);
		}
		return i;
	}
	
	public boolean offByOctave(Interval other) {
		return offByOctave(other, false);
	}
	
	// Are these intervals either equal or some number of octaves apart?
	public boolean offByOctave(Interval other, boolean allowEnharmonic) {
		Interval i1 = this.normalize();
		Interval i2 = other.normalize();
		if(allowEnharmonic) return i1.enharmonic(i2);
		return i1.equals(i2);
	}
	
	// Not very important since there's not that many usable Intervals anyways
	@Override
	public int hashCode() {
		return (quality + interval * 31);
	}
	
	// Finds interval between two tones. Gets the interval between note with lower key/octave, or if they are equal, with lower pitch
	// and the higher note.
	public static Interval intervalBetween(Tone one, Tone two) {
		// t1 note+octave below t2 if not equal
		Tone t1 = null, t2 = null;
		if(one.getAbsoluteKey() > two.getAbsoluteKey()) {
			t2 = one;
			t1 = two;
		}else if(one.getAbsoluteKey() < two.getAbsoluteKey()) {
			t1 = one;
			t2 = two;
		}else {
			if(one.getValue() > two.getValue()) {
				t2 = one;
				t1 = two;
			}else {
				t1 = one;
				t2 = two;
			}
		}
		
		int interval = t2.getAbsoluteKey() - t1.getAbsoluteKey() + 1;
		int offset = t2.getValue() - t1.getValue();
		int quality = offset - defaultOffset(interval);
		
		return new Interval(quality, interval);
	}
	
	public static Interval fromString(String str) {
		if(str == null || str.length() == 0) 
			throw new IllegalArgumentException("Cannot parse null/empty string into Interval");
		char[] chars = str.toCharArray();
		char type = chars[0];
		int quality = 0;
		switch(type) {
		case 'A':
		case 'a':
			quality = AUG;
			break;
		case 'M':
		case 'P':
		case 'p':
			quality = MAJPERF;
			break;
		case 'm':
			quality = MIN;
			break;
		case 'D':
		case 'd':
			quality = DIM;
			break;
		default:
			throw new IllegalArgumentException("Cannot parse string " + str + " into interval (invalid quality)");
		}
		int i = 1;
	
		while(chars.length > i && (chars[i] == '-' || chars[i] == '+')) {
			if(chars[i] == '-')
				quality--;
			else
				quality++;
			i++;
		}
		
		String intervalStr = str.substring(i);
		if(intervalStr.length() == 0)
			throw new IllegalArgumentException("Cannot parse string " + str + " into interval (missing interval number)");
		try {
			int interval = Integer.parseInt(intervalStr);
			return new Interval(quality, interval);
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException("Cannot parse string " + str + " into interval (Does not end with interval number)");
		}
	}
	
	public String toString() {
		String str = "";
		
		if(quality > AUG) str = "Augmented" + Util.repeatChar('+', (quality - AUG));
		if(quality == AUG) str = "Augmented";
		if(quality == MAJPERF) str = isPerfect(interval) ? "Perfect" : "Major";
		if(isPerfect(this.interval)) {
			if(quality == MIN) str = "Diminished";
			if(quality < MIN) str = "Diminished" + Util.repeatChar('-', (MIN - quality));
		}else {
			if(quality == MIN) str = "Minor";
			if(quality == DIM) str = "Diminished";
			if(quality < DIM) str = "Diminished" + Util.repeatChar('-', (DIM - quality));
		}
		str += " " + interval;
		return str;
	}
	
	public boolean equals(Object other) {
		if(other == this) return true;
		if(other == null) return false;
		if(!(other instanceof Interval)) return false;
		Interval i = (Interval) other;
		return this.interval == i.interval && this.quality == i.quality;
	}
	
	public boolean enharmonic(Interval other) {
		return Interval.enharmonic(this, other);
	}
	
	// Do these intervals *sound* the same?
	public static boolean enharmonic(Interval i1, Interval i2) {
		return i1.offset == i2.offset;
	}
	
	public static class SignedInterval {
		private Interval interval;
		private boolean up;
		
		public static SignedInterval UNISON = new SignedInterval(Interval.UNISON);
		
		public SignedInterval(Interval i) {
			this(i, true);
		}
		
		public SignedInterval(Interval i, boolean u) {
			interval = i;
			up = u;
		}
		
		public Tone addTo(Tone t) {
			if(up) return t.up(interval);
			return t.down(interval);
		}
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
		
		Tone t1 = Tone.A4;
		Tone t2 = new Tone(Tone.E, 4, -5);
		System.out.println("Interval between " + t1 + " and " + t2 + " is " + Interval.intervalBetween(t1, t2));		
		t1 = Tone.C4.down(Interval.m3); // A3
		t2 = Tone.Fs4.up(Interval.A1); // F##4
		System.out.println("Interval between " + t1 + " and " + t2 + " is " + Interval.intervalBetween(t1, t2));		
		t1 = Tone.C4.down(Interval.d5); // F#3
		t2 = Tone.Ds4.up(Interval.m3); // F#4
		System.out.println("Interval between " + t1 + " and " + t2 + " is " + Interval.intervalBetween(t1, t2));
	}
}
