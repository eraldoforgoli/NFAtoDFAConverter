package com.InputHandlerFromFile;

import com.Models.FinalStatesGroup;
import com.Models.State;

public class InputStatesHandler {
    private int startStateID;
    private Set<String> inputSymbols;
    private State dfaStatesData[];
    private FinalStatesGroup finalStates;
    private int numberOfStates = 0;

    public void getAutomataStatesFromFile(String file) throws IOException {
        BufferedReader nfaFileReader = getBufferedReader(file);
        numberOfStates = getNumberOfAutomataStates(nfaFileReader);
        nameAutomataStatesFrom0ToN();

        String lineInNFAFile;
        inputSymbols = new HashSet<>();

        lineInNFAFile = nfaFileReader.readLine();
        startStateID = getStartStateID(lineInNFAFile);

        lineInNFAFile = nfaFileReader.readLine();
        while (statesAreNotFinal(lineInNFAFile)) {
            StringTokenizer st = new StringTokenizer(lineInNFAFile);

            int from = Integer.parseInt(st.nextToken());
            String at = st.nextToken();
            int to = Integer.parseInt(st.nextToken());

            dfaStatesData[from].putState(at, dfaStatesData[to]);
            inputSymbols.add(at);
            lineInNFAFile = nfaFileReader.readLine();
        }

        finalStates = new FinalStatesGroup(0);
        StringTokenizer st = new StringTokenizer(lineInNFAFile);
        st.nextToken();
        while (st.hasMoreTokens()) {
            finalStates.add(dfaStatesData[Integer.parseInt(st.nextToken())]);
        }
    }

    public int getStartStateID() {
        return this.startStateID;
    }

    public Set<String> getInputSymbols() {
        return this.inputSymbols;
    }

    public State[] getDfaStatesData() {
        return this.dfaStatesData;
    }

    public FinalStatesGroup getFinalStates() {
        return this.finalStates;
    }

    private BufferedReader getBufferedReader(String file) {
        BufferedReader nfaFileReader = null;
        try {
            nfaFileReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            return nfaFileReader;
        }
    }

    private int getNumberOfAutomataStates(BufferedReader nfaFileReader) {
        try {
            return Integer.parseInt(nfaFileReader.readLine());
        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    private void nameAutomataStatesFrom0ToN() {
        dfaStatesData = new State[numberOfStates];
        for (int i = 0; i < numberOfStates; i++) {
            dfaStatesData[i] = new State(i);
        }
    }

    private int getStartStateID(String lineInNFAFile) {
        return Integer.parseInt(lineInNFAFile.substring(6));
    }

    private boolean statesAreNotFinal(String lineInNFAFile) {
        if (lineInNFAFile.startsWith("final"))
            return false;
        return true;
    }
}
