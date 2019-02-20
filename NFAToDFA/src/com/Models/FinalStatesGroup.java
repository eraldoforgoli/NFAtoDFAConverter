package com.Models;

import java.util.ArrayList;

public class FinalStatesGroup extends ArrayList<State> {
	private int groupID;

	public FinalStatesGroup(int id) {
		groupID = id;
	}

	FinalStatesGroup(FinalStatesGroup g, int id) {
		groupID = id;
		for (State s : g) {
			add(s);
		}
	}

	@Override
	public boolean add(State state) {
		if (this.contains(state))
			return false;
		return super.add(state);
	}

	public int getGroupId() {
		return groupID;
	}

	public void setId(int id) {
		this.groupID = id;
	}

	@Override
	public String toString() {
		return "Group " + groupID + "\t" + super.toString();
	}
}
