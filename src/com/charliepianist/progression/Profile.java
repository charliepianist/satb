package com.charliepianist.progression;

public interface Profile {
	// Moves Profile into next state and returns the new state. If profile has ended, throws an Exception.
	public State next();
	
	// Instantiates a new Profile from reference profile (now usable).
	public Profile instantiateCopy(boolean minor);
	
	// Returns current state.
	public State getCurrent();
	
	// Returns whether this profile has reached an end state or not
	public boolean hasEnded();
	
	// Returns whether this profile should attempt strict voicing when generating SATB or not. Only useful in conjunction with SATB generation.
	public boolean attemptStrict();
}
