import java.util.*;

public class GNFA {
    private String comment;
    private List<GNFAState> states;
    private List<String> alphabet;

    /**
     * Creates a blank GNFA
     */
    public GNFA() {
        this("");
    }

    /**
     * Creates a GNFA with the specified comment
     * @param comment The comment of the GNFA
     */
    public GNFA(String comment) {
        this.comment = comment;
        this.states = new ArrayList<>();
        this.alphabet = new ArrayList<>();
    }

    /**
     * Creates a GNFA with the specified GNFAStates
     * @param states The states of the GNFA
     */
    public GNFA(List<GNFAState> states) {
        this.states = states;
        this.alphabet = alphabet;
        this.comment = "";
    }

    /**
     * Creates a GNFA with the specified params
     * @param states The states of the GNFA
     * @param alphabet The alphabet of the GNFA
     * @param comment The comment of the GNFA
     */
    public GNFA(List<GNFAState> states, List<String> alphabet, String comment) {
        this.states = states;
        this.comment = comment;
        this.alphabet = alphabet;
    }

    /**
     * Creates a GNFA from another GNFA's data
     * @param other The other GNFA
     */
    public GNFA(GNFA other) {
        constructGNFA(other.toString());
    }

    /**
     * Constructs a GNFA with the string representation of a GNFA
     * @param input The string representation of the GNFA
     * @return A GNFA that is a clone of the string representation
     */
    public static GNFA constructGNFA(String input) {
        GNFA gnfa = new GNFA();
        Scanner scan = new Scanner(input);

        Scanner line = new Scanner(scan.nextLine());
        //set comment of GNFA
        gnfa.setComment(line.next());

        //get states
        List<String> alphabet = new ArrayList<>();

        Scanner line2 = new Scanner(scan.nextLine());

        while (line2.hasNext()) {
            alphabet.add(line2.next());
        }

        List<GNFAState> states = new ArrayList<>();

        line = new Scanner(scan.nextLine());

        while (line.hasNext()) {
            GNFAState st = new GNFAState(line.next());
            states.add(st);
        }
        for (GNFAState s : states) {
            ArrayList<Regex> temp = new ArrayList<>();
            for (int i = 0; i < states.size(); i++) {
                temp.add(new Regex());
            }
            s.setNextStateTransitions(temp);
        }

        int i = 0;
        //get regex transitions for the GNFA
        while (scan.hasNextLine()) {
            String temp = scan.nextLine();
            String[] regexes = temp.split(" {2,}");

            for (int k = 1; k < regexes.length; k++) {
                Regex r1;
                if (regexes[k].equals("..")) {
                    r1 = new Regex();
                } else {
                    r1 = new Regex(regexes[k]);
                }
                states.get(i).setTransition(r1, k - 1);
            }
            i += 1;
        }
        //construct GNFA with certain alphabet and states
        gnfa.setAlphabet(alphabet);
        gnfa.setStates(states);

        return gnfa;
    }

    /**
     * Converts a DFA to a GNFA, to be used with DFA to regex
     * @param dfa The DFA to convert into a GNFA
     * @return The GNFA representation of the inputted DFA
     */
    public static GNFA dfa2gnfa(Automaton dfa) {
        String[] temp = (dfa.toString()).split("\n");

        GNFA gnfa = new GNFA();
        List<String> alphabet = new ArrayList<>();

        Collections.addAll(alphabet, temp[1].split(" +"));  //splits the transitions and stores them
        alphabet.remove("");    //remove the first state, blank

        gnfa.setComment(dfa.getComment());
        gnfa.setAlphabet(alphabet);

        List<GNFAState> states = new ArrayList<>();

        GNFAState newStart = new GNFAState("s", true);
        GNFAState newEnd = new GNFAState("e", true);

        states.add(newStart);
        //create states from DFA
        for (int i = 2; i < temp.length; i++) {
            Scanner sc = new Scanner(temp[i]);
            String sID = sc.next();
            if (sID.contains("*")) sID = sID.substring(1, sID.length());
            GNFAState gs1 = new GNFAState(sID);
            states.add(gs1);
        }

        states.add(newEnd);
        //set transitions to blank
        for (GNFAState s : states) {
            ArrayList<Regex> temp1 = new ArrayList<>();
            for (int i = 0; i < states.size(); i++) {
                temp1.add(new Regex());
            }
            s.setNextStateTransitions(temp1);
        }
        //get transitions from arraylist and set them to current transitions
        for (int i = 2; i < temp.length; i++) {
            String[] transitionIDs = temp[i].split(" +");
            transitionIDs = Arrays.copyOfRange(transitionIDs, 1, transitionIDs.length);
            for (int j = 0; j < transitionIDs.length; j++) {
                Regex r1 = new Regex(alphabet.get(j));
                int k;
                for (k = 0; k < states.size(); k++) {
                    GNFAState gs1 = getStateFromString(states, transitionIDs[j]);
                    if (gs1.compareTo(states.get(k)) == 0)
                        break;
                }
                states.get(i - 1).setTransition(r1, k);
            }
        }
        //set first state transition to epsilon, default first state
        states.get(0).setTransition(new Regex(Regex.EPSILONCHARACTER + ""), 1);
        //set all acceptstates to transition to new end state
        for (int i = 0; i < dfa.getStates().size(); i++) {
            if (dfa.getStates().get(i).isAcceptState()) {
                GNFAState gs1 = getStateFromString(states, dfa.getStates().get(i).getStateID());
                gs1.setTransition(new Regex(Regex.EPSILONCHARACTER + ""), states.size() - 1);
            }
        }
        gnfa.setStates(states);
        return gnfa;
    }

