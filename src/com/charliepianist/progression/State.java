package com.charliepianist.progression;

import com.charliepianist.main.Chord;
import com.charliepianist.main.Interval;
import com.charliepianist.main.Seventh;
import com.charliepianist.main.Triad;

public class State {
	private Chord chord; // Chord quality
	private Interval rootInterval; // Interval of root from tonic (normalized)
	private boolean atEnd; // Does this end a sequence?
	
	// MAJOR KEY TRIADS - STANDARD

	public static final State MAJ_I = new State(Triad.MAJ, Interval.UNISON);
	public static final State MAJ_I_END = new State(Triad.MAJ, Interval.UNISON, true);
	public static final State MAJ_I_TRIP_ROOT = new State(Triad.MAJ_TRIP_ROOT, Interval.UNISON);
	public static final State MAJ_I_TRIP_ROOT_END = new State(Triad.MAJ_TRIP_ROOT, Interval.UNISON, true);
	public static final State MAJ_I6 = new State(Triad.MAJ63, Interval.UNISON);
	public static final State MAJ_I64 = new State(Triad.MAJ64, Interval.UNISON);
	public static final State MAJ_ii = new State(Triad.MIN, Interval.M2);
	public static final State MAJ_ii6 = new State(Triad.MIN63, Interval.M2);
	public static final State MAJ_ii64 = new State(Triad.MIN64, Interval.M2);
	public static final State MAJ_iii = new State(Triad.MIN, Interval.M3);
	public static final State MAJ_iii6 = new State(Triad.MIN63, Interval.M3);
	public static final State MAJ_iii64 = new State(Triad.MIN64, Interval.M3);
	public static final State MAJ_IV = new State(Triad.MAJ, Interval.P4);
	public static final State MAJ_IV6 = new State(Triad.MAJ6, Interval.P4);
	public static final State MAJ_IV64 = new State(Triad.MAJ64, Interval.P4);
	public static final State MAJ_V = new State(Triad.MAJ, Interval.P5);
	public static final State MAJ_V6 = new State(Triad.MAJ63, Interval.P5);
	public static final State MAJ_V64 = new State(Triad.MAJ64, Interval.P5);
	public static final State MAJ_Vdom = new State(Triad.DOM, Interval.P5);
	public static final State MAJ_V6dom = new State(Triad.DOM6, Interval.P5);
	public static final State MAJ_V64dom = new State(Triad.DOM64, Interval.P5);
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
	public static final State MAJ_V7 = new State(Seventh.DOM7_STRICT, Interval.P5);
	public static final State MAJ_V65 = new State(Seventh.DOM65_STRICT, Interval.P5);
	public static final State MAJ_V43 = new State(Seventh.DOM43_STRICT, Interval.P5);
	public static final State MAJ_V42 = new State(Seventh.DOM42_STRICT, Interval.P5);
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
	public static final State MIN_i_END = new State(Triad.MIN, Interval.UNISON, true);
	public static final State MIN_i_TRIP_ROOT = new State(Triad.MIN_TRIP_ROOT, Interval.UNISON);
	public static final State MIN_i_TRIP_ROOT_END = new State(Triad.MIN_TRIP_ROOT, Interval.UNISON, true);
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
	public static final State MIN_Vdom = new State(Triad.DOM, Interval.P5);
	public static final State MIN_V6dom = new State(Triad.DOM6, Interval.P5);
	public static final State MIN_V64dom = new State(Triad.DOM64, Interval.P5);
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
	public static final State MIN_iv7 = new State(Seventh.MIN7, Interval.P4);
	public static final State MIN_iv65 = new State(Seventh.MIN65, Interval.P4);
	public static final State MIN_iv43 = new State(Seventh.MIN43, Interval.P4);
	public static final State MIN_iv42 = new State(Seventh.MIN42, Interval.P4);
	public static final State MIN_V7 = new State(Seventh.DOM7_STRICT, Interval.P5);
	public static final State MIN_V65 = new State(Seventh.DOM65_STRICT, Interval.P5);
	public static final State MIN_V43 = new State(Seventh.DOM43_STRICT, Interval.P5);
	public static final State MIN_V42 = new State(Seventh.DOM42_STRICT, Interval.P5);
	public static final State MIN_VI7 = new State(Seventh.MAJ7, Interval.m6);
	public static final State MIN_VI65 = new State(Seventh.MAJ65, Interval.m6);
	public static final State MIN_VI43 = new State(Seventh.MAJ43, Interval.m6);
	public static final State MIN_VI42 = new State(Seventh.MAJ42, Interval.m6);
	public static final State MIN_vii7 = new State(Seventh.DIM7, Interval.M7);
	public static final State MIN_vii65 = new State(Seventh.DIM65, Interval.M7);
	public static final State MIN_vii43 = new State(Seventh.DIM43, Interval.M7);
	public static final State MIN_vii42 = new State(Seventh.DIM42, Interval.M7);
	
