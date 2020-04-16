package com.charliepianist.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.charliepianist.main.Interval.SignedInterval;

public class SATBGenerator {
	public static final int ENTROPY_NONE = Voice.ENTROPY_NONE;
	public static final int ENTROPY_START = Voice.ENTROPY_START;
	public static final int ENTROPY_ALL = Voice.ENTROPY_ALL;
	
	public static final Tone SOPRANO_TOP = Tone.HIGHEST_TONE;
	public static final Tone SOPRANO_BOTTOM = Tone.C4;
	public static final Tone ALTO_TOP = Tone.D4.up(Interval.OCTAVE); // D5
	public static final Tone ALTO_BOTTOM = Tone.F4.down(Interval.OCTAVE); // F3
	public static final Tone TENOR_TOP = Tone.A4;
	public static final Tone TENOR_BOTTOM = Tone.C3;
	public static final Tone BASS_TOP = Tone.E4;
	public static final Tone BASS_BOTTOM = Tone.LOWEST_TONE; // E2
	
	// Preferred resolutions for each interval from the tonic in decreasing order of preference
	public static final HashMap<Interval, SignedInterval[]> preferredResolutions;
	static {
		preferredResolutions = new HashMap<Interval, SignedInterval[]>();
		preferredResolutions.put(Interval.m6, new SignedInterval[] { new SignedInterval(Interval.m2, false), SignedInterval.UNISON });
		preferredResolutions.put(Interval.A5, new SignedInterval[] { new SignedInterval(Interval.m2), SignedInterval.UNISON });
		preferredResolutions.put(Interval.M7, new SignedInterval[] { new SignedInterval(Interval.m2), SignedInterval.UNISON, new SignedInterval(Interval.M2, false) });
	}
	
	private Voice s, a, t, b;
	
	public SATBGenerator() {
		s = new Voice(SOPRANO_BOTTOM, SOPRANO_TOP);
		a = new Voice(ALTO_BOTTOM, ALTO_TOP);
		t = new Voice(TENOR_BOTTOM, TENOR_TOP);
		b = new Voice(BASS_BOTTOM, BASS_TOP);
	}
	
	public Voice[] generateSATB(Tone tonic, List<Interval> bassIntervals, List<Chord> chords, int entropy) {
		if(bassIntervals.size() != chords.size()) throw new IllegalArgumentException("bassIntervals must have same size as chords.");
		if(bassIntervals.size() == 0 || chords.size() == 0) throw new IllegalArgumentException("bassIntervals and chords cannot be empty.");
		tonic = Tone.LOWEST_TONE.nextInstanceOf(tonic);
		
		if(generate(tonic, bassIntervals, chords, 0, entropy, true)) {
			return new Voice[] { s, a, t, b };
		}else if(generate(tonic, bassIntervals, chords, 0, entropy, false)) {
			System.out.println("Relaxing strict voice leading... (failed to generate SATB with strict voice leading)");
			return new Voice[] { s, a, t, b };
		}
		return null;
	}
	
