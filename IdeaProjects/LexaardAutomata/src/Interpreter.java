import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Interpreter {
    private static final String VALUEPATTERN = "\"";   //value to indicate number instead of variable

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
                        String input = scan.nextLine() + '\n';
                        input += scan.nextLine() + '\n';
                        String temp;
                        while (!(temp = scan.nextLine()).isEmpty()) {
                            input += temp + '\n';
                        }
                        Automaton fsa = Automaton.constructFSA(input);

                        if (!varMap.containsKey(name))
                            varMap.put(name, fsa);
                        else
                            varMap.replace(name, fsa);
                    } else if (command.contains(VALUEPATTERN)) {
                        if (!varMap.containsKey(name))
                            varMap.put(name, command.replaceAll(VALUEPATTERN, ""));
                        else
                            varMap.replace(name, command.replaceAll(VALUEPATTERN, ""));
                    } else if (command.equals("true") || command.equals("false")) {
                        if (!varMap.containsKey(name))
                            varMap.put(name, command);
                        else
                            varMap.replace(name, command);
                    }
                    //if user command is nfa2Dfa, read in one FSA and return the DFA equivalent regardless if DFA or NFA
                    else if (command.equals("nfa2dfa")) {
                        Automaton fsa = Automaton.nfa2Dfa((Automaton) varMap.get(scanIn.next()));

                        if (!varMap.containsKey(name))
                            varMap.put(name, fsa);
                        else
                            varMap.replace(name, fsa);
                    }
                    //if user command is dfaUnion, read in the two DFAs and put it in the variable mapping
                    else if (command.equals("dfaUnion")) {
                        Automaton fsa = Automaton.dfaUnion((Automaton) varMap.get(scanIn.next()), (Automaton) varMap.get(scanIn.next()));
                        if (!varMap.containsKey(name))
                            varMap.put(name, fsa);
                        else
                            varMap.replace(name, fsa);
                    }
                    //if user command is nfaConcat, read in the two NFAs and put it in the variable mapping
                    else if (command.equals("nfaConcat")) {
                        Automaton fsa = Automaton.nfaConcat((Automaton) varMap.get(scanIn.next()), (Automaton) varMap.get(scanIn.next()));
                        if (!varMap.containsKey(name))
                            varMap.put(name, fsa);
                        else
                            varMap.replace(name, fsa);
                    }
                    //if user command is nfaStar, read in the NFA and insert into variable mapping
                    else if (command.equals("nfaStar")) {
                        Automaton fsa = Automaton.nfaStar((Automaton) varMap.get(scanIn.next()));
                        if (!varMap.containsKey(name))
                            varMap.put(name, fsa);
                        else
                            varMap.replace(name, fsa);
                    }
                    //if user command is nfaUnion, read in the two NFAs and insert into the variable mapping
                    else if (command.equals("nfaUnion")) {
                        Automaton fsa = Automaton.nfaUnion((Automaton) varMap.get(scanIn.next()), (Automaton) varMap.get(scanIn.next()));
                        if (!varMap.containsKey(name))
                            varMap.put(name, fsa);
                        else
                            varMap.replace(name, fsa);
                    }
                    //if user command is prune, read in the FSA to prune and insert the pruned FSA into the variable mapping
                    else if (command.equals("prune")) {
                        Automaton fsa = Automaton.fsaPrune((Automaton) varMap.get(scanIn.next()));
                        if (!varMap.containsKey(name))
                            varMap.put(name, fsa);
                        else
                            varMap.replace(name, fsa);
                    }
                    //if user command is FSAequivP, read in the two FSAs and return a boolean denoting whether they are equivalent
                    else if (command.equals("FSAequivp")) {
                        boolean newBool = Automaton.fsaEquivP((Automaton) varMap.get(scanIn.next()), (Automaton) varMap.get(scanIn.next()));
                        if (!varMap.containsKey(name))
                            varMap.put(name, newBool);
                        else
                            varMap.replace(name, newBool);
                    } else if (command.equals("regex")) {
                        String input = "";
                        String temp;
                        while (!(temp = scan.nextLine()).isEmpty()) {
                            input += temp;
                        }
                        Regex newRegex = new Regex(input);
                        if (!varMap.containsKey(name))
                            varMap.put(name, newRegex);
                        else
                            varMap.replace(name, newRegex);
                    } else if (command.equals("regex2fsa")) {

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
                        System.out.println(fsa.run(value.replaceAll(VALUEPATTERN, "")));
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
}