	public static final State[] ALL_STATES = new State[] { 
		State.MAJ_I, State.MAJ_I_END, State.MAJ_I_TRIP_ROOT, State.MAJ_I_TRIP_ROOT_END, State.MAJ_I7, State.MAJ_I65, State.MAJ_I43, State.MAJ_I42, State.MAJ_ii, State.MAJ_ii6, State.MAJ_ii64, State.MAJ_ii7, State.MAJ_ii65, State.MAJ_ii43, State.MAJ_ii42, State.MAJ_iii, State.MAJ_iii6, State.MAJ_iii64, State.MAJ_IV, State.MAJ_IV6, State.MAJ_IV64, State.MAJ_IV7, State.MAJ_IV65, State.MAJ_IV43, State.MAJ_IV42, State.MAJ_V, State.MAJ_V6, State.MAJ_V64, State.MAJ_Vdom, State.MAJ_V6dom, State.MAJ_V64dom, State.MAJ_V7, State.MAJ_V65, State.MAJ_V43, State.MAJ_V42, State.MAJ_vi, State.MAJ_vi6, State.MAJ_vi64, State.MAJ_vi7, State.MAJ_vi65, State.MAJ_vi43, State.MAJ_vi42, State.MAJ_vii, State.MAJ_vii6, State.MAJ_vii64, State.MAJ_vii7, State.MAJ_vii65, State.MAJ_vii43, State.MAJ_vii42,
		State.MIN_i, State.MIN_i_END, State.MIN_i_TRIP_ROOT, State.MIN_i_TRIP_ROOT_END, State.MIN_i7, State.MIN_i65, State.MIN_i43, State.MIN_i42, State.MIN_ii, State.MIN_ii6, State.MIN_ii64, State.MIN_ii7, State.MIN_ii65, State.MIN_ii43, State.MIN_ii42, State.MIN_III, State.MIN_III6, State.MIN_III64, State.MIN_iv, State.MIN_iv6, State.MIN_iv64, State.MIN_iv7, State.MIN_iv65, State.MIN_iv43, State.MIN_iv42, State.MIN_V, State.MIN_V6, State.MIN_V64, State.MIN_Vdom, State.MIN_V6dom, State.MIN_V64dom, State.MIN_V7, State.MIN_V65, State.MIN_V43, State.MIN_V42, State.MIN_VI, State.MIN_VI6, State.MIN_VI64, State.MIN_VI7, State.MIN_VI65, State.MIN_VI43, State.MIN_VI42, State.MIN_vii, State.MIN_vii6, State.MIN_vii64, State.MIN_vii7, State.MIN_vii65, State.MIN_vii43, State.MIN_vii42
	};
	
	public static final State[] ALL_STATES_NO_END = new State[] {
		State.MAJ_I, State.MAJ_I_TRIP_ROOT, State.MAJ_I7, State.MAJ_I65, State.MAJ_I43, State.MAJ_I42, State.MAJ_ii, State.MAJ_ii6, State.MAJ_ii64, State.MAJ_ii7, State.MAJ_ii65, State.MAJ_ii43, State.MAJ_ii42, State.MAJ_iii, State.MAJ_iii6, State.MAJ_iii64, State.MAJ_IV, State.MAJ_IV6, State.MAJ_IV64, State.MAJ_IV7, State.MAJ_IV65, State.MAJ_IV43, State.MAJ_IV42, State.MAJ_V, State.MAJ_V6, State.MAJ_V64, State.MAJ_Vdom, State.MAJ_V6dom, State.MAJ_V64dom, State.MAJ_V7, State.MAJ_V65, State.MAJ_V43, State.MAJ_V42, State.MAJ_vi, State.MAJ_vi6, State.MAJ_vi64, State.MAJ_vi7, State.MAJ_vi65, State.MAJ_vi43, State.MAJ_vi42, State.MAJ_vii, State.MAJ_vii6, State.MAJ_vii64, State.MAJ_vii7, State.MAJ_vii65, State.MAJ_vii43, State.MAJ_vii42,
		State.MIN_i, State.MIN_i_TRIP_ROOT, State.MIN_i7, State.MIN_i65, State.MIN_i43, State.MIN_i42, State.MIN_ii, State.MIN_ii6, State.MIN_ii64, State.MIN_ii7, State.MIN_ii65, State.MIN_ii43, State.MIN_ii42, State.MIN_III, State.MIN_III6, State.MIN_III64, State.MIN_iv, State.MIN_iv6, State.MIN_iv64, State.MIN_iv7, State.MIN_iv65, State.MIN_iv43, State.MIN_iv42, State.MIN_V, State.MIN_V6, State.MIN_V64, State.MIN_Vdom, State.MIN_V6dom, State.MIN_V64dom, State.MIN_V7, State.MIN_V65, State.MIN_V43, State.MIN_V42, State.MIN_VI, State.MIN_VI6, State.MIN_VI64, State.MIN_VI7, State.MIN_VI65, State.MIN_VI43, State.MIN_VI42, State.MIN_vii, State.MIN_vii6, State.MIN_vii64, State.MIN_vii7, State.MIN_vii65, State.MIN_vii43, State.MIN_vii42
	};
	
	public State(Chord chord, Interval rootInterval) {
		this(chord, rootInterval, false);
	}
	
	public State(Chord chord, Interval rootInterval, boolean atEnd) {
		this.chord = chord;
		this.rootInterval = rootInterval;
		this.atEnd = atEnd;
	}
	
	public Chord getChord() {
		return chord;
	}
	
	public Interval getRootInterval() {
		return this.rootInterval;
	}
	
	public Interval getBassInterval() {
		return rootInterval.add(chord.rootToBass()).normalize();
	}
	
	public boolean isEnd() {
		return this.atEnd;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(this == other) return true;
		if(!(other instanceof State)) return false;
		State s = (State) other;
		return s.rootInterval.equals(this.rootInterval) && s.chord.equals(this.chord) && this.atEnd == s.atEnd;
	}
	
	@Override
	public int hashCode() {
		return this.rootInterval.hashCode() * 31 * 31 + this.chord.hashCode() + (this.atEnd ? 31 * 31 * 31 : 0);
	}
	
	public String toString() {
		return this.rootInterval + ", " + this.chord; 
	}
}