    /**
     * Helper method to get a length of parentheses where a regex can't help, as it doesn't know the difference between embedded
     * parentheses
     * @param input The input that contains a parentheses at the starting position, which will find the index of the end of the embedded parentheses
     * @return The length of the parentheses
     */
    public static int getEmbeddedParenthesesLength(String input) {
        int openParenCounter = 0;
        int closeParenCounter = 0;
        int position = 0;
        do {
            if (input.charAt(position) == '(') {
                openParenCounter += 1;
            } else if (input.charAt(position) == ')') {
                closeParenCounter += 1;
            }
            position += 1;
        } while (openParenCounter != closeParenCounter && position != input.length());
        return position;
    }

    /**
     * Returns a GNFA.GNFAState from the string representation of it
     *
     * @param states The states to search for the string representation
     * @param sta    The string representation of the state
     * @return The state that matches the string representation, or null
     */
    private static GNFAState getStateFromString(List<GNFAState> states, String sta) {
        for (GNFAState st : states) {
            if (st.getStateID().equals(sta)) {
                return st;
            }
        }
        return null;
    }

    @Deprecated
    public String run(String input) {
//        List<Automaton.State> currentState = new ArrayList<>();
//        List<Automaton.State> nextStates = new ArrayList<>();
//
//        currentState.add(getStartState());
//
//        for (char c : input.toCharArray()) {
//
//        }
//        for (Automaton.State s : currentState)
//            if (s.isAcceptState())
//                return "accept";

        return "reject";
    }

    /**
     * Returns the alphabet of the GNFA
     * @return The alphabet
     */
    public List<String> getAlphabet() {
        return alphabet;
    }

    /**
     * Sets the alphabet of the GNFA
     * @param alphabet The new alphabet
     */
    public void setAlphabet(List<String> alphabet) {
        this.alphabet = alphabet;
    }

    /**
     * Returns the states of the GNFA
     * @return The states
     */
    public List<GNFAState> getStates() {
        return states;
    }

    /**
     * Sets the states of a GNFA
     * @param states The new states
     */
    public void setStates(List<GNFAState> states) {
        this.states = states;
    }

    /**
     * Returns the comment of the GNFA
     * @return The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment of the GNFA
     * @param comment The new comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Checks if the GNFA has any more valid states to remove(non placeholder)
     * @return Any more non placeholder states existing in states
     */
    public boolean hasValidStates() {
        for (GNFAState gs : states) {
            if (!gs.isPHState())
                return true;
        }
        return false;
    }

    public String toString() {
        String result = comment + '\n';
        for (String al : alphabet) {
            result += String.format("%-5s", al);
        }
        result += String.format("\n%-8s", "");
        for (GNFAState gs : states) {
            result += String.format("%" + Automaton.SPACE + "s", gs.getStateID());
        }
        result += '\n';
        for (GNFAState gs : states) {
            result += gs.toString() + '\n';
        }
        return result;
    }

    public static class GNFAState implements Comparable<GNFAState> {
        private static int stateCounter = 0;
        private String stateID;
        private boolean phState;

        private ArrayList<Regex> nextStateTransitions;

        /**
         * Creates a blank GNFA.GNFAState
         */
        public GNFAState() {
            this(Integer.toString(stateCounter));
        }

        /**
         * Creates a GNFA.GNFAState with the specified stateID
         *
         * @param stateID The stateID of the GNFA.GNFAState
         */
        public GNFAState(String stateID) {
            stateCounter += 1;
            this.stateID = stateID;
            this.phState = false;
        }

        /**
         * Creates a GNFA.GNFAState with the specified stateID and whether this state is a placeholder, to be used in DFA to Regex
         *
         * @param stateID          The stateID of the state
         * @param placeHolderState Whether this state cannot be removed from the GNFA
         */
        public GNFAState(String stateID, boolean placeHolderState) {
            stateCounter += 1;
            this.stateID = stateID;
            this.phState = placeHolderState;
        }

        /**
         * Changes a transition at a certain index to the certain regex
         *
         * @param r               The regex of the transition
         * @param transitionIndex The index of the transition
         */
        public void setTransition(Regex r, int transitionIndex) {
            this.nextStateTransitions.set(transitionIndex, r);
        }

        /**
         * The stateID of the state
         *
         * @return The stateID
         */
        public String getStateID() {
            return stateID;
        }

        /**
         * Sets the stateID of the string
         *
         * @param stateID The new stateID of the state
         */
        public void setStateID(String stateID) {
            this.stateID = stateID;
        }

        /**
         * Gets the nextStateTransitions of the GNFA.GNFAState
         *
         * @return The transitions of the GNFA.GNFAState, an ArrayList of the regexes
         */
        public ArrayList<Regex> getNextStateTransitions() {
            return nextStateTransitions;
        }

        /**
         * Sets the transitions of the GNFA.GNFAState
         *
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
         *
         * @return Whether the state is a placeholder
         */
        public boolean isPHState() {
            return phState;
        }

        /**
         * Sets whether a state is a placeholder
         *
         * @param phState true / false
         */
        public void setPhState(boolean phState) {
            this.phState = phState;
        }
    }
}
