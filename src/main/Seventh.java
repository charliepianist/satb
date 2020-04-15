package main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import main.Util.Range;

public class Seventh implements Chord {

	public static final int ROOT = 0;
	public static final int FIRST = 1;
	public static final int SECOND = 2;
	public static final int THIRD = 3;
	
	public static final Interval MAX_INTERVAL = Interval.THREE_OCTAVES;

	public static final Seventh MAJ7 = new Seventh(Interval.M3, Interval.P5, Interval.M7, ROOT, new Range[] { Range.R12, Range.R12, Range.R01, Range.R11 });
	public static final Seventh MAJ65 = new Seventh(Interval.m3, Interval.P5, Interval.m6, FIRST, new Range[] { Range.R11, Range.R11, Range.R11, Range.R11 });
	public static final Seventh MAJ43 = new Seventh(Interval.M3, Interval.P4, Interval.M6, SECOND, new Range[] { Range.R11, Range.R11, Range.R11, Range.R11 });
	public static final Seventh MAJ42 = new Seventh(Interval.m2, Interval.P4, Interval.m6, THIRD, new Range[] { Range.R11, Range.R12, Range.R12, Range.R01 });

	public static final Seventh DOM7 = new Seventh(Interval.M3, Interval.P5, Interval.m7, ROOT, new Range[] { Range.R12, Range.R12, Range.R01, Range.R11 });
	public static final Seventh DOM65 = new Seventh(Interval.m3, Interval.d5, Interval.m6, FIRST, new Range[] { Range.R12, Range.R01, Range.R11, Range.R12 });
	public static final Seventh DOM43 = new Seventh(Interval.m3, Interval.P4, Interval.M6, SECOND, new Range[] { Range.R11, Range.R11, Range.R11, Range.R11 });
	public static final Seventh DOM42 = new Seventh(Interval.M2, Interval.A4, Interval.M6, THIRD, new Range[] { Range.R11, Range.R12, Range.R11, Range.R01 });

	public static final Seventh MIN7 = new Seventh(Interval.m3, Interval.P5, Interval.m7, ROOT, new Range[] { Range.R12, Range.R12, Range.R01, Range.R11 });
	public static final Seventh MIN65 = new Seventh(Interval.M3, Interval.P5, Interval.M6, FIRST, new Range[] { Range.R12, Range.R01, Range.R11, Range.R12 });
	public static final Seventh MIN43 = new Seventh(Interval.m3, Interval.P4, Interval.m6, SECOND, new Range[] { Range.R11, Range.R11, Range.R11, Range.R11 });
	public static final Seventh MIN42 = new Seventh(Interval.M2, Interval.P4, Interval.M6, THIRD, new Range[] { Range.R11, Range.R12, Range.R11, Range.R01 });

	public static final Seventh HALFDIM7 = new Seventh(Interval.m3, Interval.d5, Interval.m7, ROOT, new Range[] { Range.R12, Range.R01, Range.R11, Range.R11 });
	public static final Seventh HALFDIM65 = new Seventh(Interval.m3, Interval.P5, Interval.M6, FIRST, new Range[] { Range.R11, Range.R11, Range.R11, Range.R11 });
	public static final Seventh HALFDIM43 = new Seventh(Interval.M3, Interval.A4, Interval.M6, SECOND, new Range[] { Range.R12, Range.R11, Range.R12, Range.R01 });
	public static final Seventh HALFDIM42 = new Seventh(Interval.M2, Interval.P4, Interval.m6, THIRD, new Range[] { Range.R12, Range.R12, Range.R01, Range.R12 });

	public static final Seventh DIM7 = new Seventh(Interval.m3, Interval.d5, Interval.d7, ROOT, new Range[] { Range.R11, Range.R11, Range.R11, Range.R11 });
	public static final Seventh DIM65 = new Seventh(Interval.m3, Interval.d5, Interval.M6, FIRST, new Range[] { Range.R11, Range.R11, Range.R11, Range.R11 });
	public static final Seventh DIM43 = new Seventh(Interval.m3, Interval.A4, Interval.M6, SECOND, new Range[] { Range.R11, Range.R11, Range.R11, Range.R11 });
	public static final Seventh DIM42 = new Seventh(Interval.A2, Interval.A4, Interval.M6, THIRD, new Range[] { Range.R11, Range.R11, Range.R11, Range.R11 });
	
	private Interval first;
	private Interval second;
	private Interval third;
	private int inversion;
	private Range[] allowedAmount;
	private int df;
	
	// Intervals in increasing order, the inversion of the chord, and how many of each tone is allowed
	public Seventh(Interval first, Interval second, Interval third, int inversion, Range[] allowedAmount) {
		if(first == null || second == null || third == null) throw new IllegalArgumentException("Intervals must be non-null values.");
		if(first.lt(Interval.UNISON)) throw new IllegalArgumentException("First interval cannot be lower than a perfect unison.");
		if(first.gt(second)) throw new IllegalArgumentException("Second interval cannot be lower than first interval.");
		if(second.gt(third)) throw new IllegalArgumentException("Third interval cannot be lower than second interval.");
		if(third.gt(Interval.OCTAVE)) throw new IllegalArgumentException("Third interval cannot exceed an octave.");
		if(inversion != ROOT && inversion != FIRST && inversion != SECOND && inversion != THIRD) throw new IllegalArgumentException("Chord must be in root, first, second, or third inversion.");
		if(allowedAmount.length != 4) throw new IllegalArgumentException("allowedAmount must be length 4 (one for each tone)");
		
		this.inversion = inversion;
		this.first = first;
		this.second = second;
		this.third = third;
		this.allowedAmount = allowedAmount;
		
		df = 4 - allowedAmount[0].getMin() - allowedAmount[1].getMin() - allowedAmount[2].getMin() - allowedAmount[3].getMin();
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
			if(temp.gt(Tone.HIGHEST_TONE))
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
	
	@Override
	public Interval rootToBass() {
		if(inversion == ROOT) {
			return Interval.UNISON;
		}else if(inversion == FIRST) {
			return Interval.OCTAVE.sub(third);
		}else if(inversion == SECOND) {
			return Interval.OCTAVE.sub(second);
		}else if(inversion == THIRD) {
			return Interval.OCTAVE.sub(first);
		}
		return null;
	}
	
	@Override
	public boolean hasPotential(Tone bass, List<Tone> tones) {
		int[] counter = new int[4];
		for(Tone t : tones) {
			if(Interval.intervalBetween(bass, bass.nextInstanceOf(t)).equals(Interval.UNISON)) {
				counter[0]++;
			}else if(Interval.intervalBetween(bass, bass.nextInstanceOf(t)).equals(first)) {
				counter[1]++;
			}else if(Interval.intervalBetween(bass, bass.nextInstanceOf(t)).equals(second)) {
				counter[2]++;
			}else if(Interval.intervalBetween(bass, bass.nextInstanceOf(t)).equals(third)) {
				counter[3]++;
			}
		}
		int cumulativedf = 0;
		for(int i = 0; i < counter.length; i++) {
			if(counter[i] > allowedAmount[i].getMax()) return false;
			cumulativedf += counter[i] - allowedAmount[i].getMin();
		}
		if(cumulativedf > df) return false;
		return true;
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
