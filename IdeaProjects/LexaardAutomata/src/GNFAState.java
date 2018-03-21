import java.util.ArrayList;
import java.util.Set;

public class GNFAState implements Comparable<GNFAState> {
    //TODO FIX ERRORS AND DECIDE ON HOW TO MAKE STATES INTERACT
    private static int stateCounter = 0;
    private String stateID;
    private boolean acceptState;
    private ArrayList<Regex> nextStateTransitions;

    public GNFAState() {
        this(Integer.toString(stateCounter));
    }

    public GNFAState(boolean acceptState) {
        this(Integer.toString(stateCounter));
        this.acceptState = acceptState;
        nextStateTransitions = new ArrayList<>();
    }

    public GNFAState(String stateID) {
        stateCounter += 1;
        this.stateID = stateID;
    }

    /**
     * Creates a new state with stateID and set of states, to be used with DFA
     *
     * @param stateID
     * @param stateSet
     */
//    public GNFAState(String stateID, Set<GNFAState> stateSet) {
//        this.stateID = stateID;
//        this.stateSet = stateSet;
//        this.setNextState(new HashMap<>());
//        for (GNFAState st : stateSet) {
//            if (st.isAcceptState()) {
//                st.setAcceptState(true);
//                break;
//            }
//        }
//    }

//    public GNFAState(GNFAState other) {
//        this.stateID = other.stateID;
//        this.stateSet = new HashSet<>(other.getStateSet());
//        this.setNextState(new HashMap<>());
//        for (Regex s : other.getNextState().keySet()) {
//            for (GNFAState st : other.getAllTransitions(s)) {
//                addTransition(new GNFAState(st), s);
//            }
//        }
//
//        for (GNFAState st : stateSet) {
//            if (st.isAcceptState()) {
//                st.setAcceptState(true);
//                break;
//            }
//        }
//    }
    public void addTransition(int stateIndex, Regex r) {
        ArrayList<GNFAState> nextGroup = this.nextState.computeIfAbsent(r, k -> new ArrayList<>());
        if (!nextGroup.contains(next))
            nextGroup.add(next);
    }

    public ArrayList<GNFAState> getAllTransitions(Regex r) {
        ArrayList<GNFAState> nextGroup = this.nextState.get(r);
        if (nextGroup == null) return new ArrayList<>();
        return nextGroup;
    }

    public ArrayList<GNFAState> getAllTransitions(String s) {
        ArrayList<GNFAState> nextGroup = new ArrayList<>();
        for (Regex r : nextState.keySet()) {
            if (r.match(s)) {
                nextGroup.add(nextState.get(r));
            }
        }
        return nextGroup;
    }

    public String getGNFAStateID() {
        return stateID;
    }

    public void setGNFAStateID(String stateID) {
        this.stateID = stateID;
    }

    public boolean isAcceptState() {
        return acceptState;
    }

    public void setAcceptState(boolean acceptGNFAState) {
        this.acceptState = acceptGNFAState;
    }

    public void acceptState() {
        this.acceptState = !this.acceptState;
    }

    public Set<GNFAState> getStateSet() {
        return stateSet;
    }

    public void setStateSet(Set<GNFAState> stateSet) {
        this.stateSet = stateSet;
    }

    public Regex[] getNextStateTransitions() {
        return nextStateTransitions;
    }

    public void setNextStateTransitions(ArrayList<Regex> nextStateTransitions) {
        this.nextStateTransitions = nextStateTransitions;
    }

    @Override
    public int compareTo(GNFAState o) {

        int val = 0;
        for (char c : this.getGNFAStateID().toCharArray()) {
            val += c;
        }

        int oVal = 0;
        for (char c : o.getGNFAStateID().toCharArray()) {
            oVal += c;
        }

        return val - oVal;
    }

    @Override
    public String toString() {
        return stateID;
    }
}
