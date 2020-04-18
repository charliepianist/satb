package com.charliepianist.progression;

import java.util.Arrays;
import java.util.HashMap;

public class MarkovProfile implements Profile {
	
	private static final HashMap<State, Distribution<State>> testRelations;
	static {
		testRelations = new HashMap<State, Distribution<State>>();
		testRelations.put(State.MAJ_I, new Distribution<State>( new State[] { State.MAJ_IV }, new int[] { 1 } ));
		testRelations.put(State.MAJ_I64, new Distribution<State>( new State[] { State.MAJ_Vdom, State.MAJ_V7 }, new int[] { 1, 1 }));
		testRelations.put(State.MAJ_IV, new Distribution<State>( new State[] { State.MAJ_I64 }, new int[] { 1 }));
		testRelations.put(State.MAJ_Vdom, new Distribution<State>( new State[] { State.MAJ_I_END }, new int[] { 1 } ));
		testRelations.put(State.MAJ_V7, new Distribution<State>( new State[] { State.MAJ_I_TRIP_ROOT_END }, new int[] { 1 } ));
	}
	
	private static final HashMap<State, Distribution<State>> randomRelations;
	static {
		randomRelations = new HashMap<State, Distribution<State>>();
		State[] states = State.ALL_STATES;
		int numStates = states.length;
		int[] weights = new int[numStates];
		Arrays.fill(weights, 1);
		for(int i = 0; i < numStates; i++) {
			randomRelations.put(states[i], new Distribution<State>(states, weights));
		}
	}
	
	private static final HashMap<State, Distribution<State>> randomNoEndRelations;
	static {
		randomNoEndRelations = new HashMap<State, Distribution<State>>();
		State[] states = State.ALL_STATES_NO_END;
		int numStates = states.length;
		int[] weights = new int[numStates];
		Arrays.fill(weights, 1);
		for(int i = 0; i < numStates; i++) {
			randomNoEndRelations.put(states[i], new Distribution<State>(states, weights));
		}
	}
	
