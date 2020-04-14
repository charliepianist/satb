package main;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Seventh implements Chord {

	public static final Interval MAX_INTERVAL = Interval.THREE_OCTAVES;
	public static final Tone HIGHEST_TONE = Tone.C6;

	public static final Seventh MAJ7 = new Seventh(Interval.M3, Interval.P5, Interval.M7);
	public static final Seventh MAJ65 = new Seventh(Interval.m3, Interval.P5, Interval.m6);
	public static final Seventh MAJ43 = new Seventh(Interval.M3, Interval.P4, Interval.M6);
	public static final Seventh MAJ42 = new Seventh(Interval.m2, Interval.P4, Interval.m6);

	public static final Seventh DOM7 = new Seventh(Interval.M3, Interval.P5, Interval.m7);
	public static final Seventh DOM65 = new Seventh(Interval.m3, Interval.d5, Interval.m6);
	public static final Seventh DOM43 = new Seventh(Interval.m3, Interval.P4, Interval.M6);
	public static final Seventh DOM42 = new Seventh(Interval.M2, Interval.A4, Interval.M6);

	public static final Seventh MIN7 = new Seventh(Interval.m3, Interval.P5, Interval.m7);
	public static final Seventh MIN65 = new Seventh(Interval.M3, Interval.P5, Interval.M6);
	public static final Seventh MIN43 = new Seventh(Interval.m3, Interval.P4, Interval.m6);
	public static final Seventh MIN42 = new Seventh(Interval.M2, Interval.P4, Interval.M6);

	public static final Seventh HALFDIM7 = new Seventh(Interval.m3, Interval.d5, Interval.m7);
	public static final Seventh HALFDIM65 = new Seventh(Interval.m3, Interval.P5, Interval.M6);
	public static final Seventh HALFDIM43 = new Seventh(Interval.M3, Interval.A4, Interval.M6);
	public static final Seventh HALFDIM42 = new Seventh(Interval.M2, Interval.P4, Interval.m6);

	public static final Seventh DIM7 = new Seventh(Interval.m3, Interval.d5, Interval.d7);
	public static final Seventh DIM65 = new Seventh(Interval.m3, Interval.d5, Interval.M6);
	public static final Seventh DIM43 = new Seventh(Interval.m3, Interval.A4, Interval.M6);
	public static final Seventh DIM42 = new Seventh(Interval.A2, Interval.A4, Interval.M6);
	
	Interval first;
	Interval second;
	Interval third;
	
	public Seventh(Interval first, Interval second, Interval third) {
		if(first == null || second == null || third == null) throw new IllegalArgumentException("Intervals must be non-null values.");
		if(first.lt(Interval.UNISON)) throw new IllegalArgumentException("First interval cannot be lower than a perfect unison.");
		if(first.gt(second)) throw new IllegalArgumentException("Second interval cannot be lower than first interval.");
		if(second.gt(third)) throw new IllegalArgumentException("Third interval cannot be lower than second interval.");
		if(third.gt(Interval.OCTAVE)) throw new IllegalArgumentException("Third interval cannot exceed an octave.");
		
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	@Override
	public ArrayList<Interval> intervals() {
		ArrayList<Interval> ret = new ArrayList<Interval>();
		ret.add(Interval.UNISON);
		
		Interval iFirst = first;
		Interval iSecond = second;
		Interval iThird = third;
		Interval iBottom = Interval.OCTAVE;
		
		for(int i = 0; i < 10; i++) {
			if(iFirst.gt(MAX_INTERVAL))
				return ret;
			ret.add(iFirst);
			if(iSecond.gt(MAX_INTERVAL))
				return ret;
			ret.add(iSecond);
			if(iThird.gt(MAX_INTERVAL))
				return ret;
			ret.add(iThird);
			if(iBottom.gt(MAX_INTERVAL))
				return ret;
			ret.add(iBottom);
			
			iFirst = iFirst.add(Interval.OCTAVE);
			iSecond = iSecond.add(Interval.OCTAVE);
			iThird = iThird.add(Interval.OCTAVE);
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
		intervals.add(first);
		intervals.add(second);
		intervals.add(third);
		return intervals;
	}

	@Override
	public ArrayList<Tone> baseChord(Tone bass) {
		ArrayList<Tone> tones = new ArrayList<Tone>();
		tones.add(bass);
		tones.add(bass.up(first));
		tones.add(bass.up(second));
		tones.add(bass.up(third));
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
		System.out.println("MAJOR SEVENTHS");
		System.out.println("===================");
		System.out.println(Seventh.MAJ7);
		System.out.println(Seventh.MAJ65);
		System.out.println(Seventh.MAJ43);
		System.out.println(Seventh.MAJ42);
		System.out.println("DOMINANT SEVENTHS");
		System.out.println("===================");
		System.out.println(Seventh.DOM7);
		System.out.println(Seventh.DOM65);
		System.out.println(Seventh.DOM43);
		System.out.println(Seventh.DOM42);
		System.out.println("MINOR SEVENTHS");
		System.out.println("===================");
		System.out.println(Seventh.MIN7);
		System.out.println(Seventh.MIN65);
		System.out.println(Seventh.MIN43);
		System.out.println(Seventh.MIN42);
		System.out.println("HALF DIMINISHED SEVENTHS");
		System.out.println("========================");
		System.out.println(Seventh.HALFDIM7);
		System.out.println(Seventh.HALFDIM65);
		System.out.println(Seventh.HALFDIM43);
		System.out.println(Seventh.HALFDIM42);
		System.out.println("DIMINISHED SEVENTHS");
		System.out.println("===================");
		System.out.println(Seventh.DIM7);
		System.out.println(Seventh.DIM65);
		System.out.println(Seventh.DIM43);
		System.out.println(Seventh.DIM42);

		System.out.println("\nIntervals and Tones (Half Dim 43)");
		System.out.println("===================================");
		System.out.println(Seventh.HALFDIM43.intervals());
		System.out.println(Seventh.HALFDIM43.tones(Tone.A4.down(Interval.OCTAVE)));
	}
}
