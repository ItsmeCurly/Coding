import java.util.*;

public class Interpreter {
    private final String VALUEPATTERN = "\"";   //value to indicate number instead of variable

    /**
     * Interpreter to run program
     */
    private Interpreter() {
        this.run();
    }

    /**
     * Main function
     *
     * @param args input to program
     */
    public static void main(String[] args) {
        new Interpreter();
    }

    /**
     * @param s The string to validate
     * @return Whether the string is a valid Java variable identifier
     */
    private boolean isValidIdentifier(String s) {
        if (!Character.isJavaIdentifierStart(s.charAt(0)))
            return false;
        for (int i = 1; i < s.length(); i++) {
            if (!Character.isJavaIdentifierPart(s.charAt(i)))
                return false;
        }
        return true;
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
     * Runner of Interpreter
     */
    private void run() {
        Scanner scan = new Scanner(System.in);
        Map<String, Object> varMap = new LinkedHashMap<>();
        String in;
        label:
        while (!(in = scan.nextLine()).isEmpty()) {
            Scanner scanIn = new Scanner(in);
            switch (scanIn.next()) {
                case "quit":
                    break label;
                case "print": {
                    System.out.println(varMap.get(scanIn.next()));
                    break;
                }
                case "define":
                    String name = scanIn.next();
                    if (!isValidIdentifier(name)) {
                        System.err.println("Invalid identifier for variable");
                        break;
                    }
                    String command = scanIn.next().trim();
                    //create a new FSA
                    if (command.equals("fsa")) {
                        Automaton fsa = new Automaton();
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
                        String fsaLine;
                        ArrayList<State> states = new ArrayList<>();
                        ArrayList<String> transitions = new ArrayList<>();
                        //first get states to define what states exist in the FSA
                        while (!(fsaLine = scan.nextLine()).isEmpty()) {
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

                        if (!varMap.containsKey(name))
                            varMap.put(name, fsa);
                        else
                            varMap.replace(name, fsa);
                    } else if (command.contains(this.VALUEPATTERN)) {
                        if (!varMap.containsKey(name))
                            varMap.put(name, command.replaceAll(this.VALUEPATTERN, ""));
                        else
                            varMap.replace(name, command.replaceAll(this.VALUEPATTERN, ""));
                    } else if (command.equals("true") || command.equals("false")) {
                        if (!varMap.containsKey(name))
                            varMap.put(name, command);
                        else
                            varMap.replace(name, command);
                    }
                    //if user command is nfa2Dfa, read in one FSA and return the DFA equivalent regardless if DFA or NFA
                    else if (command.equals("nfa2dfa")) {
                        Automaton fsa = nfa2Dfa((Automaton) varMap.get(scanIn.next()));

                        if (!varMap.containsKey(name))
                            varMap.put(name, fsa);
                        else
                            varMap.replace(name, fsa);
                    }
                    //if user command is dfaUnion, read in the two DFAs and put it in the variable mapping
                    else if (command.equals("dfaUnion")) {
                        Automaton fsa = dfaUnion((Automaton) varMap.get(scanIn.next()), (Automaton) varMap.get(scanIn.next()));
                        if (!varMap.containsKey(name))
                            varMap.put(name, fsa);
                        else
                            varMap.replace(name, fsa);
                    }
                    //if user command is nfaConcat, read in the two NFAs and put it in the variable mapping
                    else if (command.equals("nfaConcat")) {
                        Automaton fsa = nfaConcat((Automaton) varMap.get(scanIn.next()), (Automaton) varMap.get(scanIn.next()));
                        if (!varMap.containsKey(name))
                            varMap.put(name, fsa);
                        else
                            varMap.replace(name, fsa);
                    }
                    //if user command is nfaStar, read in the NFA and insert into variable mapping
                    else if (command.equals("nfaStar")) {
                        Automaton fsa = nfaStar((Automaton) varMap.get(scanIn.next()));
                        if (!varMap.containsKey(name))
                            varMap.put(name, fsa);
                        else
                            varMap.replace(name, fsa);
                    }
                    //if user command is nfaUnion, read in the two NFAs and insert into the variable mapping
                    else if (command.equals("nfaUnion")) {
                        Automaton fsa = nfaUnion((Automaton) varMap.get(scanIn.next()), (Automaton) varMap.get(scanIn.next()));
                        if (!varMap.containsKey(name))
                            varMap.put(name, fsa);
                        else
                            varMap.replace(name, fsa);
                    }
                    //if user command is prune, read in the FSA to prune and insert the pruned FSA into the variable mapping
                    else if (command.equals("prune")) {
                        Automaton fsa = fsaPrune((Automaton) varMap.get(scanIn.next()));
                        if (!varMap.containsKey(name))
                            varMap.put(name, fsa);
                        else
                            varMap.replace(name, fsa);
                    }
                    //if user command is FSAequivP, read in the two FSAs and return a boolean denoting whether they are equivalent
                    else if (command.equals("FSAequivp")) {
                        boolean newBool = fsaEquivP((Automaton) varMap.get(scanIn.next()), (Automaton) varMap.get(scanIn.next()));
                        if (!varMap.containsKey(name))
                            varMap.put(name, newBool);
                        else
                            varMap.replace(name, newBool);
                    }
                    break;
                case "run": {
                    Automaton fsa;
                    String varIdentifier = scanIn.next();

                    if (!isValidIdentifier(varIdentifier)) {
                        System.err.println("Automaton identifier not valid");
                        break;
                    }

                    if (varMap.containsKey(varIdentifier)) {
                        if (varMap.get(varIdentifier) instanceof Automaton)
                            fsa = (Automaton) varMap.get(varIdentifier);
                        else
                            break;
                    }
                    else {
                        System.err.println("Automaton " + varIdentifier + " not present");
                        break;
                    }
                    String value = scanIn.next();
                    if (value.contains("\"")) {
                        System.out.println(fsa.run(value.replaceAll(this.VALUEPATTERN, "")));
                        break;
                    }
                    if (!isValidIdentifier(value)) {
                        System.err.println("Variable identifier not valid");
                        break;
                    }
                    if (varMap.containsKey(value)) {
                        System.out.println(fsa.run((String) varMap.get(value)));
                    } else
                        System.err.println("Variable Identifier " + value + " not present");
                    break;
                }
                default:
                    System.err.println("Invalid input");
                    break;
            }
        }
    }

    private Automaton nfa2Dfa(Automaton nfa1) {
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

        List<State> oldStates = nfa1.getStates();

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

        temp.add(nfa1.getStates().get(0));

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
    private Automaton dfaUnion(Automaton dfa1, Automaton dfa2) {
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

        Automaton newAutomata = new Automaton();

        newAutomata.setAlphabet(dfa1.getAlphabet());

        List<State> newStates = new ArrayList<>();

        List<State> dfa1States = new ArrayList<>(dfa1.getStates());
        List<State> dfa2States = new ArrayList<>(dfa2.getStates());

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

                System.out.println(transitionsList.toString());
                s.addTransition(getStateFromString(newStates, stateNotation(transitionsList)), str);
            }

        }
        ArrayList<State> temp = new ArrayList<>();

        temp.add(dfa1States.get(0));
        temp.add(dfa2States.get(0));

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
    private Automaton nfaUnion(Automaton nfa1, Automaton nfa2) {
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

        newStart.addTransition(nfa1States.get(0), "..");
        newStart.addTransition(nfa2States.get(0), "..");

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
    private Automaton nfaConcat(Automaton nfa1, Automaton nfa2) {
        if (nfa1 == null) {
            return nfa2;
        }
        if (nfa2 == null) {
            return nfa1;
        }
        if (nfa1.isDeterministic() || nfa2.isDeterministic()) {
            return null;
        }
        Automaton newAutomata = new Automaton();

        List<State> nfa1States = new ArrayList<>(nfa1.getStates());
        List<State> nfa2States = new ArrayList<>(nfa2.getStates());

        for (State s : nfa1States) {
            if (s.isAcceptState()) {
                s.setAcceptState(false);
                s.addTransition(nfa2States.get(0), "..");
            }
        }

        List<State> newStates = new ArrayList<>();

        newStates.addAll(nfa1States);
        newStates.addAll(nfa2States);

        newAutomata.setStates(newStates);
        newAutomata.setStartState(nfa1States.get(0));

        return newAutomata;
    }

    /**
     * Applies a Kleene star operation to a Finite State Automaton
     *
     * @param nfa1 The FSA to apply the Kleene star to
     * @return The FSA with the star operation applied to it
     */
    private Automaton nfaStar(Automaton nfa1) {
        if (nfa1 == null) {
            return null;
        }
        if (nfa1.isDeterministic()) {
            return null;
        }

        Automaton newAutomata = new Automaton();

        List<State> nfa1States = new ArrayList<>(nfa1.getStates());
        State newStart = new State("0");

        newStart.setAcceptState(true);

        newStart.addTransition(nfa1States.get(0), "..");

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
    private Automaton fsaPrune(Automaton fsa1) {
        //TODO CAN'T REMEMBER IF THIS WORKS OR NOT
        Automaton newAutomata = new Automaton();

        if (fsa1 == null) {
            return null;
        }

        List<Integer> countingList = new ArrayList<>();

        for (int i = 0; i < fsa1.getStates().size(); i++) {
            countingList.add(0);
        }
        List<State> oldStates = fsa1.getStates();
        for (int i = 0; i < countingList.size(); i++) {

            for (String str : fsa1.getAlphabet()) {

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
            if ((i = (int) iter.next()) == 0 && !(newStates.get(countingList.indexOf(i)).equals(fsa1.getStartState()))) {
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
    private boolean fsaEquivP(Automaton fsa1, Automaton fsa2) {
        Automaton dfa1, dfa2;
        if (!fsa1.isDeterministic()) {
            dfa1 = nfa2Dfa(fsa1);
        } else {
            dfa1 = fsa1;
        }
        if (!fsa2.isDeterministic()) {
            dfa2 = nfa2Dfa(fsa2);
        } else {
            dfa2 = fsa2;
        }

        return dfa1.equals(dfa2);
    }

    /**
     * Helper method to get a State variable within an array given a certain state ID
     *
     * @param states The states to search for the State
     * @param sta    The state ID of the state to find
     * @return The State variable within the array, matching the state ID. If not found, return null
     */
    private State getStateFromString(List<State> states, String sta) {
        for (State st : states) {
            if (st.getStateID().equals(sta)) {
                return st;
            }
        }
        return null;
    }

    /**
     * Finds the cross product set of two lists
     *
     * @param l1 The first list to combine
     * @param l2 The second list to combine
     * @return A cross product of the two lists
     */
    private List<Set<State>> crossProduct(List<State> l1, List<State> l2) {
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
}