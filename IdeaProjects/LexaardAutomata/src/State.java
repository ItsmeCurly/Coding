import java.util.*;

public class State implements Comparable<State> {
    private static int stateCounter = 0;
    private String stateID;
    private boolean acceptState;
    private Map<String, ArrayList<State>> nextState;
    private Set<State> stateSet;

    /**
     *
     */
    public State() {
        this(Integer.toString(stateCounter));
    }

    /**
     * @param acceptState
     */
    public State(boolean acceptState) {
        this("s" + Integer.toString(stateCounter));
        this.acceptState = acceptState;
    }

    /**
     * @param stateID
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
     * @param stateID
     * @param stateSet
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

    public void addTransition(State next, String s) {
        ArrayList<State> nextGroup = this.nextState.computeIfAbsent(s, k -> new ArrayList<>());
        if (!nextGroup.contains(next))
            nextGroup.add(next);
    }

    public ArrayList<State> getAllTransitions(String s) {
        ArrayList<State> nextGroup = this.nextState.get(s);
        if (nextGroup == null) return new ArrayList<>();
        return nextGroup;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public boolean isAcceptState() {
        return acceptState;
    }

    public void setAcceptState(boolean acceptState) {
        this.acceptState = acceptState;
    }

    public void negateAcceptState() {
        this.acceptState = !this.acceptState;
    }

    public Map<String, ArrayList<State>> getNextState() {
        return nextState;
    }

    private void setNextState(Map<String, ArrayList<State>> nextState) {
        this.nextState = nextState;
    }

    public Set<State> getStateSet() {
        return stateSet;
    }

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
