package main;

import java.util.Collection;
import java.util.List;

public interface Chord {
	// Intervals of acceptable notes *relative to the bass* (the lowest note), including the bass (Perfect Unison)
	// Returned in increasing order of magnitude.
	public Collection<Interval> intervals();
	
	// Acceptable tones in the chord (including the bass), given bass note _bass_.
	// Returned in order of increasing pitch.
	public Collection<Tone> tones(Tone bass);
	
	// One of each interval in the chord, in increasing order.
	public Collection<Interval> baseIntervals();
	
	// One of each tone in the chord, in increasing order of pitch.
	public Collection<Tone> baseChord(Tone bass);
	
	// Interval from root tone *up to* bass tone (*add* interval to get bass tone from root tone).
	public Interval rootToBass();
	
	// Is the current list of tones allowed?
	public boolean hasPotential(Tone bass, List<Tone> tones);
}
