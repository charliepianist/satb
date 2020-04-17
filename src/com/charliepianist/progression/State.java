package com.charliepianist.progression;

import com.charliepianist.main.Chord;
import com.charliepianist.main.Interval;
import com.charliepianist.main.Seventh;
import com.charliepianist.main.Triad;

public class State {
	private Chord chord; // Chord quality
	private Interval bassInterval; // Interval of bass from tonic (normalized)
	
	// MAJOR KEY TRIADS - STANDARD

	public static final State MAJ_I = new State(Triad.MAJ, Interval.UNISON);
	public static final State MAJ_I6 = new State(Triad.MAJ63, Interval.UNISON);
	public static final State MAJ_I64 = new State(Triad.MAJ64, Interval.UNISON);
	public static final State MAJ_ii = new State(Triad.MIN, Interval.M2);
	public static final State MAJ_ii6 = new State(Triad.MIN63, Interval.M2);
	public static final State MAJ_ii64 = new State(Triad.MIN64, Interval.M2);
	public static final State MAJ_iii = new State(Triad.MIN, Interval.M3);
	public static final State MAJ_iii6 = new State(Triad.MIN63, Interval.M3);
	public static final State MAJ_iii64 = new State(Triad.MIN64, Interval.M3);
	public static final State MAJ_IV = new State(Triad.MAJ, Interval.P4);
	public static final State MAJ_IV6 = new State(Triad.MAJ63, Interval.P4);
	public static final State MAJ_IV64 = new State(Triad.MAJ64, Interval.P4);
	public static final State MAJ_V = new State(Triad.MAJ, Interval.P5);
	public static final State MAJ_V6 = new State(Triad.MAJ63, Interval.P5);
	public static final State MAJ_V64 = new State(Triad.MAJ64, Interval.P5);
	public static final State MAJ_vi = new State(Triad.MIN, Interval.M6);
	public static final State MAJ_vi6 = new State(Triad.MIN63, Interval.M6);
	public static final State MAJ_vi64 = new State(Triad.MIN64, Interval.M6);
	public static final State MAJ_vii = new State(Triad.DIM, Interval.M7);
	public static final State MAJ_vii6 = new State(Triad.DIM63, Interval.M7);
	public static final State MAJ_vii64 = new State(Triad.DIM64, Interval.M7);
	
	// MAJOR KEY SEVENTHS - STANDARD

	public static final State MAJ_I7 = new State(Seventh.MAJ7, Interval.UNISON);
	public static final State MAJ_I65 = new State(Seventh.MAJ65, Interval.UNISON);
	public static final State MAJ_I43 = new State(Seventh.MAJ43, Interval.UNISON);
	public static final State MAJ_I42 = new State(Seventh.MAJ42, Interval.UNISON);
	public static final State MAJ_ii7 = new State(Seventh.MIN7, Interval.M2);
	public static final State MAJ_ii65 = new State(Seventh.MIN65, Interval.M2);
	public static final State MAJ_ii43 = new State(Seventh.MIN43, Interval.M2);
	public static final State MAJ_ii42 = new State(Seventh.MIN42, Interval.M2);
	public static final State MAJ_IV7 = new State(Seventh.MAJ7, Interval.P4);
	public static final State MAJ_IV65 = new State(Seventh.MAJ65, Interval.P4);
	public static final State MAJ_IV43 = new State(Seventh.MAJ43, Interval.P4);
	public static final State MAJ_IV42 = new State(Seventh.MAJ42, Interval.P4);
	public static final State MAJ_V7 = new State(Seventh.DOM7, Interval.P5);
	public static final State MAJ_V65 = new State(Seventh.DOM65, Interval.P5);
	public static final State MAJ_V43 = new State(Seventh.DOM43, Interval.P5);
	public static final State MAJ_V42 = new State(Seventh.DOM42, Interval.P5);
	public static final State MAJ_vi7 = new State(Seventh.MIN7, Interval.M6);
	public static final State MAJ_vi65 = new State(Seventh.MIN65, Interval.M6);
	public static final State MAJ_vi43 = new State(Seventh.MIN43, Interval.M6);
	public static final State MAJ_vi42 = new State(Seventh.MIN42, Interval.M6);
	public static final State MAJ_vii7 = new State(Seventh.HALFDIM7, Interval.M7);
	public static final State MAJ_vii65 = new State(Seventh.HALFDIM65, Interval.M7);
	public static final State MAJ_vii43 = new State(Seventh.HALFDIM43, Interval.M7);
	public static final State MAJ_vii42 = new State(Seventh.HALFDIM42, Interval.M7);
	
