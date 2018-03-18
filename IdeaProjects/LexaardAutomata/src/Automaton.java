import java.util.*;

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
        this.states = new LinkedList<>(other.getStates());
        this.comment = other.getComment();
        this.startState = other.getStartState();
    }

    public static Automaton constructFSA(String thisFSA) {
        Automaton fsa = new Automaton();
        Scanner scan = new Scanner(thisFSA);

        Scanner line = new Scanner(scan.nextLine());
        //set comment of FSA
        fsa.setComment(line.next());

        //get alphabet of FSA
        ArrayList<String> alphabet = new ArrayList<>();
        line = new Scanner(scan.nextLine());
        while (line.hasNext()) {
            alphabet.add(line.next());
        }
        fsa.setAlphabet(alphabet);

        //end get alphabet of FSA

        //read in states and transitions
        ArrayList<State> states = new ArrayList<>();
        ArrayList<String> transitions = new ArrayList<>();

        //first get states to define what states exist in the FSA
        while (scan.hasNextLine()) {
            String fsaLine = scan.nextLine();
            transitions.add(fsaLine);

            Scanner transition = new Scanner(fsaLine);
            String first = transition.next();
            State state;
            if (first.contains("*")) {
                first = first.substring(1, first.length());
                state = new State(first);
                state.setAcceptState(true);
            } else {
                state = new State(first);
            }
            states.add(state);
        }
        //with the states, construct the transitions that exist for each state
        for (String transition : transitions) {
            Scanner sc = new Scanner(transition);
            String firstState = sc.next();

            if (firstState.contains("*"))
                firstState = firstState.substring(1, firstState.length());

            State st = getStateFromString(states, firstState);

            int j = 1;

            while (sc.hasNext()) {
                String str = sc.next();
                //if transition is null(NFA to empty transition)
                if (str.equals("..")) {
                    j += 1;
                    continue;
                }

                Scanner transitionScan = new Scanner(str);
                transitionScan.useDelimiter(",");
                while (transitionScan.hasNext()) {
                    State st1 = getStateFromString(states, transitionScan.next());
                    st.addTransition(st1, alphabet.get(j - 1));
                }

                j += 1;
            }
        }
        fsa.setStartState(states.get(0));
        fsa.setStates(states);
        return fsa;
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

    public static Automaton constructFSA(String thisFSA, State startState) {
        Automaton fsa = constructFSA(thisFSA);
        fsa.setStartState(getStateFromString(fsa.getStates(), startState.getStateID()));

        return fsa;
    }

    public static Automaton nfa2Dfa(Automaton nfa1) {
        Automaton fsa;

        if (nfa1.isDeterministic()) {
            fsa = new Automaton(nfa1);
            return fsa;
        }
        fsa = new Automaton();

        ArrayList<String> alphabet = new ArrayList<>();

        for (String s : nfa1.getAlphabet()) {
            if (!s.equals(".."))
                alphabet.add(s);
        }
        fsa.setComment(nfa1.getComment());

        fsa.setAlphabet(alphabet);

        Automaton copy1 = constructFSA(nfa1.toString());

        List<State> oldStates = copy1.getStates();

        Set<State> stateSet = new TreeSet<>(oldStates);
        Set<Set<State>> powerSet = powerSet(stateSet);

        List<State> states = new ArrayList<>();

        for (Set<State> stateList : powerSet) {
            List<State> newList = asSortedList(stateList);
            State s = new State(stateNotation(newList), stateList);
            states.add(s);
        }

        fsa.setStates(states);

        for (State s : states) {

            List<State> transitionsList;
            for (String str : alphabet) {

                transitionsList = new ArrayList<>();
                for (State st : s.getStateSet()) {

                    List<State> transitions = st.getAllTransitions(str);
                    for (State sta : transitions) {

                        if (!transitionsList.contains(sta))
                            transitionsList.add(sta);
                        ArrayList<State> epsTransitions = sta.getAllTransitions("..");
                        for (State state : epsTransitions) {

                            if (!transitionsList.contains(state))
                                transitionsList.add(state);
                        }
                    }
                    transitionsList = asSortedList(transitionsList);
                }
                s.addTransition(getStateFromString(states, stateNotation(transitionsList)), str);
            }
        }
        ArrayList<State> temp = new ArrayList<>();

        temp.add(getStateFromString(oldStates, nfa1.getStartState().getStateID()));

        fsa.setStartState(getStateFromString(states, stateNotation(temp)));
        //to get states in lexicographical order
        fsa.sortStates();
        //insert state into varmapping
        return fsa;
    }

    /**
     * Applies a union to two DFAs
     *
     * @param dfa1 The first DFA to apply a union to
     * @param dfa2 The second DFA to apply a union to
     * @return The new DFA with a union applied
     */
    public static Automaton dfaUnion(Automaton dfa1, Automaton dfa2) {
        if (dfa1 == null) {
            return dfa2;
        }
        if (dfa2 == null) {
            return dfa1;
        }
        if (!(dfa1.isDeterministic() && dfa2.isDeterministic())) {
            System.err.println("DFA(s) not deterministic");
            return null;
        }

        if (!dfa1.getAlphabet().equals(dfa2.getAlphabet())) {
            System.err.println("DFA's alphabets don't match");
            return null;
        }
        Automaton copy1 = constructFSA(dfa1.toString());
        Automaton copy2 = constructFSA(dfa2.toString());

        Automaton newAutomata = new Automaton();

        newAutomata.setAlphabet(dfa1.getAlphabet());

        List<State> newStates = new ArrayList<>();

        List<State> dfa1States = new ArrayList<>(copy1.getStates());
        List<State> dfa2States = new ArrayList<>(copy2.getStates());

        List<Set<State>> newList = crossProduct(dfa1States, dfa2States);

        for (Set<State> stateList : newList) {
            State s = new State(stateNotation(stateList), stateList);
            newStates.add(s);
        }

        newAutomata.setStates(newStates);

        for (State s : newStates) {

            List<State> transitionsList;
            for (String str : newAutomata.getAlphabet()) {

                transitionsList = new ArrayList<>();
                for (State st : s.getStateSet()) {

                    List<State> transitions = st.getAllTransitions(str);
                    for (State sta : transitions) {

                        if (!transitionsList.contains(sta))
                            transitionsList.add(sta);
                    }

                    transitionsList = asSortedList(transitionsList);
                }

                s.addTransition(getStateFromString(newStates, stateNotation(transitionsList)), str);
            }

        }
        ArrayList<State> temp = new ArrayList<>();

        temp.add(getStateFromString(dfa1States, dfa1.getStartState().getStateID()));
        temp.add(getStateFromString(dfa2States, dfa2.getStartState().getStateID()));

        newAutomata.setStartState(getStateFromString(newStates, stateNotation(temp)));
        return newAutomata;
    }

    /**
     * Applies a union to two NFAs
     *
     * @param nfa1 The first NFA to apply a union to
     * @param nfa2 The second NFA to apply a union to
     * @return The new NFA with a union applied
     */
    public static Automaton nfaUnion(Automaton nfa1, Automaton nfa2) {
        if (nfa1 == null) {
            return nfa2;
        }
        if (nfa2 == null) {
            return nfa1;
        }
        if (nfa1.isDeterministic() || nfa2.isDeterministic()) {
            return null;
        }

        State newStart = new State("0");

        Automaton newAutomata = new Automaton();

        newStart.setAcceptState(false);

        List<State> nfa1States = new ArrayList<>(nfa1.getStates());
        List<State> nfa2States = new ArrayList<>(nfa2.getStates());

        newStart.addTransition(nfa1.getStartState(), "..");
        newStart.addTransition(nfa2.getStartState(), "..");

        List<State> newStates = new ArrayList<>();

        newStates.add(newStart);

        newStates.addAll(nfa1States);
        newStates.addAll(nfa2States);

        newAutomata.setStates(newStates);
        newAutomata.setStartState(newStart);

        return newAutomata;
    }

    /**
     * Concatenates two NFAs together
     *
     * @param nfa1 The first NFA to concatenate
     * @param nfa2 The second NFA to concatenate
     * @return A new NFA that is two NFAs concatenated
     */
    public static Automaton nfaConcat(Automaton nfa1, Automaton nfa2) {
        if (nfa1 == null) {
            return nfa2;
        }
        if (nfa2 == null) {
            return nfa1;
        }
        if (nfa1.isDeterministic() || nfa2.isDeterministic()) {
            return null;
        }
        Automaton copy1 = constructFSA(nfa1.toString(), nfa1.getStartState());

        Automaton copy2 = constructFSA(nfa2.toString(), nfa2.getStartState());

        Automaton newAutomata = new Automaton();

        List<State> nfa1States = new ArrayList<>(copy1.getStates());
        List<State> nfa2States = new ArrayList<>(copy2.getStates());

        for (State s : nfa1States) {
            if (s.isAcceptState()) {
                s.setAcceptState(false);
                s.addTransition(getStateFromString(nfa2States, nfa2.getStartState().getStateID()), "..");
            }
        }

        List<State> newStates = new ArrayList<>();

        newStates.addAll(nfa1States);
        newStates.addAll(nfa2States);

        newAutomata.setStates(newStates);

        newAutomata.setComment(nfa1.getComment() + nfa2.getComment());
        newAutomata.setAlphabet(nfa1.getAlphabet());

        newAutomata.setStartState(nfa1States.get(0));

        return newAutomata;
    }

    /**
     * Applies a Kleene star operation to a Finite State Automaton
     *
     * @param nfa1 The FSA to apply the Kleene star to
     * @return The FSA with the star operation applied to it
     */
    public static Automaton nfaStar(Automaton nfa1) {
        if (nfa1 == null) {
            return null;
        }
        if (nfa1.isDeterministic()) {
            return null;
        }
        Automaton copyAutomata = constructFSA(nfa1.toString(), nfa1.getStartState());

        Automaton newAutomata = new Automaton(copyAutomata);

        List<State> nfa1States = copyAutomata.getStates();

        State newStart = new State("0");

        newStart.setAcceptState(true);

        newStart.addTransition(copyAutomata.getStartState(), "..");

        for (State s : nfa1States) {
            if (s.isAcceptState()) {
                s.addTransition(nfa1States.get(0), "..");
            }
        }

        newAutomata.setStates(nfa1States);

        newAutomata.setStartState(newStart);

        return newAutomata;
    }

    /**
     * Prunes the FSA of any unreachable states
     *
     * @param fsa1 The FSA to be pruned
     * @return The pruned automaton, to be inserted into the variable map as a new variable name
     */
    public static Automaton fsaPrune(Automaton fsa1) {
        if (fsa1 == null) {
            return null;
        }

        Automaton newAutomata = constructFSA(fsa1.toString(), fsa1.getStartState());

        List<Integer> countingList = new ArrayList<>();

        for (int i = 0; i < fsa1.getStates().size(); i++) {
            countingList.add(0);
        }

        List<State> oldStates = newAutomata.getStates();

        for (int i = 0; i < countingList.size(); i++) {

            for (String str : newAutomata.getAlphabet()) {

                for (State s : oldStates.get(i).getAllTransitions(str)) {

                    int count = countingList.get(oldStates.indexOf(s));
                    count += 1;
                    countingList.set(oldStates.indexOf(s), count);
                }
            }
        }
        List<State> newStates = new ArrayList<>(oldStates);
        Iterator iter = countingList.iterator();
        while (iter.hasNext()) {
            int i;
            if ((i = (int) iter.next()) == 0 && !(newStates.get(countingList.indexOf(i)).equals(newAutomata.getStartState()))) {
                newStates.remove(countingList.indexOf(i));
                iter.remove();
            }
        }
        newAutomata.setStates(newStates);
        newAutomata.setStartState(getStateFromString(newStates, fsa1.getStartState().getStateID()));
        return newAutomata;
    }

    /**
     * Tests whether two Finite State Automata are equivalent
     *
     * @param fsa1 The first automaton
     * @param fsa2 The second automaton
     * @return Whether the two FSA are equivalent
     */
    public static boolean fsaEquivP(Automaton fsa1, Automaton fsa2) {
        Automaton newA, newB;
        Automaton negA, negB;

        newA = constructFSA(fsa1.toString());
        newB = constructFSA(fsa2.toString());

        negA = constructFSA(newA.toString());
        negA.negateAcceptStates();

        negB = constructFSA(newB.toString());
        negB.negateAcceptStates();

        Automaton concatA = nfaConcat(newA, negB);
        Automaton concatB = nfaConcat(negA, newB);
        Automaton union = nfaUnion(concatA, concatB);

        return union.getStates().size() == 0;
    }

    /**
     * Helper method to get a State variable within an array given a certain state ID
     *
     * @param states The states to search for the State
     * @param sta    The state ID of the state to find
     * @return The State variable within the array, matching the state ID. If not found, return null
     */
    public static State getStateFromString(List<State> states, String sta) {
        for (State st : states) {
            if (st.getStateID().equals(sta)) {
                return st;
            }
        }
        return null;
    }

    /**
     * Code received from https://stackoverflow.com/questions/1670862/obtaining-a-powerset-of-a-set-in-java, modified to
     * fit intent
     *
     * @param originalSet The set to get a powerSet of
     * @return The power set of the original set
     */
    private static Set<Set<State>> powerSet(Set<State> originalSet) {
        Set<Set<State>> sets = new HashSet<>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<>());
            return sets;
        }
        List<State> list = new ArrayList<>(originalSet);
        State head = list.get(0);
        Set<State> rest = new HashSet<>(list.subList(1, list.size()));
        for (Set<State> set : powerSet(rest)) {
            Set<State> newSet = new HashSet<>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
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

    /**
     * Code provided by https://stackoverflow.com/a/740351, utilized to sort lexicographically
     *
     * @param c
     * @return
     */
    public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);
        java.util.Collections.sort(list);
        return list;
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

    /**
     * Specified notation to get a state ID when converting between NFA to DFA(i.e. {q1,q2,q3}
     *
     * @param stateSet The set to create a state notation
     * @return A formatted state ID
     */
    private static String stateNotation(Collection<State> stateSet) {
        String result = "{";
        for (Object aStateSet : stateSet) {
            result += aStateSet + ",";
        }
        if (result.charAt(result.length() - 1) == ',')
            result = result.substring(0, result.length() - 1);
        result += "}";
        return result;
    }

    /**
     * Finds the cross product set of two lists
     *
     * @param l1 The first list to combine
     * @param l2 The second list to combine
     * @return A cross product of the two lists
     */
    public static List<Set<State>> crossProduct(List<State> l1, List<State> l2) {
        List<Set<State>> newList = new ArrayList<>();
        Set<State> temp;
        for (State st1 : l1) {
            for (State st2 : l2) {
                temp = new TreeSet<>();

                temp.add(st1);
                temp.add(st2);

                newList.add(temp);
            }
        }
        return newList;
    }

    /**
     * Runs an input string on the FSA
     *
     * @param input The input specified by the input alphabet
     * @return The string "accept" or "reject" depending on whether the input string is accepted or not
     */
    public String run(String input) {
        List<State> currentState = new ArrayList<>();
        List<State> nextStates = new ArrayList<>();

        currentState.add(getStartState());

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
                for (State st : s.getAllTransitions("..")) {
                    if (!nextStates.contains(st)) {
                        nextStates.add(st);
                    }
                }
            }

            currentState.clear();
            for (State st : nextStates) {
                if (!currentState.contains(st)) {
                    currentState.add(st);
                }
            }

            for (State s : nextStates) {
                for (State st : s.getAllTransitions("..")) {
                    if (!currentState.contains(st)) {
                        currentState.add(st);
                    }
                }
            }
            nextStates.clear();
        }
        for (State s : currentState)
            if (s.isAcceptState())
                return "accept";

        return "reject";
    }

    /**
     * Sorts the states of the FSA in lexicographical order
     */
    public void sortStates() {
        states = asSortedList(states);
    }

    public void negateAcceptStates() {
        for (State s : states) {
            s.negateAcceptState();
        }
    }
}