	// True indicates success
	// Voices handle leaps and stepwise motion
	// SATBGenerator handles most of the SATB voice-leading (e.g. tritone resolution, raised interval back down)
	private boolean generate(Tone tonic, List<Interval> bassIntervals, List<Chord> chords, int index, int entropy, boolean strictLeading) {
		Tone root = BASS_BOTTOM.nextInstanceOf(tonic.up(bassIntervals.get(index))); // Lowest in-range note that can be the root of the chord
		Tone minTonic = Tone.MIN_TONE.nextInstanceOf(tonic);
		Chord chord = chords.get(index);
		List<Tone> tones = chord.tones(root);
		ArrayList<Tone> currTones = new ArrayList<Tone>();
		int numChords = bassIntervals.size();
		
		// Adding root is effectively equivalent to adding the actual bass:
		// 1) Normalized interval is still the same as for any higher octave of that note that is below the other voices
		// 2) hasPotential checks only based on key/accidentals, not octaves
		currTones.add(root);
		
		// Setup for the following
		List<Tone> sOptions, aOptions, tOptions, bOptions;
		Tone[] sTemp, aTemp, tTemp, bTemp;
		Chord prevChord = null;
		Tone prevRoot = null;
		if(index >= 1) {
			prevChord = chords.get(index - 1);
			prevRoot = BASS_BOTTOM.nextInstanceOf(tonic.up(bassIntervals.get(index - 1)));
		}
		// Is there a limited set of tones allowed?
		sOptions = tones;
		if(strictLeading && index >= 1) {
			sTemp = prevChord.allowedNext(prevRoot, s.last());
			if(sTemp != null) {
				sOptions = Arrays.asList(sTemp).stream()
						.distinct()
						.filter(sOptions::contains)
						.collect(Collectors.toList());
			}
		}
		// Final contents, but not final order
		sOptions = s.validMoves(sOptions, root, Tone.MAX_TONE, entropy);
		
		// Prioritize preferred resolutions
		if(index >= 1) {
			Tone sLast = s.last();
			Interval fromTonic = minTonic.intervalTo(sLast).normalize(); 
			if(preferredResolutions.containsKey(fromTonic) && sOptions.size() > 0) {
				SignedInterval[] prefs = preferredResolutions.get(fromTonic);
				for(int i = prefs.length - 1; i >= 0; i--) {
					Util.moveToFront(sOptions, prefs[i].addTo(sLast));
				}
			}
		}
			
		for(Tone sopranoTone : sOptions) {
			// Check for parallel intervals
			if(Voice.parallelPerfectIntervals(b, s, root, sopranoTone)) {
				continue;
			}
			
			// Are the current tones so far even eligible to form a valid chord?
			currTones.add(sopranoTone);
			if(!chord.hasPotential(root, currTones)) {
				currTones.remove(1);
				continue;
			}
			
			// Is there a limited set of tones allowed?
			tOptions = tones;
			if(strictLeading && index >= 1) {
				tTemp = prevChord.allowedNext(prevRoot, t.last());
				if(tTemp != null) {
					tOptions = Arrays.asList(tTemp).stream()
							.distinct()
							.filter(tOptions::contains)
							.collect(Collectors.toList());
				}
			}
			// Final contents, but not final order
			tOptions = t.validMoves(tOptions, root, sopranoTone, entropy);
			
			// Prioritize preferred resolutions
			if(index >= 1) {
				Tone tLast = t.last();
				Interval fromTonic = minTonic.intervalTo(tLast).normalize(); 
				if(preferredResolutions.containsKey(fromTonic) && tOptions.size() > 0) {
					SignedInterval[] prefs = preferredResolutions.get(fromTonic);
					for(int i = prefs.length - 1; i >= 0; i--) {
						Util.moveToFront(tOptions, prefs[i].addTo(tLast));
					}
				}
			}
			
			for(Tone tenorTone : tOptions) {

				// Check for parallel intervals
				if(Voice.parallelPerfectIntervals(b, t, root, tenorTone)
						|| Voice.parallelPerfectIntervals(t, s, tenorTone, sopranoTone)) {
					continue;
				}
				// Are the current tones so far even eligible to form a valid chord?
				currTones.add(tenorTone);
				if(!chord.hasPotential(root, currTones)) {
					currTones.remove(2);
					continue;
				}
				
				// Is there a limited set of tones allowed?
				aOptions = tones;
				if(strictLeading && index >= 1) {
					aTemp = prevChord.allowedNext(prevRoot, a.last());
					if(aTemp != null) {
						aOptions = Arrays.asList(aTemp).stream()
								.distinct()
								.filter(aOptions::contains)
								.collect(Collectors.toList());
					}
				}
				// Final contents, but not final order
				aOptions = a.validMoves(aOptions, Tone.max(tenorTone, sopranoTone.down(Interval.OCTAVE)), Tone.min(sopranoTone, tenorTone.up(Interval.OCTAVE)), entropy);
				
				// Prioritize preferred resolutions
				if(index >= 1) {
					Tone aLast = a.last();
					Interval fromTonic = minTonic.intervalTo(aLast).normalize(); 
					if(preferredResolutions.containsKey(fromTonic) && aOptions.size() > 0) {
						SignedInterval[] prefs = preferredResolutions.get(fromTonic);
						for(int i = prefs.length - 1; i >= 0; i--) {
							Util.moveToFront(aOptions, prefs[i].addTo(aLast));
						}
					}
				}
				
				for(Tone altoTone : aOptions) {
					
					// Check for parallel intervals
					if(Voice.parallelPerfectIntervals(b, a, root, altoTone)
							|| Voice.parallelPerfectIntervals(t, a, tenorTone, altoTone)
							|| Voice.parallelPerfectIntervals(a, s, altoTone, sopranoTone)) {
						continue;
					}
					currTones.add(altoTone);
					if(!chord.hasPotential(root, currTones)) {
						currTones.remove(3);
						continue;
					}
					
					// Is there a limited set of tones allowed?
					bOptions = root.allInstances();
					if(strictLeading && index >= 1) {
						bTemp = prevChord.allowedNext(prevRoot, b.last());
						if(bTemp != null) {
							bOptions = Arrays.asList(bTemp).stream()
									.distinct()
									.filter(bOptions::contains)
									.collect(Collectors.toList());
						}	
					}
					bOptions = b.filterInRange(bOptions, entropy);
					
					// Prioritize preferred resolutions
					if(index >= 1) {
						Tone bLast = b.last();
						Interval fromTonic = minTonic.intervalTo(bLast).normalize(); 
						if(preferredResolutions.containsKey(fromTonic) && bOptions.size() > 0) {
							SignedInterval[] prefs = preferredResolutions.get(fromTonic);
							for(int i = prefs.length - 1; i >= 0; i--) {
								Util.moveToFront(bOptions, prefs[i].addTo(bLast));
							}
					
						}
					}
					
					for(Tone bassTone : bOptions) {
						if(bassTone.gt(tenorTone)) continue;
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
						if(generate(tonic, bassIntervals, chords, index + 1, entropy, strictLeading)) {
							return true;
						}
						s.pop();
						a.pop();
						t.pop();
						b.pop();
					}
					currTones.remove(3);
				}
				currTones.remove(2);
			}
			currTones.remove(1);
		}
		// Went through all possible combinations given past history and failed, so return false
		return false;
	}
}