	private static final HashMap<State, Distribution<State>> exampleRelations;
	static {
		exampleRelations = new HashMap<State, Distribution<State>>();
		// I CHORD
		exampleRelations.put(State.MAJ_I, new Distribution<State>(
				new State[] { State.MAJ_I6, State.MAJ_ii, State.MAJ_ii6, State.MAJ_iii, State.MAJ_IV, State.MAJ_IV6, State.MAJ_IV64, State.MAJ_Vdom, State.MAJ_V6, State.MAJ_V64, State.MAJ_vi, State.MAJ_vi6, State.MAJ_vii, State.MAJ_vii6, State.MAJ_vii64,
						State.MAJ_I7, State.MAJ_I65, State.MAJ_I42, State.MAJ_ii7, State.MAJ_ii65, State.MAJ_ii42, State.MAJ_V7, State.MAJ_V65, State.MAJ_V43, State.MAJ_V42, State.MAJ_vi7, State.MAJ_vi65, State.MAJ_vii7, State.MAJ_vii65, State.MAJ_vii43},
				new int[]   { 4,            12,            3,             2,            16,           3,             10,             12,             10,           20,            4,            4,             5,             7,              1,             
						3,            1,             24,            10,            2,              1,              4,            8,             16,            4,             1,             2,              2,              1,               1}
			));
		exampleRelations.put(State.MAJ_I6, new Distribution<State>(
				new State[] { State.MAJ_ii, State.MAJ_ii6, State.MAJ_iii, State.MAJ_IV, State.MAJ_Vdom, State.MAJ_V6, State.MAJ_V64, State.MAJ_vi, State.MAJ_vi6, State.MAJ_vi64, State.MAJ_vii, State.MAJ_vii6, State.MAJ_vii64,
						State.MAJ_I65, State.MAJ_ii7, State.MAJ_ii65, State.MAJ_IV7, State.MAJ_V7, State.MAJ_V65, State.MAJ_V43, State.MAJ_V42, State.MAJ_vi43, State.MAJ_vi42, State.MAJ_vii7, State.MAJ_vii65, State.MAJ_vii43},
				new int[]   { 1,            14,            3,             26,           3,              4,            2,             1,            2,             2,              2,             3,              1,
						5,             2,             12,             1,             2,            3,             2,             8,             1,              1,              2,              1,               2}
			));
		exampleRelations.put(State.MAJ_I64, new Distribution<State>(
				new State[] { State.MAJ_Vdom, State.MAJ_V7 },
				new int[]   { 2,              3}
			));
		// ii CHORD
		exampleRelations.put(State.MAJ_ii, new Distribution<State>(
				new State[] { State.MAJ_I, State.MAJ_I6, State.MAJ_I64, State.MAJ_ii6, State.MAJ_iii, State.MAJ_IV, State.MAJ_IV64, State.MAJ_Vdom, State.MAJ_V6, State.MAJ_V64, State.MAJ_vi, State.MAJ_vi6, State.MAJ_vii, State.MAJ_vii6, State.MAJ_vii64,
						State.MAJ_I7, State.MAJ_I65, State.MAJ_ii7, State.MAJ_ii65, State.MAJ_ii42, State.MAJ_IV7, State.MAJ_IV43, State.MAJ_V7, State.MAJ_V65, State.MAJ_V43, State.MAJ_V42, State.MAJ_vi7, State.MAJ_vi65, State.MAJ_vi43, State.MAJ_vii7, State.MAJ_vii65, State.MAJ_vii43},
				new int[]   { 2,           48,           1,             1,             2,             18,           3,              15,             9,            5,             6,             4,            1,             3,              1,
						1,            1,             2,             1,              4,              3,             1,              20,           12,            8,             4,             2,             2,              1,              1,              1,               1}
			));
		exampleRelations.put(State.MAJ_ii6, new Distribution<State>(
				new State[] { State.MAJ_I_END, State.MAJ_I6, State.MAJ_I64, State.MAJ_ii, State.MAJ_iii, State.MAJ_IV, State.MAJ_Vdom, State.MAJ_vi, State.MAJ_vii, State.MAJ_vii6, State.MAJ_vii64,
						State.MAJ_I7, State.MAJ_ii7, State.MAJ_ii65, State.MAJ_ii42, State.MAJ_IV7, State.MAJ_V7, State.MAJ_V65, State.MAJ_V43, State.MAJ_V42, State.MAJ_vi43, State.MAJ_vii7, State.MAJ_vii65, State.MAJ_vii43},
				new int[]   { 4,               8,            40,            2,            4,             8,            12,             12,           8,             2,              4,
						1,            1,             6,              1,              1,             10,           3,             4,             8,             2,              1,              2,               6}
			));
		exampleRelations.put(State.MAJ_ii64, new Distribution<State>(
				new State[] { State.MAJ_I64, State.MAJ_Vdom, State.MAJ_V6, State.MAJ_vi, State.MAJ_vii, State.MAJ_V7, State.MAJ_V65, State.MAJ_vi7, State.MAJ_vii7 },
				new int[]   { 4,             6,              12,           32,           6,             6,            8,             2,             1 }
			));
		// iii CHORD
		exampleRelations.put(State.MAJ_iii, new Distribution<State>(
				new State[] { State.MAJ_I, State.MAJ_I6, State.MAJ_ii, State.MAJ_ii6, State.MAJ_IV, State.MAJ_Vdom, State.MAJ_V6, State.MAJ_V64, State.MAJ_vi, State.MAJ_vi6, State.MAJ_vii, State.MAJ_vii6, State.MAJ_vii64,
						State.MAJ_I7, State.MAJ_I65, State.MAJ_ii7, State.MAJ_ii65, State.MAJ_IV7, State.MAJ_V7, State.MAJ_V65, State.MAJ_V43, State.MAJ_V42, State.MAJ_vi7, State.MAJ_vi65, State.MAJ_vii7, State.MAJ_vii65, State.MAJ_vii43},
				new int[]   { 8,           12,           12,           18,            30,           2,              3,            2,             4,            2,             2,             2,              1,
						12,           6,             1,             15,             12,            4,            5,             4,             7,             3,             3,              2,              1,                1}
			));
		exampleRelations.put(State.MAJ_iii6, new Distribution<State>(
				new State[] { State.MAJ_ii6, State.MAJ_IV, State.MAJ_IV6, State.MAJ_Vdom, State.MAJ_vi, State.MAJ_vii64, State.MAJ_I7, State.MAJ_I43, State.MAJ_ii65, State.MAJ_IV7, State.MAJ_V7, State.MAJ_vi7, State.MAJ_vii43 },
				new int[]   { 4,             6,            3,             14,             2,            1,               4,            2,             7,              6,             12,           3,             1}
			));
		exampleRelations.put(State.MAJ_iii64, new Distribution<State>(
				new State[] { State.MAJ_I, State.MAJ_IV64, State.MAJ_V6, State.MAJ_vi6, State.MAJ_vii, State.MAJ_I7, State.MAJ_IV43, State.MAJ_V65, State.MAJ_vii7 },
				new int[]   { 3,           1,              12,           1,             1,             2,            1,              6,             1}
			));
		// IV CHORD
		exampleRelations.put(State.MAJ_IV, new Distribution<State>(
				new State[] { State.MAJ_I_END, State.MAJ_I, State.MAJ_I6, State.MAJ_I64, State.MAJ_ii, State.MAJ_ii6, State.MAJ_iii6, State.MAJ_Vdom, State.MAJ_V6, State.MAJ_vii, State.MAJ_vii6,
						State.MAJ_I7, State.MAJ_I43, State.MAJ_ii7, State.MAJ_ii65, State.MAJ_IV7, State.MAJ_V7, State.MAJ_V65, State.MAJ_V43, State.MAJ_V42, State.MAJ_vi65, State.MAJ_vii7, State.MAJ_vii65, State.MAJ_vii43},
				new int[]   { 7,               7,           10,           36,            3,            3,             1,              14,             4,            2,             1,
						4,            1,             7,             4,              2,             16,           4,             2,             3,             1,              1,              1,               1}
			));
		exampleRelations.put(State.MAJ_IV6, new Distribution<State>(
				new State[] { State.MAJ_I64, State.MAJ_ii6, State.MAJ_ii64, State.MAJ_iii6, State.MAJ_IV, State.MAJ_Vdom, State.MAJ_V6, State.MAJ_vi, State.MAJ_vii,
						State.MAJ_I7, State.MAJ_I43, State.MAJ_I42, State.MAJ_ii65, State.MAJ_ii43, State.MAJ_ii42, State.MAJ_IV65, State.MAJ_V7, State.MAJ_V65, State.MAJ_vi7, State.MAJ_vi42, State.MAJ_vii7, State.MAJ_vii43},
				new int[]   { 40,            7,             5,              4,              2,            8,              12,           3,            5,
						2,            1,             4,             4,              2,              1,              6,              16,           12,            2,             3,              1,              2}
			));
		exampleRelations.put(State.MAJ_IV64, new Distribution<State>(
				new State[] { State.MAJ_I, State.MAJ_I7, State.MAJ_vi6},
				new int[]   { 10,          2,            1}
			));
		// V CHORD - These heavily influence the expected length of the sequence generated
		exampleRelations.put(State.MAJ_Vdom, new Distribution<State>(
				new State[] { State.MAJ_I, State.MAJ_I_END, State.MAJ_vi },
				new int[]   { 72,          20,              8 }
			));
		exampleRelations.put(State.MAJ_V6, new Distribution<State>(
				new State[] { State.MAJ_I, State.MAJ_IV6, State.MAJ_IV64, State.MAJ_Vdom, State.MAJ_vi, State.MAJ_vi6, State.MAJ_vii,
						State.MAJ_I7, State.MAJ_I42, State.MAJ_ii43, State.MAJ_ii42, State.MAJ_IV65, State.MAJ_V7, State.MAJ_V65, State.MAJ_vi7, State.MAJ_vi65, State.MAJ_vii7},
				new int[]   { 20,          28,            8,              5,              12,           6,             6,
						2,            1,             1,              1,              2,              4,            16,            8,             2,              4}
			));
		exampleRelations.put(State.MAJ_V64, new Distribution<State>(
				new State[] { State.MAJ_I, State.MAJ_I6, State.MAJ_IV64, State.MAJ_vi6, State.MAJ_I7, State.MAJ_I65, State.MAJ_IV43, State.MAJ_vi65, State.MAJ_vi43 },
				new int[]   { 6,           72,           6,              4,             2,            14,            4,              3,              2 }
			));
		// vi CHORD
		// UNIMPLEMENTED BELOW
		exampleRelations.put(State.MAJ_vi, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_vi6, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_vi64, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		// vii CHORD
		exampleRelations.put(State.MAJ_vii, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_vii6, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_vii64, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		// I7 CHORD
		exampleRelations.put(State.MAJ_I7, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_I65, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_I43, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_I42, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		// ii7 CHORD
		exampleRelations.put(State.MAJ_ii7, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_ii65, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_ii43, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_ii42, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		// IV7 CHORD
		exampleRelations.put(State.MAJ_IV7, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_IV65, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_IV43, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_IV42, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		// V7 CHORD
		exampleRelations.put(State.MAJ_V7, new Distribution<State>(
				new State[] { State.MAJ_I_TRIP_ROOT },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_I_TRIP_ROOT, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_V65, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_V43, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_V42, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		// vi7 CHORD
		exampleRelations.put(State.MAJ_vi7, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_vi65, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_vi43, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_vi42, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		// vii7 CHORD
		exampleRelations.put(State.MAJ_vii7, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_vii65, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_vii43, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
		exampleRelations.put(State.MAJ_vii42, new Distribution<State>(
				new State[] { State.MAJ_I },
				new int[]   { 1}
			));
	}

	public static final MarkovProfile TEST = new MarkovProfile(testRelations);
	public static final MarkovProfile RANDOM = new MarkovProfile(randomRelations, false);
	public static final MarkovProfile RANDOM_NO_LIMIT = new MarkovProfile(randomNoEndRelations, false);
	public static final MarkovProfile EXAMPLE = new MarkovProfile(exampleRelations);
	
	private HashMap<State, Distribution<State>> relations;
	private State current;
	private int numStates;
	private boolean attemptStrict;
	
	public MarkovProfile(HashMap<State, Distribution<State>> relations) {
		this(relations, true);
	}
	
	public MarkovProfile(HashMap<State, Distribution<State>> relations, boolean attemptStrict) {
		if(relations == null) throw new IllegalArgumentException("relations cannot be null");
		
		this.relations = relations;
		this.attemptStrict = attemptStrict;
		current = null;
		numStates = 0;
	}
	
	public MarkovProfile instantiateCopy(boolean minor) {
		MarkovProfile newProfile = new MarkovProfile(this.relations, this.attemptStrict);
		newProfile.numStates = 1;
		if(minor) {
			newProfile.current = State.MIN_i;
		}else {
			newProfile.current = State.MAJ_I;
		}
		return newProfile;
	}
	
	public boolean attemptStrict() {
		return attemptStrict;
	}
	
	public State next() {
		if(current == null) throw new IllegalArgumentException("Cannot call next() from reference profile. Create a new Profile instead (new Profile(Profile profile, boolean minor))");
		Distribution<State> dist = relations.get(current);
		if(dist == null) throw new IllegalArgumentException("Invalid profile. Profiles must be self-contained, but " + current + " does not have a distribution of next states associated to it.");
		
		State nextState = dist.sample();
		current = nextState;
		numStates++;
		return nextState;
	}
	
	public boolean hasEnded() {
		return current.isEnd();
	}
	
	public State getCurrent() {
		return current;
	}
	
	public int numStates() {
		return numStates;
	}
}
