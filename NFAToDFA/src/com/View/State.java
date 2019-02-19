package com.View;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class State {
	int stateID;
	Map<String,HashSet<State>> transitions;
	
	State(int id){
		stateID=id;
		transitions=new HashMap<>();
	}

	void addTransition(String key,State state){
		HashSet<State> set;
		if(transitions.containsKey(key)){
			set = transitions.get(key);

		}
		else{
			set=new HashSet<>();
		}
		set.add(state);
		transitions.put(key,set);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return stateID+"";
	}
}
