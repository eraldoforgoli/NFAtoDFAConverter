package com.Models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class State {
	private int stateID;
	private Map<String, HashSet<State>> transitions;

	public State(int stateID) {
		this.stateID = stateID;
		transitions = new HashMap<>();
	}

	public void putState(String key, State value) {
		HashSet<State> set;
		if (transitions.containsKey(key)) {
			set = transitions.get(key);
		} else {
			set = new HashSet<>();
		}
		set.add(value);
		transitions.put(key, set);
	}

	@Override
	public String toString() {
		return "state " + stateID;
	}

	public int getStateID() {
		return this.stateID;
	}

	public Map<String, HashSet<State>> getTransitions() {
		return this.transitions;
	}
}
