package com.Logic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import com.InputHandlerFromFile.InputStatesHandler;
import com.Models.Group;
import com.Models.State;

public class NFAToDFAConverter {
	private InputStatesHandler inputStatesHandler = new InputStatesHandler();
	private int startStateID;
	private Set<String> inputSymbols;
	private State dfaStatesData[];
	private Group finalStates;

	public void convertNFAToDFA() throws IOException {

		/*
		 * Get all the inputs from 'nfa' file
		 */
		inputStatesHandler.getAutomataStatesFromFile("nfa");
		startStateID = inputStatesHandler.getStartStateID();
		inputSymbols = inputStatesHandler.getInputSymbols();
		dfaStatesData = inputStatesHandler.getDfaStatesData();
		finalStates = inputStatesHandler.getFinalStates();

		/*
		 * Thread ad
		 * ed to make program execution faster.
		 */
		new Thread(new Runnable() {
			@Override
			public void run() {
				// draw NFA from file "nfa"
				new com.View.MainFrameDrawer("nfa", "NFA");
			}
		}).start();

		// store the output dfa as it is stored in the file
		String dfaOutputAsInFile = "";
		dfaOutputAsInFile = dfaOutputAsInFile + "start " + startStateID + "\n";

		/*
		 * Stores the mapping of every state with its id, each state is stored as a
		 * string and mapped to its id.
		 */
		Map<String, Integer> statesMap = new HashMap<>();

		// id of start state is 0
		statesMap.put(startStateID + "", 0);
		int stateCounter = 1;

		LinkedList<String> statesQueue = new LinkedList<>();
		statesQueue.add(startStateID + "");

		String finalState = "final";
		boolean isFinalState;

		while (!statesQueue.isEmpty()) {
			// get states as an array of characters
			char states[] = statesQueue.poll().toCharArray();

			for (String inputSymbol : inputSymbols) {
				String tmp = "";
				isFinalState = false;

				// for each state as a character ch in the array states
				for (char stateNumberChar : states) {
					/*
					 * If the current state has a transition for the current input symbol
					 */
					if (dfaStatesData[stateNumberChar - 48].getTransitions().get(inputSymbol) != null)
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
				if (!statesMap.containsKey(tmp)) {
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

		// store DFA in a file "dfa"
		PrintWriter pw = new PrintWriter(new FileWriter("dfa"));
		pw.println(dfaOutputAsInFile);
		pw.close();

		// draw DFA from file "dfa"
		new com.View.MainFrameDrawer("dfa", "DFA");
	}
}
