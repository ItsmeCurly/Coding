import java.util.*;

public class Interpreter {
    private final String VALUEPATTERN = "\"";

    private Interpreter() {
        this.run();
    }

    public static void main(String[] args) {
        new Interpreter();
    }

    /**
     * Code received from https://stackoverflow.com/questions/1670862/obtaining-a-powerset-of-a-set-in-java, modified to
     * fit intent
     *
     * @param originalSet
     * @return
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
     * @param <T>
     * @return
     */
    public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);
        java.util.Collections.sort(list);
        return list;
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

                    if (command.equals("fsa")) {
                        Automaton fsa = new Automaton();
                        Scanner line = new Scanner(scan.nextLine());
                        fsa.setComment(line.next());

                        ArrayList<String> alphabet = new ArrayList<>();
                        line = new Scanner(scan.nextLine());
                        while (line.hasNext()) {
                            alphabet.add(line.next());
                        }
                        fsa.setAlphabet(alphabet);

                        String fsaLine = "";
                        ArrayList<State> states = new ArrayList<>();
                        ArrayList<String> transitions = new ArrayList<>();
                        while (!(fsaLine = scan.nextLine()).isEmpty()) {
                            transitions.add(fsaLine);
                            Scanner transition = new Scanner(fsaLine);
                            String first = transition.next();
                            State state = null;
                            if (first.contains("*")) {
                                first = first.substring(1, first.length());
                                state = new State(first);
                                state.setAcceptState(true);
                            } else {
                                state = new State(first);
                            }
                            states.add(state);
                        }
                        for (int i = 0; i < transitions.size(); i++) {
                            Scanner sc = new Scanner(transitions.get(i));
                            String firstState = sc.next();
                            if (firstState.contains("*"))
                                firstState = firstState.substring(1, firstState.length());
                            State st = getStateFromString(states, firstState);
                            int j = 1;
                            while (sc.hasNext()) {
                                String str = sc.next();
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
                    } else if (command.equals("nfa2dfa")) {
                        String oldName = scanIn.nextLine().trim();
                        Automaton oldFSA = (Automaton) varMap.get(oldName);

                        if (oldFSA.isDeterministic()) {
                            Automaton fsa = new Automaton(oldFSA);
                            if (!varMap.containsKey(name))
                                varMap.put(name, fsa);
                            else
                                varMap.replace(name, fsa);
                            break;
                        }
                        Automaton fsa = new Automaton();
                        ArrayList<String> alphabet = new ArrayList<>();
                        for (String s : oldFSA.getAlphabet()) {
                            if (!s.equals(".."))
                                alphabet.add(s);
                        }
                        fsa.setComment(oldFSA.getComment());
                        fsa.setAlphabet(alphabet);

                        List<State> oldStates = oldFSA.getStates();
                        Set<State> stateSet = new HashSet(oldStates);

                        Set<Set<State>> powerSet = powerSet(stateSet);
                        List<State> states = new ArrayList<>();

                        for (Set<State> stateList : powerSet) {
                            List<State> newList = asSortedList(stateList);
                            State s = new State(newList.toString(), stateList);
                            for (State st : newList) {
                                for (String str : alphabet) {
                                    ArrayList<State> transitions = st.getAllTransitions(str);
                                    for (State sta : transitions) {
                                        s.addTransition(sta, str);
                                        ArrayList<State> epsTransitions = sta.getAllTransitions("..");
                                        for (State state : epsTransitions) {
                                            s.addTransition(state, str);
                                        }
                                    }
                                }
                            }
                            states.add(s);
                        }
                        fsa.setStates(states);
                        fsa.sortStates();
                    } else if (command.equals("nfaConcat")) {
                        //TODO
                    } else if (command.equals("nfaStar")) {
                        //TODO
                    } else if (command.equals("nfaUnion")) {
                        //TODO
                    } else if (command.equals("dfaUnion")) {
                        //TODO
                    } else if (command.equals("prune")) {
                        //TODO
                    } else if (command.equals("FSAequivp")) {
                        //TODO
                    }
                    break;
                case "run": {
                    Automaton fsa;
                    String varIdentifier = scanIn.next();
                    if (!isValidIdentifier(varIdentifier)) {
                        System.err.println("Automaton identifier not valid");
                        break;
                    }

                    if (varMap.containsKey(varIdentifier))
                        fsa = (Automaton) varMap.get(varIdentifier);
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

    private State getStateFromString(ArrayList<State> states, String sta) {
        for (State st : states) {
            if (st.getStateID().equals(sta)) {
                return st;
            }
        }
        return null;
    }
}