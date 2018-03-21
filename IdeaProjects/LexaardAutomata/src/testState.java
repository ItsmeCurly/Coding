import java.util.*;

public class testState<E> implements Comparable<testState<E>> {
    private static int stateCounter = 0;
    private String stateID;
    private boolean accepttestSTate;
    private Map<E, ArrayList<testState<E>>> nexttestSTate;
    private Set<testState<E>> stateSet;

    public testState() {
        this(Integer.toString(stateCounter));
    }

    public testState(boolean accepttestSTate) {
        this(Integer.toString(stateCounter));
        this.accepttestSTate = accepttestSTate;
    }

    public testState(String stateID) {
        stateCounter += 1;
        this.stateID = stateID;
        this.stateSet = new HashSet<>();
        this.setNexttestSTate(new HashMap<>());
    }

    /**
     * Creates a new testSTatewith stateID and set of states, to be used with DFA
     *
     * @param stateID
     * @param stateSet
     */
    public testState(String stateID, Set<testState<E>> stateSet) {
        this.stateID = stateID;
        this.stateSet = stateSet;
        this.setNexttestSTate(new HashMap<>());
        for (testState<E> st : stateSet) {
            if (st.isAccepttestSTate()) {
                st.setAccepttestSTate(true);
                break;
            }
        }
    }

    public testState(testState<E> other) {
        this.stateID = other.stateID;
        this.stateSet = new HashSet<>(other.gettestSTateSet());
        this.setNexttestSTate(new HashMap<>());
        for (E s : other.getNexttestSTate().keySet()) {
            for (testState<E> st : other.getAllTransitions(s)) {
                addTransition(new testState<E>(st), s);
            }
        }
        for (testState<E> st : stateSet) {
            if (st.isAccepttestSTate()) {
                st.setAccepttestSTate(true);
                break;
            }
        }
    }

    public void addTransition(testState<E> next, E s) {
        ArrayList<testState<E>> nextGroup = this.nexttestSTate.computeIfAbsent(s, k -> new ArrayList<>());
        if (!nextGroup.contains(next))
            nextGroup.add(next);
    }

    public ArrayList<testState<E>> getAllTransitions(E s) {
        ArrayList<testState<E>> nextGroup = this.nexttestSTate.get(s);
        if (nextGroup == null) return new ArrayList<>();
        return nextGroup;
    }

    public String gettestSTateID() {
        return stateID;
    }

    public void settestSTateID(String stateID) {
        this.stateID = stateID;
    }

    public boolean isAccepttestSTate() {
        return accepttestSTate;
    }

    public void setAccepttestSTate(boolean accepttestSTate) {
        this.accepttestSTate = accepttestSTate;
    }

    public void negateAccepttestSTate() {
        this.accepttestSTate = !this.accepttestSTate;
    }

    public Map<E, ArrayList<testState<E>>> getNexttestSTate() {
        return nexttestSTate;
    }

    public void setNexttestSTate(Map<E, ArrayList<testState<E>>> nexttestSTate) {
        this.nexttestSTate = nexttestSTate;
    }

    public Set<testState<E>> gettestSTateSet() {
        return stateSet;
    }

    public void settestSTateSet(Set<testState<E>> stateSet) {
        this.stateSet = stateSet;
    }

    @Override
    public int compareTo(testState<E> o) {

        int val = 0;
        for (char c : this.gettestSTateID().toCharArray()) {
            val += c;
        }

        int oVal = 0;
        for (char c : o.gettestSTateID().toCharArray()) {
            oVal += c;
        }

        return val - oVal;
    }

    @Override
    public String toString() {
        return stateID;
    }
}
