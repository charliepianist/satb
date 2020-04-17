package com.charliepianist.progression;

import java.util.ArrayList;
import java.util.List;

import com.charliepianist.main.Chord;
import com.charliepianist.main.Interval;
import com.charliepianist.main.SATB;
import com.charliepianist.main.SATBGenerator;
import com.charliepianist.main.Tone;
import com.charliepianist.main.Voice;

public class ProgressionGenerator {
	
	public static final int MAX_STATES = 100; // Max number of states of a generated progression
	
	public static List<State> generateStates(Profile profile, boolean minor) {
		profile = new Profile(profile, minor);
		ArrayList<State> list = new ArrayList<State>();
		list.add(profile.getCurrent()); // Add initial tonic chord
		int states = 1;
		
		State next = profile.next();
		while(!next.isEnd() && states < MAX_STATES - 1) {
			list.add(next);
			states++;
			next = profile.next();
		}
		list.add(next);
		
		return list;
	}
	
	public static Voice[] generateSATB(Profile profile, Tone tonic, boolean minor) {
		return generateSATB(profile, tonic, minor, SATBGenerator.ENTROPY_DEFAULT);
	}
	
	public static Voice[] generateSATB(Profile profile, Tone tonic, boolean minor, int entropy) {
		List<State> states = generateStates(profile, minor);
		List<Interval> bassIntervals = new ArrayList<Interval>();
		List<Chord> chords = new ArrayList<Chord>();
		
		for(int i = 0; i < states.size(); i++) {
			chords.add(states.get(i).getChord());
			bassIntervals.add(states.get(i).getBassInterval());
		}
		
		SATBGenerator generator = new SATBGenerator();
		Voice[] satb = generator.generateSATB(tonic, bassIntervals, chords, entropy);
		
		return satb;
	}
	
	public static void main(String[] args) {
		Voice[] satb = generateSATB(Profile.RANDOM, Tone.C4, false, SATBGenerator.ENTROPY_DEFAULT);
		SATB.output(satb, false);
	}
	
}
