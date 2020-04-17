package com.charliepianist.progression;

public class Distribution<T> {
	private T[] options;
	private int[] cumulativeWeights; // each int is the weights summed up from everything BEFORE it, not including it
	private int total;
	
	public Distribution(T[] options, int[] weights) {
		if(options == null || weights == null) throw new IllegalArgumentException("options and weights cannot be null");
		if(options.length != weights.length) throw new IllegalArgumentException("options and weights must have the same length");
		if(options.length < 1) throw new IllegalArgumentException("options and weights must have length at least 1");
		
		total = 0;
		this.options = options;
		this.cumulativeWeights = new int[weights.length];
		
		for(int i = 0; i < weights.length; i++) {
			cumulativeWeights[i] = total;
			total += weights[i];
		}
	}
	
	public T sample() {
		int rand = (int) (Math.random() * total);
		for(int i = 0; i < cumulativeWeights.length; i++) {
			if(rand < cumulativeWeights[i]) {
				return options[i - 1];
			}
		}
		return options[options.length - 1];
	}
}
