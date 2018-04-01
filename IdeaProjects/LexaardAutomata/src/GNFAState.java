import java.util.ArrayList;

public class GNFAState implements Comparable<GNFAState> {
    private static int stateCounter = 0;
    private String stateID;
    private boolean phState;

    private ArrayList<Regex> nextStateTransitions;

    /**
     * Creates a blank GNFAState
     */
    public GNFAState() {
        this(Integer.toString(stateCounter));
    }

    /**
     * Creates a GNFAState with the specified stateID
     * @param stateID The stateID of the GNFAState
     */
    public GNFAState(String stateID) {
        stateCounter += 1;
        this.stateID = stateID;
        this.phState = false;
    }

    /**
     * Creates a GNFAState with the specified stateID and whether this state is a placeholder, to be used in DFA to Regex
     * @param stateID The stateID of the state
     * @param placeHolderState Whether this state cannot be removed from the GNFA
     */
    public GNFAState(String stateID, boolean placeHolderState) {
        stateCounter += 1;
        this.stateID = stateID;
        this.phState = placeHolderState;
    }

    /**
     * Changes a transition at a certain index to the certain regex
     * @param r The regex of the transition
     * @param transitionIndex The index of the transition
     */
    public void setTransition(Regex r, int transitionIndex) {
        this.nextStateTransitions.set(transitionIndex, r);
    }

    /**
     * The stateID of the state
     * @return The stateID
     */
    public String getStateID() {
        return stateID;
    }

    /**
     * Sets the stateID of the string
     * @param stateID The new stateID of the state
     */
    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    /**
     * Gets the nextStateTransitions of the GNFAState
     * @return The transitions of the GNFAState, an ArrayList of the regexes
     */
    public ArrayList<Regex> getNextStateTransitions() {
        return nextStateTransitions;
    }

    /**
     * Sets the transitions of the GNFAState
     * @param nextStateTransitions The ArrayList of Regex transitions
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

    /**
     * Returns whether the state is a placeholder
     * @return Whether the state is a placeholder
     */
    public boolean isPHState() {
        return phState;
    }

    /**
     * Sets whether a state is a placeholder
     * @param phState true / false
     */
    public void setPhState(boolean phState) {
        this.phState = phState;
    }
}
