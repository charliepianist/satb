package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Voice {
	ArrayList<Tone> tones;
	Tone top, bottom; // Edges of range
	
	public Voice(Tone top, Tone bottom) {
		this(new ArrayList<Tone>(), top, bottom);
	}
	
	public Voice(ArrayList<Tone> tones, Tone bottom, Tone top) {
		this.tones = tones;
		this.top = top;
		this.bottom = bottom;
	}
	
	public void add(Tone t) {
		tones.add(t);
	}
	
	public Tone pop() {
		return tones.remove(tones.size() - 1);
	}
	
	public Tone last() {
		return tones.get(tones.size() - 1);
	}
	
	public ArrayList<Tone> getLine() {
		return tones;
	}
	
	public boolean isEmpty() {
		return tones.isEmpty();
	}
	
	public Collection<Tone> filterInRange(Collection<Tone> options) {
		return filterInRange(options, true, true);
	}
	
	public Collection<Tone> filterInRange(Collection<Tone> options, boolean withinOctave) {
		return filterInRange(options, withinOctave, true);
	}
	
	// Just filter options for notes that are in this voice's range, and within an octave of previous note, if applicable
	// Specifically can be useful for Bass with looser voice leading requirements
	public Collection<Tone> filterInRange(Collection<Tone> options, boolean withinOctave, boolean shuffle) {
		List<Tone> tones = options.stream().filter(t -> t.geq(bottom) && t.leq(top)).collect(Collectors.toList());
		if(withinOctave && this.tones.size() >= 1) {
			Tone lastTone = this.tones.get(this.tones.size() - 1);
			tones = tones.stream().filter(t -> t.leq(lastTone.up(Interval.OCTAVE)) && t.geq(lastTone.down(Interval.OCTAVE))).collect(Collectors.toList());
		}
		if(shuffle) Collections.shuffle(tones);
		return tones;
	}
	
	public Collection<Tone> validMoves(Chord chord, Tone bass, Tone min, Tone max) {
		Tone lowest = bottom.nextInstanceOf(bass);
		Collection<Tone> options = chord.tones(lowest);
		return validMoves(options, min, max);
	}
	
	public Collection<Tone> validMoves(Collection<Tone> options, Tone min, Tone max) {
		return validMoves(options, min, max, true);
	}
	
	// Possible moves among list of Tones tones, with restrictions from other voices min, max
	public Collection<Tone> validMoves(Collection<Tone> options, Tone min, Tone max, boolean sort) {
		Tone origMin = min;
		ArrayList<Tone> moves = new ArrayList<Tone>();
		min = Tone.max(min, bottom);
		max = Tone.min(max, top);
		int len = tones.size();
		
		if(len > 1) {
			Tone last = tones.get(len - 1);
			Tone secondLast = tones.get(len - 2);
			int dist = secondLast.dist(last);
			if(dist > 2) {
				if(dist > 4) {
					// Greater than a 3rd
					if(secondLast.gt(last)) {
						// Previous leap was downwards
						min = Tone.max(min, last);
						max = Tone.min(max, last.up(Interval.M3));
					}else {
						// Previous leap was upwards
						max = Tone.min(max, last);
						min = Tone.max(min, last.down(Interval.M3));
					}
				}else {
					// Between 2nd and 3rd
					if(secondLast.gt(last)) {
						// Previous leap was downwards
						min = Tone.max(min, last.down(Interval.M2));
					}else {
						// Previous leap was upwards
						max = Tone.min(max, last.up(Interval.M2));
					}
				}
			}
		}
		
		boolean allowOrigMin = false;
		if(len > 0) {
			Tone last = tones.get(len - 1);
			for(Tone option : options) {
				if(option.geq(min) && option.leq(max)) {
					if(Interval.intervalBetween(option, last).leq(Interval.OCTAVE)) {
						if(option.enharmonic(origMin)) {
							allowOrigMin = true;
							continue;
						}
						moves.add(option);
					}
				}
			}
		}else {
			for(Tone option : options) {
				if(option.geq(min) && option.leq(max)) {
					if(option.enharmonic(origMin)) {
						allowOrigMin = true;
						continue;
					}
					moves.add(option);
				}
			}
		}
		
		if(sort && !this.isEmpty()) {
			Collections.sort(moves, Tone.byDistanceTo(this.last()));
		}else Collections.shuffle(moves);
		if(allowOrigMin) moves.add(origMin);
		return moves;
	}
	
	// Do v1 and v2 have parallel perfect intervals if we were to add t1 to v1 and t2 to v2?
	public static boolean parallelPerfectIntervals(Voice v1, Voice v2, Tone t1, Tone t2) {
		if(v1.isEmpty() || v2.isEmpty()) return false;
		Interval interval = Interval.normalize(t1.intervalTo(t2));
		
		if(interval.enharmonic(Interval.normalize(v1.last().intervalTo(v2.last())))) {
			if(interval.enharmonic(Interval.P5) || interval.enharmonic(Interval.OCTAVE) || interval.enharmonic(Interval.UNISON)) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		Collection<Tone> Cmaj = Triad.MAJ.tones(Tone.C3);
		Voice testSop = new Voice(Tone.C4, Tone.C6);
		
		System.out.println("All options: " + Cmaj);
		System.out.println(testSop.validMoves(Cmaj, Tone.MIN_TONE, Tone.MAX_TONE));
		testSop.add(Tone.D4.up(Interval.OCTAVE));
		testSop.add(Tone.F4.up(Interval.OCTAVE));
		System.out.println(testSop.validMoves(Cmaj, Tone.MIN_TONE, Tone.MAX_TONE));
	}
}
