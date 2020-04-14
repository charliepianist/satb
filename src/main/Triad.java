package main;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Triad implements Chord {

	public static final Interval MAX_INTERVAL = Interval.THREE_OCTAVES;
	public static final Tone HIGHEST_TONE = Tone.C6;

	public static final Triad AUG = new Triad(Interval.M3, Interval.A5);
	public static final Triad AUG53 = AUG;
	public static final Triad AUG6 = new Triad(Interval.M3, Interval.m6);
	public static final Triad AUG63 = AUG6;
	public static final Triad AUG64 = new Triad(Interval.d4, Interval.m6);

	public static final Triad MAJ = new Triad(Interval.M3, Interval.P5);
	public static final Triad MAJ53 = MAJ;
	public static final Triad MAJ6 = new Triad(Interval.m3, Interval.m6);
	public static final Triad MAJ63 = MAJ6;
	public static final Triad MAJ64 = new Triad(Interval.P4, Interval.M6);
	
	public static final Triad MIN = new Triad(Interval.m3, Interval.P5);
	public static final Triad MIN53 = MIN;
	public static final Triad MIN6 = new Triad(Interval.M3, Interval.M6);
	public static final Triad MIN63 = MIN6;
	public static final Triad MIN64 = new Triad(Interval.P4, Interval.m6);
	
	public static final Triad DIM = new Triad(Interval.m3, Interval.d5);
	public static final Triad DIM53 = DIM;
	public static final Triad DIM6 = new Triad(Interval.m3, Interval.M6);
	public static final Triad DIM63 = DIM6;
	public static final Triad DIM64 = new Triad(Interval.A4, Interval.M6);
	
	Interval middle;
	Interval top;
	
	public Triad(Interval middle, Interval top) {
		if(middle == null || top == null) throw new IllegalArgumentException("Middle and Top must be non-null values.");
		if(middle.lt(Interval.UNISON)) throw new IllegalArgumentException("Middle cannot be lower than a perfect unison.");
		if(middle.gt(top)) throw new IllegalArgumentException("Top interval cannot be lower than middle interval.");
		if(top.gt(Interval.OCTAVE)) throw new IllegalArgumentException("Top interval cannot exceed a perfect octave.");
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
		System.out.println("MAJOR TRIADS");
		System.out.println("==================");
		System.out.println(Triad.MAJ);
		System.out.println(Triad.MAJ6);
		System.out.println(Triad.MAJ64);
		System.out.println("\nMINOR TRIADS");
		System.out.println("==================");
		System.out.println(Triad.MIN);
		System.out.println(Triad.MIN6);
		System.out.println(Triad.MIN64);
		System.out.println("\nAUGMENTED TRIADS");
		System.out.println("==================");
		System.out.println(Triad.AUG);
		System.out.println(Triad.AUG6);
		System.out.println(Triad.AUG64);
		System.out.println("\nDIMINISHED TRIADS");
		System.out.println("==================");
		System.out.println(Triad.DIM);
		System.out.println(Triad.DIM6);
		System.out.println(Triad.DIM64);

		System.out.println("\nIntervals and Tones");
		System.out.println("===================");
		System.out.println(Triad.MIN64.intervals());
		System.out.println(Triad.MIN64.tones(Tone.A4.down(Interval.OCTAVE)));
	}
}
