package com.charliepianist.main;

import java.util.List;

import com.charliepianist.main.Util.Range;

public interface Chord {
	// Intervals of acceptable notes *relative to the bass* (the lowest note), including the bass (Perfect Unison)
	// Returned in increasing order of magnitude.
	public List<Interval> intervals();
	
	// Acceptable tones in the chord (including the bass), given bass note _bass_.
	// Returned in order of increasing pitch.
	public List<Tone> tones(Tone bass);
	
	// One of each interval in the chord, in increasing order.
	public List<Interval> baseIntervals();
	
	// One of each tone in the chord, in increasing order of pitch.
	public List<Tone> baseChord(Tone bass);
	
	// Interval from root tone *up to* bass tone (*add* interval to get bass tone from root tone).
	public Interval rootToBass();
	
	// Is it possible to form a chord that satisfies the requirements of the chord
	// (Checking keys only, not octaves)
	public boolean hasPotential(Tone bass, List<Tone> tones);
	
	// Return a Tone if curr must be followed by another tone (or list of tones), or return null if there is no
	// requirement. The array should be sorted in order of preference of following tones.
	public Tone[] allowedNext(Tone bass, Tone curr);
	
	// Return true if and only if this chord, using strict voice leading, can precede next.
	public boolean canPrecedeStrictly(Chord next, Tone thisRoot, Tone nextRoot);
	
	// Returns the allowed amount of the interval passed in (0 -> bass, 1 -> first, 2 -> second, 3 -> third (if applicable), etc)
	public Range allowedAmount(int interval);
	
	@Override
	public boolean equals(Object other);
	
	@Override
	public int hashCode();
	
	public String toString(Tone bass);
}
