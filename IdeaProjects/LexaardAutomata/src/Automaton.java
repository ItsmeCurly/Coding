import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Automaton {
    private final int SPACE = -20;
    private ArrayList<String> alphabet;
    private List<State> states;
    private String comment;
    private State startState;

    /**
     * Creates a blank automaton
     */
    public Automaton() {
        this.states = new LinkedList<>();
        this.comment = "";
        this.alphabet = new ArrayList<>();
    }

    /**
     * Create a new Automaton with a specified list of states
     *
     * @param states
     */
    public Automaton(List<State> states) {
        this.states = states;
        this.comment = "";
        this.alphabet = new ArrayList<>();
    }

    /**
     * Copies an automaton's data onto another
     * @param other The other automaton to copy
     */
    public Automaton(Automaton other) {
        this.alphabet = other.getAlphabet();
        this.states = other.getStates();
        this.comment = other.getComment();
    }

    /**
     * Runs an input string on the FSA
     * @param input The input specified by the input alphabet
     * @return The string "accept" or "reject" depending on whether the input string is accepted or not
     */
    public String run(String input) {
        List<State> currentState = new ArrayList<>();
        List<State> nextStates = new ArrayList<>();

        currentState.add(startState);

        for (char c : input.toCharArray()) {
            for (State s : currentState) {
                ArrayList<State> current = s.getNextState().get(Character.toString(c));
                if (current != null) {
                    for (State st : s.getNextState().get(Character.toString(c))) {
                        if (st != null) {
                            nextStates.add(st);
                        }
                    }
                }
                if (!s.getAllTransitions("..").isEmpty()) {
                    nextStates.addAll(s.getAllTransitions(".."));
                }
            }
            currentState.clear();
            currentState.addAll(nextStates);
            nextStates.clear();
        }
        for (State s : currentState)
            if (s.isAcceptState())
                return "accept";

        return "reject";
    }

    /**
     * Returns the states of the FSA
     * @return The states of the FSA as a List
     */
    public List<State> getStates() {
        return states;
    }

    /**
     * Sets the states of the FSA
     * @param states The new states List
     */
    public void setStates(List<State> states) {
        this.states = states;
    }

    /**
     * Get the comment of the FSA
     * @return The FSA's comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set the comment of the FSA
     * @param comment The comment to set to the FSA
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets the start state of the FSA
     *
     * @return The start state
     */
    public State getStartState() {
        return startState;
    }

    /**
     * Sets the start state of the FSA
     *
     * @param startState The new start state
     */
    public void setStartState(State startState) {
        this.startState = startState;
    }

    /**
     * Gets the alphabet of the FSA
     *
     * @return The alphabet
     */
    public ArrayList<String> getAlphabet() {
        return alphabet;
    }

    /**
     * Sets the alphabet of the FSA
     *
     * @param alphabet The new alphabet
     */
    public void setAlphabet(ArrayList<String> alphabet) {
        this.alphabet = alphabet;
    }

    /**
     * Checks whether the FSA is deterministic or not
     *
     * @return Whether the FSA is deterministic
     */
    protected boolean isDeterministic() {
        if (alphabet.contains("..")) {
            return false;
        }
        for (String str : alphabet) {
            for (State s : states) {
                ArrayList<State> current = s.getNextState().get(str);
                if (current != null && current.size() > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sorts the states of the FSA in lexicographical order
     */
    public void sortStates() {
        states = Interpreter.asSortedList(states);
    }

    @Override
    public String toString() {
        String result = comment + '\n';
        result += String.format("%" + SPACE + "s", "");
        for (String anAlphabet : alphabet) {
            result += String.format("%" + SPACE + "s", anAlphabet);
        }
        result += "\n";
        for (State state : states) {
            Map<String, ArrayList<State>> nextState = state.getNextState();
            result += String.format("%" + SPACE + "s", (state.isAcceptState() ? "*" : "") + state.getStateID());
            for (String anAlphabet : alphabet) {
                String str = "";
                ArrayList<State> stateTransitions = nextState.get(anAlphabet);
                if (stateTransitions == null) {
                    result += String.format("%" + SPACE + "s", "..");
                    continue;
                }
                for (State st : stateTransitions) {
                    str += st.getStateID() + ",";
                }
                str = str.substring(0, str.length() - 1);
                result += String.format("%" + SPACE + "s", str);
            }
            result += "\n";
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Automaton)) {
            return false;
        }

        Automaton fsa2 = (Automaton) o;

        return this.states.equals(fsa2.getStates()) && this.alphabet.equals(fsa2.getAlphabet());
    }
}
