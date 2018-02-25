import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class State implements Comparable<State> {
    private String stateID;
    private boolean acceptState;
    private Map<String, ArrayList<State>> nextState;
    private Set<State> stateSet;

    public State(String stateID) {
        this.stateID = stateID;
        this.stateSet = stateSet;
        this.setNextState(new HashMap<>());
    }

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

    public Map<String, ArrayList<State>> getNextState() {
        return nextState;
    }

    public void setNextState(Map<String, ArrayList<State>> nextState) {
        this.nextState = nextState;
    }

    public Set<State> getStateSet() {
        return stateSet;
    }

    public void setStateSet(Set<State> stateSet) {
        this.stateSet = stateSet;
    }

    @Override
    public String toString() {
        return stateID;
    }

    @Override
    public int compareTo(State o) {
        int val = 0;
        for (char c : this.getStateID().toCharArray()) {
            val += Character.valueOf(c);
        }
        int oVal = 0;
        for (char c : o.getStateID().toCharArray()) {
            oVal += Character.valueOf(c);
        }
        return val - oVal;
    }
}
