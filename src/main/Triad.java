package main;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Triad implements Chord {

	public static final Interval MAX_INTERVAL = Interval.THREE_OCTAVES;
	public static final Tone HIGHEST_TONE = Tone.C6;

	public static final Triad AUGMENTED = new Triad(Interval.M3, Interval.A5);
	public static final Triad AUGMENTED53 = AUGMENTED;
	public static final Triad AUGMENTED6 = new Triad(Interval.M3, Interval.m6);
	public static final Triad AUGMENTED63 = AUGMENTED6;
	public static final Triad AUGMENTED64 = new Triad(Interval.d4, Interval.m6);

	public static final Triad MAJOR = new Triad(Interval.M3, Interval.P5);
	public static final Triad MAJOR53 = MAJOR;
	public static final Triad MAJOR6 = new Triad(Interval.m3, Interval.m6);
	public static final Triad MAJOR63 = MAJOR6;
	public static final Triad MAJOR64 = new Triad(Interval.P4, Interval.M6);
	
	public static final Triad MINOR = new Triad(Interval.m3, Interval.P5);
	public static final Triad MINOR53 = MINOR;
	public static final Triad MINOR6 = new Triad(Interval.M3, Interval.M6);
	public static final Triad MINOR63 = MINOR6;
	public static final Triad MINOR64 = new Triad(Interval.P4, Interval.m6);
	
	public static final Triad DIMINISHED = new Triad(Interval.m3, Interval.d5);
	public static final Triad DIMINISHED53 = DIMINISHED;
	public static final Triad DIMINISHED6 = new Triad(Interval.m3, Interval.M6);
	public static final Triad DIMINISHED63 = DIMINISHED6;
	public static final Triad DIMINISHED64 = new Triad(Interval.A4, Interval.M6);
	
	Interval middle;
	Interval top;
	
	public Triad(Interval middle, Interval top) {
		if(middle == null || top == null) throw new IllegalArgumentException("Middle and Top must be non-null values.");
		if(middle.lt(Interval.UNISON)) throw new IllegalArgumentException("Middle cannot be lower than a perfect unison.");
		if(middle.gt(top)) throw new IllegalArgumentException("Top interval cannot be lower than middle interval.");
		this.middle = middle;
		this.top = top;
	}
	
	@Override
	public ArrayList<Interval> intervals() {
		ArrayList<Interval> ret = new ArrayList<Interval>();
		ret.add(Interval.UNISON);
		
		Interval iMiddle = middle;
		Interval iTop = top;
		Interval iBottom = Interval.OCTAVE;
		
		for(int i = 0; i < 10; i++) {
			if(iMiddle.gt(MAX_INTERVAL))
				return ret;
			ret.add(iMiddle);
			if(iTop.gt(MAX_INTERVAL))
				return ret;
			ret.add(iTop);
			if(iBottom.gt(MAX_INTERVAL))
				return ret;
			ret.add(iBottom);
			
			iMiddle = iMiddle.add(Interval.OCTAVE);
			iTop = iTop.add(Interval.OCTAVE);
			iBottom = iBottom.add(Interval.OCTAVE);
		}
		
		return ret;
	}

	@Override
	public ArrayList<Tone> tones(Tone bass) {
		ArrayList<Tone> ret = new ArrayList<Tone>();
		ArrayList<Interval> intervals = intervals();
		
		for(Interval i : intervals) {
			Tone temp = bass.up(i);
			if(temp.gt(HIGHEST_TONE))
				return ret;
			
			ret.add(temp);
		}
		return ret;
	}
	
	@Override
	public ArrayList<Interval> baseIntervals() {
		ArrayList<Interval> intervals = new ArrayList<Interval>();
		intervals.add(Interval.UNISON);
		intervals.add(middle);
		intervals.add(top);
		return intervals;
	}

	@Override
	public ArrayList<Tone> baseChord(Tone bass) {
		ArrayList<Tone> tones = new ArrayList<Tone>();
		tones.add(bass);
		tones.add(bass.up(middle));
		tones.add(bass.up(top));
		return tones;
	}
	
	public String toString() {
		ArrayList<Interval> intervals = baseIntervals();
		ArrayList<Tone> tones = baseChord(Tone.middleC);
		
		String ret = String.join(" ", intervals.stream().map(i -> i.toString()).collect(Collectors.toList()));
		ret += " (C: " + String.join(" ", tones.stream().map(t -> t.toString()).collect(Collectors.toList())) + ")";
		return ret;
	}
	
	public static void main(String[] args) {
		System.out.println(Triad.MAJOR);
		System.out.println(Triad.MAJOR6);
		System.out.println(Triad.MAJOR64);
		System.out.println(Triad.MINOR);
		System.out.println(Triad.MINOR6);
		System.out.println(Triad.MINOR64);
		System.out.println(Triad.AUGMENTED);
		System.out.println(Triad.AUGMENTED6);
		System.out.println(Triad.AUGMENTED64);
		System.out.println(Triad.DIMINISHED);
		System.out.println(Triad.DIMINISHED6);
		System.out.println(Triad.DIMINISHED64);
		
		System.out.println(Triad.MINOR64.intervals());
		System.out.println(Triad.MAJOR6.tones(Tone.A4.down(Interval.OCTAVE)));
	}
}
