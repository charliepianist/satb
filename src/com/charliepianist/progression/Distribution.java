package com.charliepianist.progression;

public class Distribution {
	private State[] states;
	private int[] cumulativeWeights; // each int is the weights summed up from everything BEFORE it, not including it
	private int total;
	
	public Distribution() {
		this(new State[0], new int[0]);
	}
	
	public Distribution(State[] states, int[] weights) {
		if(states == null || weights == null) throw new IllegalArgumentException("states and weights cannot be null");
		if(states.length != weights.length) throw new IllegalArgumentException("states and weights must have the same length");
		
		total = 0;
		this.states = states;
		this.cumulativeWeights = new int[weights.length];
		
		for(int i = 0; i < weights.length; i++) {
			cumulativeWeights[i] = total;
			total += weights[i];
		}
	}
	
	public State sample() {
		int rand = (int) (Math.random() * total);
		for(int i = 0; i < cumulativeWeights.length; i++) {
			if(rand < cumulativeWeights[i]) {
				return states[i];
			}
		}
		return states[states.length - 1];
	}
}
