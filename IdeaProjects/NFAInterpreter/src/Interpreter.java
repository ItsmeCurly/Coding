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
        String in = "";
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
                    String command = scanIn.nextLine().trim();

                    if (command.equals("fsa")) {
                        boolean isNFA = false;
                        Automaton fsa = new Automaton();
                        Scanner line_ = new Scanner(scan.nextLine());
                        fsa.setComment(line_.next());
                        String line = scan.nextLine();
                        Scanner fsaScan = new Scanner(line);
                        while (fsaScan.hasNext()) {
                            String s = fsaScan.next().trim();
                            if (s.length() != 1 && !s.equals("..")) {
                                System.err.println("Invalid character value for alphabet");
                                break label;
                            }
                            else if(s.equals(".."))
                                isNFA = true;
                            fsa.addAlphabet(s);
                        }

                        ArrayList<ArrayList<String>> states = new ArrayList<>();
                        while (!(line = scan.nextLine()).isEmpty()) {
                            fsaScan = new Scanner(line);
                            ArrayList<String> state = new ArrayList<>();
                            while (fsaScan.hasNext()) {
                                String s = fsaScan.next();
                                if (s.contains("*")) {
                                    s = s.substring(1);
                                    fsa.addAccept(s);
                                }
                                if(s.contains(","))
                                    isNFA = true;
                                state.add(s);
                            }
                            states.add(state);
                        }
                        fsaScan.close();
                        fsa.setTransitions(states);
                        fsa.setNFA(isNFA);
                        if (!varMap.containsKey(name))
                            varMap.put(name, fsa);
                        else
                            varMap.replace(name, fsa);
                    } else if (command.contains(this.VALUEPATTERN)) {
                        if (!varMap.containsKey(name))
                            varMap.put(name, command.replaceAll(this.VALUEPATTERN, ""));
                        else
                            varMap.replace(name, command.replaceAll(this.VALUEPATTERN, ""));
                    } else if(command.equals("nfa2dfa")) {
                        String oldName = scan.next();
                        if(!varMap.containsKey(oldName)) {
                            System.err.println("FSA not in variables");
                        }
                        Automaton old = (Automaton)varMap.get(oldName);
                        if(old.getNFA()) {
                            ArrayList<ArrayList<String>> oldStates = old.getTransitions();
                            ArrayList<String> states = old.getStates();
                            ArrayList<String> alphabet = old.getAlphabet();
                            Automaton dfa = new Automaton();
                            dfa.setComment(old.getComment());
                            ArrayList<ArrayList<String>> newStates;
                            List<List<String>> powerSet = powerSet(states);
                        }
                        else {
                            Automaton dfa = new Automaton(old);
                            //apply to varmap
                        }
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
                        fsa.runStart(value.replaceAll(this.VALUEPATTERN, ""));
                        if(fsa.getCounter() > 0)
                            System.out.println("accept");
                        else
                            System.out.println("reject");
                        break;
                    }
                    if (!isValidIdentifier(value)) {
                        System.err.println("Variable identifier not valid");
                        break;
                    }
                    if (varMap.containsKey(value)) {
                        fsa.runStart((String) varMap.get(value));
                        if(fsa.getCounter() > 0)
                            System.out.println("accept");
                        else
                            System.out.println("reject");
                    }
                    else
                        System.err.println("Variable Identifier " + value + " not present");
                    break;
                }
                default:
                    System.err.println("Invalid input");
                    break;
            }
        }
    }

    /**
     * received from http://rosettacode.org/wiki/Power_set#Java
     * @param list
     * @return
     */
    private ArrayList<ArrayList<String>> powerSet(ArrayList<String> list) {
        List<List<String>> ps = new ArrayList<>();
        ps.add(new ArrayList<String>());   // add the empty set

        // for every item in the original list
        for (String item : list) {
            List<List<String>> newPs = new ArrayList<>();

            for (List<String> subset : ps) {
                // copy all of the current powerset's subsets
                newPs.add(subset);

                // plus the subsets appended with the current item
                ArrayList<String> newSubset = new ArrayList<>(subset);
                newSubset.add(item);
                newPs.add(newSubset);
            }

            // powerset is now powerset of list.subList(0, list.indexOf(item)+1)
            ps = newPs;
        }
        return ps;
    }
}