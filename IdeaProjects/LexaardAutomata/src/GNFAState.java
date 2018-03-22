import java.util.ArrayList;

public class GNFAState implements Comparable<GNFAState> {
    private static int stateCounter = 0;
    private String stateID;
    private boolean phState;

    private ArrayList<Regex> nextStateTransitions;

    public GNFAState() {
        this(Integer.toString(stateCounter));
    }

    public GNFAState(String stateID) {
        stateCounter += 1;
        this.stateID = stateID;
        this.phState = false;
    }

    /**
     * @param stateID
     */
    public GNFAState(String stateID, boolean placeHolderState) {
        stateCounter += 1;
        this.stateID = stateID;
        this.phState = placeHolderState;
    }

    public void setTransition(Regex r, int transitionIndex) {
        this.nextStateTransitions.set(transitionIndex, r);
    }

    public void appendTransition(Regex r, int transitionIndex) {
        this.nextStateTransitions.get(transitionIndex).unionReg(r);
    }

    public void concatTransition(Regex r, int transitionIndex) {
        this.nextStateTransitions.get(transitionIndex).concatReg(r);
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

    public boolean isPHState() {
        return phState;
    }

    public void setPhState(boolean phState) {
        this.phState = phState;
    }
}
