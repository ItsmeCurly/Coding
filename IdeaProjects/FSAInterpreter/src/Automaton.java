import java.util.ArrayList;
import java.util.List;

public class Automaton {
    private String comment;
    private List<Character> alphabet = new ArrayList<>();
    private ArrayList<ArrayList<String>> states = new ArrayList<>();
    private ArrayList<String> accept = new ArrayList<>();

    /**
     * Default Constructor
     */
    public Automaton() {
        this("", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Parameterized Constructor
     *
     * @param comment     Description of FSA
     * @param alphabet    Alphabet accepted by FSA
     * @param transitions - States and their transitions with alphabet
     * @param accept      - Accept states of FSA
     */
    public Automaton(String comment, List<Character> alphabet, ArrayList<ArrayList<String>> transitions, ArrayList<String> accept) {
        this.comment = comment;
        this.alphabet = alphabet;
        this.states = transitions;
        this.accept = accept;
    }

    /**
     * On command run from interpreter
     * @param s String of alphabet letters
     * @return Whether the input string is accepted or rejected
     */
    public String run(String s) {
        ArrayList<String> currentState = this.states.get(0);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int index = this.alphabet.indexOf(c);
            String sta = currentState.get(index + 1);
            for (ArrayList<String> state : this.states) {
                if (state.get(0).equals(sta)) {
                    currentState = state;
                }
            }
        }
        for (String acceptState : accept) {
            if (currentState.get(0).equals(acceptState)) {
                return "accept";
            }
        }
        return "reject";
    }

    /**
     *
     * @param c Add alphabet character c
     */
    public void addAlphabet(char c) {
        this.alphabet.add(c);
    }

    /**
     * @param comment Comment on the Automaton
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     *
     * @param transitions Sets the states and their transitions
     */
    public void setTransitions(ArrayList<ArrayList<String>> transitions) {
        this.states = transitions;
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
        String defaultSpace = "  ", formatSpace = " ", outputTable = "";
        ArrayList<String> strings = new ArrayList<>();

        for (ArrayList<String> stateTable : states) {
            String temp = " ";
            String state = stateTable.get(0);
            for (String acceptState : accept) {
                if (state.equals(acceptState))
                    temp = temp.replace(" ", "*");
                for (String stateTransition : stateTable)
                    temp += stateTransition + defaultSpace;
            }
            strings.add(temp);
        }

        String temp = defaultSpace + formatSpace;
        for (Character alpha : alphabet)
            temp += defaultSpace + formatSpace + alpha;
        outputTable += temp + '\n';

        for (String s : strings)
            outputTable += s + '\n';
        return outputTable;
    }
}