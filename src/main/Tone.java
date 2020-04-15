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

	public static final Tone LOWEST_TONE = new Tone(E, 2);
	public static final Tone HIGHEST_TONE = Tone.C6;
	
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

	public static final Tone C2 = new Tone (C, 2);
	public static final Tone C3 = new Tone(C, 3);
	public static final Tone C5 = new Tone (C, 5);
	public static final Tone C6 = new Tone(C, 6);
	
	private final int key;
	private final int octave;
	private final int offset;
	private final int value;
	
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
	
	public Tone(Tone other) {
		this(other.key, other.octave, other.offset);
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
	
	// First instance of the other note that comes above this tone (e.g. D5.nextInstanceOf(A8) -> A5)
	public Tone nextInstanceOf(Tone other) {
		Tone temp = new Tone(other.getKey(), octave, other.getOffset());
		if(temp.lt(this)) temp = new Tone(other.getKey(), octave + 1, other.getOffset());
		return temp;
	}
	
	// Returns whether this Tone is lower than the other
	public boolean lt(Tone other) {
		return this.getValue() < other.getValue();
	}
	
	// Returns whether this Tone is lower than or equal to the other
	public boolean leq(Tone other) {
		return this.getValue() <= other.getValue();
	}
	
	// Returns whether this Tone is greater than the other
	public boolean gt(Tone other) {
		return this.getValue() > other.getValue();
	}
	
	// Returns whether this Tone is greater than or equal to the other
	public boolean geq(Tone other) {
		return this.getValue() >= other.getValue();
	}
	
	// Are the notes the same notes (i.e. some number of octaves apart but same note name)
	public boolean sameNote(Tone other) {
		return this.getKey() == other.getKey() && this.getOffset() == other.getOffset();
	}
	
	public boolean equals(Object other) {
		if(other == this) return true;
		if(other == null) return false;
		if(!(other instanceof Tone)) return false;
		Tone t = (Tone) other;
		return this.key == t.key && this.octave == t.octave && this.offset == t.offset && this.value == t.value;
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
	
	// Gets a Tone from a String (e.g. "C##5")
	public static Tone fromString(String str) {
		if(str == null || str.length() == 0)
			throw new IllegalArgumentException("Cannot parse null/empty string into Tone");
		char[] chars = str.toLowerCase().toCharArray();
		int key = keyFromChar(chars[0]);
		int offset = 0;
		int i = 1;
		
		while(chars[i] == '#' || chars[i] == 'b') {
			if(chars[i] == '#')
				offset++;
			else
				offset--;
			i++;
		}
		
		while(chars[i] == ' ') i++;
		
		String octaveStr = str.substring(i);
		int octave;
		if(octaveStr.length() > 0)
			try {
				octave = Integer.parseInt(octaveStr);
			}catch(NumberFormatException e) {
				throw new IllegalArgumentException("Failed to parse string " + str + " (couldn't cast octave number)");
			}
		else
			octave = -1; // ONLY USE IN SPECIFIC CASES
		
		return new Tone(key, octave, offset);
	}
	
	// To key from lower case character c
	private static int keyFromChar(char c) {
		switch(c) {
		case 'a':
			return A;
		case 'b':
			return B;
		case 'c':
			return C;
		case 'd':
			return D;
		case 'e':
			return E;
		case 'f':
			return F;
		case 'g':
			return G;
		}
		return -1;
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
