package com.charliepianist.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.charliepianist.main.Interval.SignedInterval;
import com.charliepianist.main.Util.Range;

public class Triad implements Chord {
	
	public static final int ROOT = 0;
	public static final int FIRST = 1;
	public static final int SECOND = 2;

	public static final Interval MAX_INTERVAL = Interval.THREE_OCTAVES;
	
	public static final Triad AUG = new Triad(Interval.M3, Interval.A5, ROOT, new Range[] { Range.R12, Range.R12, Range.R12 });
	public static final Triad AUG53 = AUG;
	public static final Triad AUG6 = new Triad(Interval.M3, Interval.m6, FIRST, new Range[] { Range.R12, Range.R12, Range.R12 });
	public static final Triad AUG63 = AUG6;
	public static final Triad AUG64 = new Triad(Interval.d4, Interval.m6, SECOND, new Range[] { Range.R12, Range.R12, Range.R12 });

	public static final Triad MAJ = new Triad(Interval.M3, Interval.P5, ROOT, new Range[] { Range.R12, Range.R12, Range.R12 });
	public static final Triad MAJ53 = MAJ;
	public static final Triad MAJ6 = new Triad(Interval.m3, Interval.m6, FIRST, new Range[] { Range.R12, Range.R12, Range.R12 });
	public static final Triad MAJ63 = MAJ6;
	public static final Triad MAJ64 = new Triad(Interval.P4, Interval.M6, SECOND, new Range[] { Range.R12, Range.R12, Range.R11 });
	public static final Triad MAJ_TRIP_ROOT = new Triad(Interval.M3, Interval.P5, ROOT, new Range[] { Range.R13, Range.R11, Range.R02 });
	
	public static final Triad DOM = new Triad(Interval.M3, Interval.P5, ROOT, new Range[] { Range.R12, Range.R12, Range.R12 }, new SignedInterval[][] {
		null, 
		new SignedInterval[] { SignedInterval.UNISON, new SignedInterval(Interval.m2) },
		null
	});
	public static final Triad DOM6 = new Triad(Interval.m3, Interval.m6, FIRST, new Range[] { Range.R12, Range.R12, Range.R12 }, new SignedInterval[][] {
		new SignedInterval[] { SignedInterval.UNISON, new SignedInterval(Interval.m2) },
		null,
		null
	});
	public static final Triad DOM64 = new Triad(Interval.P4, Interval.M6, SECOND, new Range[] { Range.R12, Range.R12, Range.R11 }, new SignedInterval[][] {
		null,
		null,
		new SignedInterval[] { SignedInterval.UNISON, new SignedInterval(Interval.m2) }
	});
	
	public static final Triad MIN = new Triad(Interval.m3, Interval.P5, ROOT, new Range[] { Range.R12, Range.R12, Range.R12 });
	public static final Triad MIN53 = MIN;
	public static final Triad MIN6 = new Triad(Interval.M3, Interval.M6, FIRST, new Range[] { Range.R12, Range.R12, Range.R12 });
	public static final Triad MIN63 = MIN6;
	public static final Triad MIN64 = new Triad(Interval.P4, Interval.m6, SECOND, new Range[] { Range.R12, Range.R12, Range.R11 });
	public static final Triad MIN_TRIP_ROOT = new Triad(Interval.m3, Interval.P5, ROOT, new Range[] { Range.R13, Range.R11, Range.R02 });
	
	public static final Triad DIM = new Triad(Interval.m3, Interval.d5, ROOT, new Range[] { Range.R11, Range.R12, Range.R11 }, new SignedInterval[][] {
		new SignedInterval[] { SignedInterval.UNISON, new SignedInterval(Interval.m2) },
		null,
		new SignedInterval[] { SignedInterval.UNISON, new SignedInterval(Interval.m2, false), new SignedInterval(Interval.M2, false) }
	});
	public static final Triad DIM53 = DIM;
	public static final Triad DIM6 = new Triad(Interval.m3, Interval.M6, FIRST, new Range[] { Range.R12, Range.R12, Range.R12 }, new SignedInterval[][] {
		null,
		new SignedInterval[] { SignedInterval.UNISON, new SignedInterval(Interval.m2, false), new SignedInterval(Interval.M2, false) },
		new SignedInterval[] { SignedInterval.UNISON, new SignedInterval(Interval.m2) }
	});
	public static final Triad DIM63 = DIM6;
	public static final Triad DIM64 = new Triad(Interval.A4, Interval.M6, SECOND, new Range[] { Range.R12, Range.R12, Range.R11 }, new SignedInterval[][] {
		new SignedInterval[] { SignedInterval.UNISON, new SignedInterval(Interval.m2, false), new SignedInterval(Interval.M2, false) },
		new SignedInterval[] { SignedInterval.UNISON, new SignedInterval(Interval.m2) },
		null
	});
	
