import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Automaton {
    private String comment;
    private ArrayList<String> alphabet = new ArrayList<>();
    private ArrayList<String> states = new ArrayList<>();
    private ArrayList<ArrayList<String>> transitions = new ArrayList<>();
    private ArrayList<String> accept = new ArrayList<>();
    private boolean nfa;
    private int counter;

    /**
     * Default Constructor
     */
    public Automaton() {
        this("", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Parameterized Constructor
     *
     * @param comment     Description of FSA
     * @param alphabet    Alphabet accepted by FSA
     * @param transitions - States and their transitions with alphabet
     * @param accept      - Accept states of FSA
     */
    public Automaton(String comment, ArrayList<String> alphabet, ArrayList<String> states, ArrayList<ArrayList<String>> transitions, ArrayList<String> accept) {
        this.comment = comment;
        this.alphabet = alphabet;
        this.states = states;
        this.transitions = transitions;
        this.accept = accept;
        this.counter = 0;
    }

    public Automaton(Automaton other) {
        this.comment = other.getComment();
        this.alphabet = other.getAlphabet();
        this.states = other.getStates();
        this.transitions = transitions;
        this.accept = accept;
        this.nfa = other.getNFA();
        this.counter = 0;
    }

    /**
     * On command run from interpreter
     * @param s String of alphabet letters
     * @return Whether the input string is accepted or rejected
     */

    public void runStart(String s) {
        counter = 0;
        run(s, this.transitions.get(0));
    }

    /**
     * On command run from interpreter
     * @param s String of alphabet letters
     * @return Whether the input string is accepted or rejected
     */

    public void run(String s, ArrayList<String> startState) {
        if(s.length() == 0) {
            for (String acceptState : accept)
                if (startState.get(0).equals(acceptState))
                    counter++;
            return;
        }
        char c = s.charAt(0);           //read input
        int index = this.alphabet.indexOf(Character.toString(c));   //get index of char from alphabet
        String sta = startState.get(index + 1);   //get string for transitions
        Scanner scan = new Scanner(sta);
        scan.useDelimiter(",");
        if(!startState.get(startState.size()-1).equals("..")&&alphabet.contains("..")) {
            ArrayList<String> list = getTransitionList(startState.get(startState.size()-1));
            run(s, list);
        }
        while(scan.hasNext()) {
            String st1 = scan.next(); //nondefinitively transition
            ArrayList<String> list = getTransitionList(st1);
            if(list != null)
                run(s.substring(1, s.length()), list);
            else
                return;
        }
    }

    public ArrayList<String> getTransitionList(String state) {
        for (ArrayList<String> stateList : this.transitions) {
            if (stateList.get(0).equals(state)) {
                return stateList;
            }
        }
        return null;
    }

    /**
     *
     * @param s Add alphabet character c
     */
    public void addAlphabet(String s) {
        this.alphabet.add(s);
    }

    /**
     *
     * @param s Add accept state s
     */
    public void addAccept(String s) {
        this.accept.add(s);
    }

    @Override
    public String toString() {
        return this.comment + '\n' + formattedTable();
    }

    private String formattedTable() {
        String outputTable = "";
        ArrayList<String> strings = new ArrayList<>();

        for (ArrayList<String> stateTable : transitions) {
            String temp = "";
            String state = stateTable.get(0);
            for (String acceptState : accept) {
                if (state.equals(acceptState))
                    temp += "*";
                else
                    temp += " ";
            }
            for (String stateTransition : stateTable)
                temp += String.format("%-8s", stateTransition);
            strings.add(temp);
        }

        String temp = String.format("%-9s", "");
        for (String alpha : alphabet)
            temp += String.format("%-8s", alpha);
        outputTable += temp + '\n';

        for (String s : strings)
            outputTable += s + '\n';
        return outputTable;
    }

    /**
     *
     * @param transitions Sets the states and their transitions
     */
    public void setTransitions(ArrayList<ArrayList<String>> transitions) {
        this.transitions = transitions;
    }

    public ArrayList<ArrayList<String>> getTransitions() {
        return transitions;
    }

    /**
     * @param comment Comment on the Automaton
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public ArrayList<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(ArrayList<String> alphabet) {
        this.alphabet = alphabet;
    }

    public ArrayList<String> getAccept() {
        return accept;
    }

    public void setAccept(ArrayList<String> accept) {
        this.accept = accept;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public ArrayList<String> getStates() {
        return states;
    }

    public boolean getNFA() {
        return nfa;
    }

    public void setNFA(boolean isNFA) {
        nfa = isNFA;
    }
}