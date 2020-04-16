package com.charliepianist.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Voice {
	ArrayList<Tone> tones;
	Tone top, bottom; // Edges of range

	public static int ENTROPY_NONE = 0;
	public static int ENTROPY_START = 1;
	public static int ENTROPY_ALL = 2;
	
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
	
	public List<Tone> filterInRange(Collection<Tone> options) {
		return filterInRange(options, ENTROPY_NONE);
	}
	
	public List<Tone> filterInRange(Collection<Tone> options, int entropy) {
		return filterInRange(options, entropy, true);
	}
	
	// Just filter options for notes that are in this voice's range, and within an octave of previous note, if applicable
	// Specifically can be useful for Bass with looser voice leading requirements
	public List<Tone> filterInRange(Collection<Tone> options, int entropy, boolean withinOctave) {
		List<Tone> tones = options.stream().filter(t -> t.geq(bottom) && t.leq(top)).collect(Collectors.toList());
		if(withinOctave && this.tones.size() >= 1) {
			Tone lastTone = this.tones.get(this.tones.size() - 1);
			tones = tones.stream().filter(t -> t.leq(lastTone.up(Interval.OCTAVE)) && t.geq(lastTone.down(Interval.OCTAVE))).collect(Collectors.toList());
		}
		if(entropy >= ENTROPY_ALL || (entropy >= ENTROPY_START && this.isEmpty())) Collections.shuffle(tones);
		return tones;
	}
	
	public List<Tone> validMoves(Chord chord, Tone bass, Tone min, Tone max) {
		Tone lowest = bottom.nextInstanceOf(bass);
		Collection<Tone> options = chord.tones(lowest);
		return validMoves(options, min, max);
	}
	
	public List<Tone> validMoves(Collection<Tone> options, Tone min, Tone max) {
		return validMoves(options, min, max, ENTROPY_NONE);
	}
	
	// Possible moves among list of Tones tones, with restrictions from other voices min, max
	// Entropy refers to shuffling
	// Sort refers to whether to sort or leave in order from lowest to highest
	public List<Tone> validMoves(Collection<Tone> options, Tone min, Tone max, int entropy) {
		int STEP = 2;
		int SMALL_LEAP = 4;
		
		Tone origMin = min;
		Tone origMax = max;
		ArrayList<Tone> moves = new ArrayList<Tone>();
		min = Tone.max(min, bottom);
		max = Tone.min(max, top);
		int len = tones.size();
		
		if(len > 1) {
			Tone last = this.tones.get(len - 1);
			Tone secondLast = this.tones.get(len - 2);
			int dist = secondLast.dist(last);
			if(dist > STEP) {
				if(dist > SMALL_LEAP) {
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
		boolean allowOrigMax = false;
		if(len > 0) {
			Tone last = this.tones.get(len - 1);
			for(Tone option : options) {
				if(option.geq(min) && option.leq(max)) {
					if(Interval.intervalBetween(option, last).leq(Interval.OCTAVE)) {
						if(option.enharmonic(origMin)) {
							allowOrigMin = true;
							continue;
						}
						if(option.enharmonic(origMax)) {
							allowOrigMax = true;
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
					if(option.enharmonic(origMax)) {
						allowOrigMax = true;
						continue;
					}
					moves.add(option);
				}
			}
		}
		
		// Sort by ideal distance given consecutive steps leading up to this point
		if(entropy < ENTROPY_ALL) {
			if(!this.isEmpty()) {
				int consecutiveSteps = 0;
				for(int i = this.tones.size() - 2; i >= 0; i--) {
					if(tones.get(i).dist(tones.get(i + 1)) <= STEP) {
						consecutiveSteps++;
					}
				}
				int idealDist = idealInterval(consecutiveSteps);
				Collections.sort(moves, Tone.byDistanceTo(this.last(), idealDist));
			}else {
				if(entropy < ENTROPY_START) {
					Collections.sort(moves, Tone.byDistanceTo(Tone.avg(bottom, top), 0)); 
				}else Collections.shuffle(moves);
			}
		}else Collections.shuffle(moves);
		
		// We want to avoid adding the minimum/maximums given to us if possible (i.e. check those last)
		if(allowOrigMin && allowOrigMax) {
			int i = (int) (Math.random() * 2);
			if(i == 0) {
				moves.add(origMin);
				moves.add(origMax);
			}else {
				moves.add(origMax);
				moves.add(origMin);
			}
		}else {
			if(allowOrigMin) moves.add(origMin);
			if(allowOrigMax) moves.add(origMax);
		}
		return moves;
	}
	
	// Do v1 and v2 have parallel perfect intervals if we were to add t1 to v1 and t2 to v2?
	public static boolean parallelPerfectIntervals(Voice v1, Voice v2, Tone t1, Tone t2) {
		if(v1.isEmpty() || v2.isEmpty()) return false;
		Interval interval = Interval.normalize(t1.intervalTo(t2));
		
		if(interval.enharmonic(Interval.normalize(v1.last().intervalTo(v2.last())))) {
			if(interval.enharmonic(Interval.P5) || interval.enharmonic(Interval.OCTAVE) || interval.enharmonic(Interval.UNISON)) {
				if(!v1.last().equals(t1) && !v2.last().equals(t2)) {
					return true;
				}
			}
		}
		return false;
	}
	
	// What is the ideal distance to move given some number of consecutive steps?
	public static int idealInterval(int consecutiveSteps) {
		if(consecutiveSteps < 2) {
			return 0;
		}else if(consecutiveSteps < 4) {
			return 0;
		}else if(consecutiveSteps == 4) {
			return 0;
		}else {
			return 0;
		}
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