	// MINOR KEY TRIADS - STANDARD

	public static final State MIN_i = new State(Triad.MIN, Interval.UNISON);
	public static final State MIN_i6 = new State(Triad.MIN63, Interval.UNISON);
	public static final State MIN_i64 = new State(Triad.MIN64, Interval.UNISON);
	public static final State MIN_ii = new State(Triad.DIM, Interval.M2);
	public static final State MIN_ii6 = new State(Triad.DIM63, Interval.M2);
	public static final State MIN_ii64 = new State(Triad.DIM64, Interval.M2);
	public static final State MIN_III = new State(Triad.MAJ, Interval.m3);
	public static final State MIN_III6 = new State(Triad.MAJ63, Interval.m3);
	public static final State MIN_III64 = new State(Triad.MAJ64, Interval.m3);
	public static final State MIN_iv = new State(Triad.MIN, Interval.P4);
	public static final State MIN_iv6 = new State(Triad.MIN63, Interval.P4);
	public static final State MIN_iv64 = new State(Triad.MIN64, Interval.P4);
	public static final State MIN_V = new State(Triad.MAJ, Interval.P5);
	public static final State MIN_V6 = new State(Triad.MAJ63, Interval.P5);
	public static final State MIN_V64 = new State(Triad.MAJ64, Interval.P5);
	public static final State MIN_VI = new State(Triad.MAJ, Interval.m6);
	public static final State MIN_VI6 = new State(Triad.MAJ63, Interval.m6);
	public static final State MIN_VI64 = new State(Triad.MAJ64, Interval.m6);
	public static final State MIN_vii = new State(Triad.DIM, Interval.M7);
	public static final State MIN_vii6 = new State(Triad.DIM63, Interval.M7);
	public static final State MIN_vii64 = new State(Triad.DIM64, Interval.M7);
	
	// MINOR KEY SEVENTHS - STANDARD

	public static final State MIN_i7 = new State(Seventh.MIN7, Interval.UNISON);
	public static final State MIN_i65 = new State(Seventh.MIN65, Interval.UNISON);
	public static final State MIN_i43 = new State(Seventh.MIN43, Interval.UNISON);
	public static final State MIN_i42 = new State(Seventh.MIN42, Interval.UNISON);
	public static final State MIN_ii7 = new State(Seventh.HALFDIM7, Interval.M2);
	public static final State MIN_ii65 = new State(Seventh.HALFDIM65, Interval.M2);
	public static final State MIN_ii43 = new State(Seventh.HALFDIM43, Interval.M2);
	public static final State MIN_ii42 = new State(Seventh.HALFDIM42, Interval.M2);
	public static final State MIN_III7 = new State(Seventh.DOM7, Interval.m3);
	public static final State MIN_III65 = new State(Seventh.DOM65, Interval.m3);
	public static final State MIN_III43 = new State(Seventh.DOM43, Interval.m3);
	public static final State MIN_III42 = new State(Seventh.DOM42, Interval.m3);
	public static final State MIN_iv7 = new State(Seventh.MIN7, Interval.P4);
	public static final State MIN_iv65 = new State(Seventh.MIN65, Interval.P4);
	public static final State MIN_iv43 = new State(Seventh.MIN43, Interval.P4);
	public static final State MIN_iv42 = new State(Seventh.MIN42, Interval.P4);
	public static final State MIN_V7 = new State(Seventh.DOM7, Interval.P5);
	public static final State MIN_V65 = new State(Seventh.DOM65, Interval.P5);
	public static final State MIN_V43 = new State(Seventh.DOM43, Interval.P5);
	public static final State MIN_V42 = new State(Seventh.DOM42, Interval.P5);
	public static final State MIN_VI7 = new State(Seventh.MAJ7, Interval.m6);
	public static final State MIN_VI65 = new State(Seventh.MAJ65, Interval.m6);
	public static final State MIN_VI43 = new State(Seventh.MAJ43, Interval.m6);
	public static final State MIN_VI42 = new State(Seventh.MAJ42, Interval.m6);
	public static final State MIN_vii7 = new State(Seventh.DIM7, Interval.M7);
	public static final State MIN_vii65 = new State(Seventh.DIM65, Interval.M7);
	public static final State MIN_vii43 = new State(Seventh.DIM43, Interval.M7);
	public static final State MIN_vii42 = new State(Seventh.DIM42, Interval.M7);
	
	public State(Chord chord, Interval bassInterval) {
		this.chord = chord;
		this.bassInterval = bassInterval;
	}
	
	public Chord getChord() {
		return chord;
	}
	
	public Interval getBassInterval() {
		return bassInterval;
	}
}
