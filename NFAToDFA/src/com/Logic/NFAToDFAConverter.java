package com.Logic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import com.InputHandlerFromFile.InputStatesHandler;
import com.Models.FinalStatesGroup;
import com.Models.State;

public class NFAToDFAConverter {
	private InputStatesHandler inputStatesHandler = new InputStatesHandler();
	private int startStateID;
	private Set<String> inputSymbols;
	private State dfaStatesData[];
	private FinalStatesGroup finalStates;
	private LinkedList<String> statesQueue = new LinkedList<>();
	private Map<String, Integer> statesMap = new HashMap<>();

	public void convertNFAToDFA() throws IOException {

		getAllInputsFromNFAFile();
		initiateThreadToDrawNFA();

		// store the output dfa as it is stored in the file
		String dfaOutputAsInFile = "";
		dfaOutputAsInFile = dfaOutputAsInFile + "start " + startStateID + "\n";

		/*
		 * Stores the mapping of every state with its id, each state is stored as a
		 * string and mapped to its id.
		 */

		// id of start state is 0
		statesMap.put(startStateID + "", 0);
		int stateCounter = 1;

		statesQueue.add(startStateID + "");

		String finalState = "final";
		boolean isFinalState;

		while (statesQueueHasElements()) {
			// get states as an array of characters
			char states[] = getStatesFromStatesQueue();

			for (String inputSymbol : inputSymbols) {
				String tmp = "";
				isFinalState = false;

				for (char stateNumberChar : states) {
					if (currentStateHasTransitionForInputSymbol(stateNumberChar, inputSymbol))
						/*
						 * Concatenate all the states that can be traversed from current state at the
						 * current input symbol.
						 */

						for (State i : dfaStatesData[stateNumberChar - 48].getTransitions().get(inputSymbol)) {
							tmp += i.getStateID();
							if (finalStates.contains(i))
								isFinalState = true;
						}
				}

				/*
				 * Create a new State and map it to a unique and, then add it to queue
				 */
				char newStates[] = tmp.toCharArray();
				Arrays.sort(newStates);
				tmp = new String(newStates);

				if (statesMapDoesNotContainKey(tmp)) {
					statesQueue.add(tmp);
					statesMap.put(tmp, stateCounter++);

					if (isFinalState) {
						finalState = finalState + " " + statesMap.get(tmp);
					}
				}
				dfaOutputAsInFile = dfaOutputAsInFile + statesMap.get(String.valueOf(states)) + " " + inputSymbol + " "
						+ statesMap.get(tmp) + "\n";
			}
		}
		dfaOutputAsInFile = stateCounter + "\n" + dfaOutputAsInFile;
		dfaOutputAsInFile = dfaOutputAsInFile + finalState + "\n";

		System.out.println(dfaOutputAsInFile);

		storeDFAInFile(dfaOutputAsInFile);
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
}
