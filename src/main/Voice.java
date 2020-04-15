package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
	
	public Collection<Tone> possibleMoves(Collection<Tone> options, Tone min, Tone max) {
		return possibleMoves(options, min, max, true);
	}
	
	// Possible moves among list of Tones tones, with restrictions from other voices min, max
	public Collection<Tone> possibleMoves(Collection<Tone> options, Tone min, Tone max, boolean shuffle) {
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
		
		if(len > 0) {
			Tone last = tones.get(len - 1);
			for(Tone option : options) {
				if(option.geq(min) && option.leq(max)) {
					if(Interval.intervalBetween(option, last).leq(Interval.OCTAVE)) {
						moves.add(option);
					}
				}
			}
		}else {
			for(Tone option : options) {
				if(option.geq(min) && option.leq(max)) {
					moves.add(option);
				}
			}
		}
		
		if(shuffle) Collections.shuffle(moves);
		return moves;
	}
	
	public static void main(String[] args) {
		Collection<Tone> Cmaj = Triad.MAJ.tones(Tone.C3);
		Voice testSop = new Voice(Tone.C4, Tone.C6);
		
		System.out.println("All options: " + Cmaj);
		System.out.println(testSop.possibleMoves(Cmaj, Tone.MIN_TONE, Tone.MAX_TONE));
		testSop.add(Tone.D4.up(Interval.OCTAVE));
		testSop.add(Tone.F4.up(Interval.OCTAVE));
		System.out.println(testSop.possibleMoves(Cmaj, Tone.MIN_TONE, Tone.MAX_TONE));
	}
}
