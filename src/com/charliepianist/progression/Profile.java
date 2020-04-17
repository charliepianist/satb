package com.charliepianist.progression;

import java.util.HashMap;

public class Profile {
	private HashMap<State, Distribution<State>> relations;
	
	public Profile(HashMap<State, Distribution<State>> relations) {
		this.relations = relations;
	}
}
