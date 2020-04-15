package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SATBGenerator {
	public static final Tone SOPRANO_TOP = Tone.HIGHEST_TONE;
	public static final Tone SOPRANO_BOTTOM = Tone.C4;
	public static final Tone ALTO_TOP = Tone.D4.up(Interval.OCTAVE); // D5
	public static final Tone ALTO_BOTTOM = Tone.F4.down(Interval.OCTAVE); // F3
	public static final Tone TENOR_TOP = Tone.A4;
	public static final Tone TENOR_BOTTOM = Tone.C3;
	public static final Tone BASS_TOP = Tone.E4;
	public static final Tone BASS_BOTTOM = Tone.LOWEST_TONE; // E2
	
	private Voice s, a, t, b;
	
	public SATBGenerator() {
		s = new Voice(SOPRANO_BOTTOM, SOPRANO_TOP);
		a = new Voice(ALTO_BOTTOM, ALTO_TOP);
		t = new Voice(TENOR_BOTTOM, TENOR_TOP);
		b = new Voice(BASS_BOTTOM, BASS_TOP);
	}
	
	public Voice[] generateSATB(Tone tonic, List<Interval> bassIntervals, List<Chord> chords) {
		if(bassIntervals.size() != chords.size()) throw new IllegalArgumentException("bassIntervals must have same size as chords.");
		if(bassIntervals.size() == 0 || chords.size() == 0) throw new IllegalArgumentException("bassIntervals and chords cannot be empty.");
		tonic = Tone.LOWEST_TONE.nextInstanceOf(tonic);
		
		if(generate(tonic, bassIntervals, chords, 0)) {
			return new Voice[] { s, a, t, b };
		}
		return null;
	}
	
	// Todo: Tritone resolution?
	// True indicates success
	private boolean generate(Tone tonic, List<Interval> bassIntervals, List<Chord> chords, int index) {
		Tone root = BASS_BOTTOM.nextInstanceOf(tonic.up(bassIntervals.get(index)));
		Chord chord = chords.get(index);
		Collection<Tone> tones = chord.tones(root);
		ArrayList<Tone> currTones = new ArrayList<Tone>();
		int numChords = bassIntervals.size();
		
		for(Tone bassTone : b.filterInRange(root.allInstances())) {
			// Bass must be there, so no need to check anything for this part
			currTones.add(bassTone);
			for(Tone tenorTone : t.validMoves(tones, bassTone, bassTone.up(Interval.TWO_OCTAVES), false)) {
				// Check for parallel intervals
				if(Voice.parallelPerfectIntervals(b, t, bassTone, tenorTone)) {
					continue;
				}
				// Are the current tones so far even eligible to form a valid chord?
				currTones.add(tenorTone);
				if(!chord.hasPotential(bassTone, currTones)) {
					currTones.remove(1);
					continue;
				}

				for(Tone altoTone : a.validMoves(tones, tenorTone, tenorTone.up(Interval.OCTAVE))) {
					// Check for parallel intervals
					if(Voice.parallelPerfectIntervals(b, a, bassTone, altoTone)
							|| Voice.parallelPerfectIntervals(t, a, tenorTone, altoTone)) {
						continue;
					}
					// Are the current tones so far even eligible to form a valid chord?
					currTones.add(altoTone);
					if(!chord.hasPotential(bassTone, currTones)) {
						currTones.remove(2);
						continue;
					}
					
					
					for(Tone sopranoTone : s.validMoves(tones, altoTone, altoTone.up(Interval.OCTAVE))) {
						// Check for parallel intervals
						if(Voice.parallelPerfectIntervals(b, s, bassTone, sopranoTone)
								|| Voice.parallelPerfectIntervals(t, s, tenorTone, sopranoTone)
								|| Voice.parallelPerfectIntervals(a, s, altoTone, sopranoTone)) {
							continue;
						}
						currTones.add(sopranoTone);
						if(!chord.hasPotential(bassTone, currTones)) {
							currTones.remove(3);
							continue;
						}
						s.add(sopranoTone);
						a.add(altoTone);
						t.add(tenorTone);
						b.add(bassTone);
						//System.out.println(String.join(" ", bassTone.toString(), tenorTone.toString(), altoTone.toString(), sopranoTone.toString()));
						// Attempt using this combination of tones
						if(index == numChords - 1) {
							// We have found a combination for the last chord!
							return true;
						}
						// Attempt to build off of this combination of tones
						if(generate(tonic, bassIntervals, chords, index + 1)) {
							return true;
						}
						s.pop();
						a.pop();
						t.pop();
						b.pop();
						
						currTones.remove(3);
					}
					currTones.remove(2);
				}
				currTones.remove(1);
			}
			currTones.remove(0);
		}
		// Went through all possible combinations given past history and failed, so return false
		return false;
	}
}
