package com.Models;

import java.util.ArrayList;

public class Group extends ArrayList<State> {
	private int groupID;

	public Group(int id) {
		groupID = id;
	}

	Group(Group g, int id) {
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
