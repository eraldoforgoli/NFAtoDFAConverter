package com.Logic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import com.InputHandlerFromFile.InputStatesHandler;
import com.Models.FinalStatesGroup;
import com.Models.State;

public class NFAToDFAConverter {
	private InputStatesHandler inputStatesHandler;
	private int startStateID;
	private Set<String> inputSymbols;
	private State dfaStatesData[];
	private FinalStatesGroup finalStates;
	private LinkedList<String> statesQueue;
	private Map<String, Integer> statesMap;
	private String dfaString;
	private String finalState;
	private int stateCounter;
	private boolean isFinalState;
	private Map<String, HashSet<State>> tempTransitions;

	public NFAToDFAConverter() {
		inputStatesHandler = new InputStatesHandler();
		startStateID = 0;
		statesQueue = new LinkedList<>();
		statesMap = new HashMap<>();
		dfaString = "";
		finalState = "final";
		stateCounter = 1;
		isFinalState = false;
		tempTransitions = new HashMap<>();
	}

	public void convertNFAToDFA() throws IOException {

		getAllInputsFromNFAFile();
		initiateThreadToDrawNFA();
		initialiseTheDFAString();
		initialiseStatesMap();
		initialiseStatesQueue();

		while (statesQueueHasElements()) {
			char states[] = getStatesFromStatesQueue();
			for (String inputSymbol : inputSymbols) {
				String tempState = "";
				isFinalState = false;
				for (char stateNumberChar : states) {
					if (currentStateHasTransitionForInputSymbol(stateNumberChar, inputSymbol)) {
						/*
						 * Concatenate all the states that can be traversed from current state at the
						 * current input symbol.
						 */
						tempTransitions = dfaStatesData[stateNumberChar - 48].getTransitions();
						for (State state : tempTransitions.get(inputSymbol)) {
							tempState += state.getStateID();
							if (stateIsFinal(state))
								isFinalState = true;
						}
					}
				}
				tempState = sortStates(tempState);
				if (statesMapDoesNotContainKey(tempState)) {
					addStateToStatesMap(tempState, stateCounter++);
					addStateToStatesQueue(tempState);
					saveFinalStateIfStateIsFinal(isFinalState, tempState);
				}
				addStateAndTransitionIntoToDFAString(tempState, states, inputSymbol);
			}
		}
		addFinalStateToDFAString(stateCounter, finalState);
		System.out.println(dfaString);
		storeDFAInFile(dfaString);
		drawDFAFromDFAFile();
	}

	private void getAllInputsFromNFAFile() {
		try {
			inputStatesHandler.getAutomataStatesFromFile("nfa");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startStateID = inputStatesHandler.getStartStateID();
		inputSymbols = inputStatesHandler.getInputSymbols();
		dfaStatesData = inputStatesHandler.getDfaStatesData();
		finalStates = inputStatesHandler.getFinalStates();
	}

	private void initiateThreadToDrawNFA() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// draw NFA from file "nfa"
				new com.View.MainFrameDrawer("nfa", "NFA");
			}
		}).start();
	}

	private boolean statesQueueHasElements() {
		if (statesQueue.isEmpty())
			return false;
		return true;
	}

	private char[] getStatesFromStatesQueue() {
		return statesQueue.poll().toCharArray();
	}

	private boolean currentStateHasTransitionForInputSymbol(char stateNumberChar, String inputSymbol) {
		return dfaStatesData[stateNumberChar - 48].getTransitions().get(inputSymbol) != null;
	}

	private boolean statesMapDoesNotContainKey(String key) {
		if (statesMap.containsKey(key))
			return false;
		return true;
	}

	private void storeDFAInFile(String dfaOutputAsInFile) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter("dfa"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.println(dfaOutputAsInFile);
		pw.close();
	}

	private void drawDFAFromDFAFile() {
		new com.View.MainFrameDrawer("dfa", "DFA");
	}

	private void initialiseTheDFAString() {
		dfaString = dfaString + "start " + startStateID + "\n";
	}

	private void initialiseStatesMap() {
		statesMap.put(startStateID + "", 0);
	}

	private void initialiseStatesQueue() {
		statesQueue.add(startStateID + "");
	}

	private void addStateToStatesQueue(String state) {
		statesQueue.add(state);
	}

	private void addStateToStatesMap(String state, int stateNumber) {
		statesMap.put(state, stateNumber);
	}

	private void addStateAndTransitionIntoToDFAString(String state, char[] states, String inputSymbol) {
		dfaString = dfaString + statesMap.get(String.valueOf(states)) + " " + inputSymbol + " " + statesMap.get(state)
				+ "\n";
	}

	private void addFinalStateToDFAString(int stateCounter, String finalState) {
		dfaString = stateCounter + "\n" + dfaString;
		dfaString = dfaString + finalState + "\n";
	}

	private void saveFinalStateIfStateIsFinal(boolean isFinalState, String state) {
		if (isFinalState) {
			finalState = finalState + " " + statesMap.get(state);
		}
	}

	private boolean stateIsFinal(State state) {
		return finalStates.contains(state);
	}

	private String sortStates(String tempState) {
		char newStates[] = tempState.toCharArray();
		Arrays.sort(newStates);
		return new String(newStates);
	}
}