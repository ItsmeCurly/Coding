import java.util.*;

public class State implements Comparable<State> {
    private static int stateCounter = 0;
    private String stateID;
    private boolean acceptState;
    private Map<String, ArrayList<State>> nextState;
    private Set<State> stateSet;

    /**
     * Creates a blank state
     */
    public State() {
        this(Integer.toString(stateCounter));
    }

    /**
     * Creates a state with the specified acceptState value
     * @param acceptState true or false
     */
    public State(boolean acceptState) {
        this("s" + Integer.toString(stateCounter));
        this.acceptState = acceptState;
    }

    /**
     * Creates a state with the specified stateID
     * @param stateID The stateID
     */
    public State(String stateID) {
        stateCounter += 1;
        this.stateID = stateID;
        this.stateSet = new HashSet<>();
        this.setNextState(new HashMap<>());
    }

    /**
     * Creates a new state with stateID and set of states, to be used with DFA
     *
     * @param stateID The stateID
     * @param stateSet The set of states that this new state represents
     */
    public State(String stateID, Set<State> stateSet) {
        this.stateID = stateID;
        this.stateSet = stateSet;
        this.setNextState(new HashMap<>());
        for (State st : stateSet) {
            if (st.isAcceptState()) {
                st.setAcceptState(true);
                break;
            }
        }
    }

    /**
     * Creates a state with the information of another state
     * @param other The other state
     */
    public State(State other) {
        this.stateID = other.stateID;
        this.stateSet = new HashSet<>(other.getStateSet());
        this.setNextState(new HashMap<>());
        for (String s : other.getNextState().keySet()) {
            for (State st : other.getAllTransitions(s)) {
                addTransition(new State(st), s);
            }
        }
        for (State st : stateSet) {
            if (st.isAcceptState()) {
                st.setAcceptState(true);
                break;
            }
        }
    }

    /**
     * Adds a transition to the state
     * @param next The state to transition to
     * @param s The transition key, or how the state transitions from this to the next
     */
    public void addTransition(State next, String s) {
        ArrayList<State> nextGroup = this.nextState.computeIfAbsent(s, k -> new ArrayList<>());
        if (!nextGroup.contains(next))
            nextGroup.add(next);
    }

    /**
     * Returns the state's transitions to other states
     * @param s The certain character to transition with
     * @return The transitions for the certain input string on this state
     */
    public ArrayList<State> getAllTransitions(String s) {
        ArrayList<State> nextGroup = this.nextState.get(s);
        if (nextGroup == null) return new ArrayList<>();
        return nextGroup;
    }

    /**
     * Returns the stateID of the state
     * @return The stateID
     */
    public String getStateID() {
        return stateID;
    }

    /**
     * Sets the stateID of the state
     * @param stateID The new stateID
     */
    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    /**
     * Returns whether this state is an acceptstate or not
     * @return True/False
     */
    public boolean isAcceptState() {
        return acceptState;
    }

    /**
     * Sets whether this state is an acceptState or not
     * @param acceptState Whether this state will be accepting or not
     */
    public void setAcceptState(boolean acceptState) {
        this.acceptState = acceptState;
    }

    /**
     * Negates the current value of the state
     */
    public void negateAcceptState() {
        this.acceptState = !this.acceptState;
    }

    /**
     * Returns the mapping of the transitions and their characters to transition to these
     * @return The mapping of the transitions
     */
    public Map<String, ArrayList<State>> getNextState() {
        return nextState;
    }

    /**
     * Sets the transitions of this state
     * @param nextState The new mapping of transitions
     */
    private void setNextState(Map<String, ArrayList<State>> nextState) {
        this.nextState = nextState;
    }

    /**
     * Returns the state set of this state
     * @return The states that this state represents
     */
    public Set<State> getStateSet() {
        return stateSet;
    }

    /**
     * Sets the new stateSet that this state represents
     * @param stateSet The new stateset
     */
    public void setStateSet(Set<State> stateSet) {
        this.stateSet = stateSet;
    }

    @Override
    public int compareTo(State o) {

        int val = 0;
        for (char c : this.getStateID().toCharArray()) {
            val += c;
        }

        int oVal = 0;
        for (char c : o.getStateID().toCharArray()) {
            oVal += c;
        }

        return val - oVal;
    }

    @Override
    public String toString() {
        return stateID;
    }
}
