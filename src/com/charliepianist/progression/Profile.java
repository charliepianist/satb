package com.charliepianist.progression;

import java.util.HashMap;

public class Profile {
	
	private static final HashMap<State, Distribution<State>> testRelations;
	static {
		testRelations = new HashMap<State, Distribution<State>>();
		testRelations.put(State.MAJ_I, new Distribution<State>( new State[] { State.MAJ_V }, new int[] { 1 } ));
		testRelations.put(State.MAJ_V, new Distribution<State>( new State[] { State.MAJ_I }, new int[] { 1 } ));
	}
	
	private static final HashMap<State, Distribution<State>> standardRelations;
	static {
		standardRelations = new HashMap<State, Distribution<State>>();
		standardRelations.put(State.MAJ_I, new Distribution<State>(
			new State[] { State.MAJ_I6, State.MAJ_ii, State.MAJ_ii6, State.MAJ_iii, State.MAJ_IV, State.MAJ_IV6, State.MAJ_IV64, State.MAJ_V, State.MAJ_V6, State.MAJ_V64, State.MAJ_vi, State.MAJ_vi6, State.MAJ_vii, State.MAJ_vii6, State.MAJ_vii64,
					State.MAJ_I7, State.MAJ_I65, State.MAJ_I42, State.MAJ_ii7, State.MAJ_ii65, State.MAJ_ii42, State.MAJ_V7, State.MAJ_V65, State.MAJ_V43, State.MAJ_V42, State.MAJ_vi7, State.MAJ_vi65, State.MAJ_vii7, State.MAJ_vii65, State.MAJ_vii43},
			new int[]   { 6,            12,            3,             2,            16,           3,             10,             6,           10,           14,            4,            4,             5,             7,              1,             
					3,            2,             6,             10,            2,              1,              4,            8,             16,            5,             1,             2,              2,              1,               1}
		));
	}

	public static final Profile TEST = new Profile(testRelations);
	public static final Profile STANDARD = new Profile(standardRelations);
	
	private HashMap<State, Distribution<State>> relations;
	private State current;
	private int numStates;
	
	public Profile(HashMap<State, Distribution<State>> relations) {
		if(relations == null) throw new IllegalArgumentException("relations cannot be null");
		
		this.relations = relations;
		current = null;
		numStates = 0;
	}
	
	public Profile(Profile profile, boolean minor) {
		this.relations = profile.relations;
		numStates = 1;
		if(minor) {
			current = State.MIN_i;
		}else {
			current = State.MAJ_I;
		}
	}
	
	public State next() {
		if(current == null) throw new IllegalArgumentException("Cannot call next() from reference profile. Create a new Profile instead (new Profile(Profile profile, boolean minor))");
		Distribution<State> dist = relations.get(current);
		if(dist == null) throw new IllegalArgumentException("Invalid profile. Profiles must be self-contained; that is, any state that the profile can reach must have a distribution of next states associated to it.");
		
		State nextState = dist.sample();
		current = nextState;
		numStates++;
		return nextState;
	}
	
	public State getCurrent() {
		return current;
	}
	
	public int numStates() {
		return numStates;
	}
}
