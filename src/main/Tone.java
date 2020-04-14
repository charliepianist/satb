package main;

public class Tone {
	// Value of C0
	public static final int baseValue = -9;
	public static final int C = 0;
	public static final int D = 1;
	public static final int E = 2;
	public static final int F = 3;
	public static final int G = 4;
	public static final int A = 5;
	public static final int B = 6;
	
	private static final int[] keyOffsets;
	private static final String[] keyNames;
	static {
		keyOffsets = new int[] {
			0, // C
			2, // D
			4, // E
			5, // F
			7, // G
			9, // A
			11 // B
		};
		keyNames = new String[] {
			"C", "D", "E", "F", "G", "A", "B"
		};
	}

	public static final Tone Cb4 = new Tone(C, 4, -1);
	public static final Tone middleC = new Tone(C, 4);
	public static final Tone C4 = middleC;
	public static final Tone Cs4 = new Tone (C, 4, 1);
	public static final Tone Db4 = new Tone(D, 4, -1);
	public static final Tone D4 = new Tone(D, 4);
	public static final Tone Ds4 = new Tone (D, 4, 1);
	public static final Tone Eb4 = new Tone(E, 4, -1);
	public static final Tone E4 = new Tone(E, 4);
	public static final Tone Es4 = new Tone (E, 4, 1);
	public static final Tone Fb4 = new Tone(F, 4, -1);
	public static final Tone F4 = new Tone(F, 4);
	public static final Tone Fs4 = new Tone (F, 4, 1);
	public static final Tone Gb4 = new Tone(G, 4, -1);
	public static final Tone G4 = new Tone(G, 4);
	public static final Tone Gs4 = new Tone (G, 4, 1);
	public static final Tone Ab4 = new Tone(A, 4, -1);
	public static final Tone A4 = new Tone(A, 4);
	public static final Tone As4 = new Tone (A, 4, 1);
	public static final Tone Bb4 = new Tone(B, 4, -1);
	public static final Tone B4 = new Tone(B, 4);
	public static final Tone Bs4 = new Tone (B, 4, 1);
	
	private int key;
	private int octave;
	private int offset;
	private int value;
	
	public Tone(int key, int octave) {
		offset = 0;
		this.key = key;
		this.octave = octave;
		value = defaultValue(key, octave);
	}
	
	public Tone(int key, int octave, int offset) {
		this.offset = offset;
		this.key = key;
		this.octave = octave;
		value = defaultValue(key, octave) + offset;
	}
	
	public int getKey() {
		return key;
	}
	
	public int getOctave() {
		return octave;
	}
	
	// Key + Octave * 7, offset so A0 = 0
	public int getAbsoluteKey() {
		return key + octave * 7 - A;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public int getValue() {
		return value;
	}
	
	public static int defaultValue(int key, int octave) {
		if(key > B || key < C) throw new IllegalArgumentException("Key must be between " + C + " and " + B + ".");
		return octave * Interval.OCTAVE_STEPS + keyOffsets[key];
	}
	
	public Tone up(Interval interval) {
		int newKey = key + interval.getInterval() - 1;
		int diffOctaves = newKey / 7;
		int newOctave = octave + diffOctaves;
		newKey -= 7 * diffOctaves;
		
		int newValue = value + interval.getOffset();
		int newOffset = newValue - defaultValue(newKey, newOctave);
		
		return new Tone(newKey, newOctave, newOffset);
	}
	
	public Tone down(Interval interval) {
		int newKey = key - interval.getInterval() + 1;
		int diffOctaves = 0;
		if(newKey < 0) {
			diffOctaves = 1 + (Math.abs(newKey) - 1) / 7;
		}
		int newOctave = octave - diffOctaves;
		newKey += 7 * diffOctaves;
		
		int newValue = value - interval.getOffset();
		int newOffset = newValue - defaultValue(newKey, newOctave);
		
		return new Tone(newKey, newOctave, newOffset);
	}
	
	public boolean equals(Tone other) {
		return this.key == other.key && this.octave == other.octave && this.offset == other.offset && this.value == other.value;
	}
	
	public String toString() {
		String str = keyNames[key];
		if(offset > 0) {
			str += Util.repeatChar('#', offset);
		}else if(offset < 0) {
			str += Util.repeatChar('b', Math.abs(offset));
		}
		return str + octave;
	}
	
	public static void main(String[] args) {
		Tone t1 = Tone.middleC;
		Tone t2 = Tone.Gs4;
		
		System.out.println(t1.down(Interval.OCTAVE.add(Interval.OCTAVE)));
		System.out.println(t2.up(Interval.d3).up(Interval.d3));
		System.out.print(t2.up(Interval.m3) + " ");
		System.out.print(t2.up(Interval.m3).up(Interval.m3) + " ");
		System.out.print(t2.up(Interval.m3).up(Interval.m3).up(Interval.m3) + " ");
		System.out.println(t2.up(Interval.m3).up(Interval.m3).up(Interval.m3).up(Interval.m3));
		System.out.print(t2.down(Interval.A2) + " ");
		System.out.print(t2.down(Interval.A2).down(Interval.m3) + " ");
		System.out.print(t2.down(Interval.A2).down(Interval.m3).down(Interval.m3) + " ");
		System.out.println(t2.down(Interval.A2).down(Interval.m3).down(Interval.m3).down(Interval.m3));
	}
}