	private Interval middle;
	private Interval top;
	private int inversion;
	private Range[] allowedAmount;
	private int df;
	private SignedInterval[][] requiredNext;
	
	public Triad(Interval middle, Interval top, int inversion, Range[] allowedAmount) {
		this(middle, top, inversion, allowedAmount, null);
	}
	
	public Triad(Interval middle, Interval top, int inversion, Range[] allowedAmount, SignedInterval[][] requiredNext) {
		if(middle == null || top == null) throw new IllegalArgumentException("Middle and Top must be non-null values.");
		if(middle.lt(Interval.UNISON)) throw new IllegalArgumentException("Middle cannot be lower than a perfect unison.");
		if(middle.gt(top)) throw new IllegalArgumentException("Top interval cannot be lower than middle interval.");
		if(top.gt(Interval.OCTAVE)) throw new IllegalArgumentException("Top interval cannot exceed a perfect octave.");
		if(inversion != ROOT && inversion != FIRST && inversion != SECOND) throw new IllegalArgumentException("Chord must be in root, first, or second inversion.");
		if(allowedAmount.length != 3) throw new IllegalArgumentException("Ranges must be of length 3");
		this.inversion = inversion;
		this.middle = middle;
		this.top = top;
		this.allowedAmount = allowedAmount;
		df = 4 - allowedAmount[0].getMin() - allowedAmount[1].getMin() - allowedAmount[2].getMin();
		this.requiredNext = requiredNext;
	}
	
	public Triad loosenTo(Range[] newAllowedAmount) {
		return new Triad(middle, top, inversion, newAllowedAmount);
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

	@Override
	public Interval rootToBass() {
		if(inversion == ROOT) {
			return Interval.UNISON;
		}else if(inversion == FIRST) {
			return Interval.OCTAVE.sub(top);
		}else if(inversion == SECOND) {
			return Interval.OCTAVE.sub(middle);
		}
		return null;
	}
	
	@Override
	public boolean hasPotential(Tone bass, List<Tone> tones) {
		int[] counter = new int[3];
		for(Tone t : tones) {
			if(Interval.intervalBetween(bass, bass.nextInstanceOf(t)).equals(Interval.UNISON)) {
				counter[0]++;
			}else if(Interval.intervalBetween(bass, bass.nextInstanceOf(t)).equals(middle)) {
				counter[1]++;
			}else if(Interval.intervalBetween(bass, bass.nextInstanceOf(t)).equals(top)) {
				counter[2]++;
			}
		}
		int cumulativedf = 0;
		for(int i = 0; i < counter.length; i++) {
			if(counter[i] > allowedAmount[i].getMax()) return false;
			if(counter[i] - allowedAmount[i].getMin() > 0) {
				cumulativedf += counter[i] - allowedAmount[i].getMin();
			}
		}
		if(cumulativedf > df) return false;
		return true;
	}
	
	@Override
	public Tone[] allowedNext(Tone bass, Tone curr) {
		if(requiredNext == null) return null;
		Interval interval = bass.intervalTo(curr).normalize();
		
		if(interval.enharmonic(Interval.UNISON)) {
			if(requiredNext[0] == null) return null;
			return Arrays.asList(requiredNext[0]).stream().map(i -> i.addTo(curr)).collect(Collectors.toList()).toArray(new Tone[0]);	
		}
		if(interval.enharmonic(middle)) {
			if(requiredNext[1] == null) return null;
			return Arrays.asList(requiredNext[1]).stream().map(i -> i.addTo(curr)).collect(Collectors.toList()).toArray(new Tone[0]);		
		}
		if(interval.enharmonic(top)) {
			if(requiredNext[2] == null) return null;
			return Arrays.asList(requiredNext[2]).stream().map(i -> i.addTo(curr)).collect(Collectors.toList()).toArray(new Tone[0]);	
		}
		
		return null;
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
