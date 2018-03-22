import java.util.ArrayList;

public class GNFAState implements Comparable<GNFAState> {
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

    /**
     * @param stateID
     */
    public GNFAState(String stateID) {
        stateCounter += 1;
        this.stateID = stateID;
    }

    /**
     * @param r
     */
    public void addTransition(Regex r) {
        this.nextStateTransitions.add(r);
    }

    public ArrayList<Regex> getAllTransitions(String s) {
        return nextStateTransitions;
    }

    /**
     * @return
     */
    public String getStateID() {
        return stateID;
    }

    /**
     * @param stateID
     */
    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    /**
     * @return
     */
    public boolean isAcceptState() {
        return acceptState;
    }

    /**
     * @param acceptGNFAState
     */
    public void setAcceptState(boolean acceptGNFAState) {
        this.acceptState = acceptGNFAState;
    }

    /**
     *
     */
    public void negateAcceptState() {
        this.acceptState = !this.acceptState;
    }

    /**
     * @return
     */
    public ArrayList<Regex> getNextStateTransitions() {
        return nextStateTransitions;
    }

    /**
     * @param nextStateTransitions
     */
    public void setNextStateTransitions(ArrayList<Regex> nextStateTransitions) {
        this.nextStateTransitions = nextStateTransitions;
    }

    @Override
    public int compareTo(GNFAState o) {
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
        String temp = String.format("%-8s", stateID);
        for (Regex nextStateTransition : nextStateTransitions) {
            temp += String.format("%" + Automaton.SPACE + "s", nextStateTransition);
        }
        return temp;
    }
}
