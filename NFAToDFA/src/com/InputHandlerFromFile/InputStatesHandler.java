package com.InputHandlerFromFile;

import java.io.*;
import java.util.*;

import com.Models.Group;
import com.Models.State;

public class InputStatesHandler {
	private int startStateID;
	private Set<String> inputSymbols;
	private State dfaStatesData[];
	private Group finalStates;

	public int getStartStateID() {
		return this.startStateID;
	}

	public Set<String> getInputSymbols() {
		return this.inputSymbols;
	}

	public State[] getDfaStatesData() {
		return this.dfaStatesData;
	}

	public Group getFinalStates() {
		return this.finalStates;
	}

	public void getAutomataStatesFromFile(String file) throws IOException {
		BufferedReader nfaFileReader = null;
		try {
			nfaFileReader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int numberOfStates = Integer.parseInt(nfaFileReader.readLine());
		dfaStatesData = new State[numberOfStates];
		for (int i = 0; i < numberOfStates; i++) {
			dfaStatesData[i] = new State(i);
		}

		String lineInNFAFile;
		inputSymbols = new HashSet<>();

		lineInNFAFile = nfaFileReader.readLine();
		startStateID = Integer.parseInt(lineInNFAFile.substring(6));

		lineInNFAFile = nfaFileReader.readLine();
		while (!lineInNFAFile.startsWith("final")) {
			StringTokenizer st = new StringTokenizer(lineInNFAFile);

			int from = Integer.parseInt(st.nextToken());
			String at = st.nextToken();
			int to = Integer.parseInt(st.nextToken());
			dfaStatesData[from].putState(at, dfaStatesData[to]);
			inputSymbols.add(at);
			lineInNFAFile = nfaFileReader.readLine();
		}

		finalStates = new Group(0);

		StringTokenizer st = new StringTokenizer(lineInNFAFile);
		st.nextToken();
		while (st.hasMoreTokens()) {
			finalStates.add(dfaStatesData[Integer.parseInt(st.nextToken())]);
		}
	}
}
