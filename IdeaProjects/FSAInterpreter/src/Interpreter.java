import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Interpreter {
    private final String VALUEPATTERN = "\"";

    public Interpreter() {
        this.run();
    }

    public static void main(String[] args) {
        new Interpreter();
    }

    private void run() {
        Scanner scan = new Scanner(System.in);
        Map<String, Object> varMap = new LinkedHashMap<>();
        label:
        while (true) {
            switch (scan.next()) {
                case "quit":
                    break label;
                case "print": {
                    System.out.println(varMap.get(scan.next()));
                    break;
                }
                case "define":
                    String name = scan.next();
                    if (!isValidIdentifier(name)) {
                        System.err.println("Invalid identifier for variable");
                        break;
                    }
                    String data = scan.nextLine().trim();

                    if (data.equals("fsa")) {
                        Automaton fsa = new Automaton();
                        Scanner line_ = new Scanner(scan.nextLine());
                        fsa.setDesc(line_.next());
                        String line = scan.nextLine();
                        Scanner fsaScan = new Scanner(line);
                        while (fsaScan.hasNext()) {
                            String s = fsaScan.next().trim();
                            if (s.length() != 1) {
                                System.err.println("Invalid character value for alphabet");
                                break;
                            }
                            fsa.addAlphabet(s.charAt(0));
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
                                state.add(s);
                            }
                            states.add(state);
                        }
                        fsaScan.close();
                        fsa.setTransitions(states);
                        if (!varMap.containsKey(name))
                            varMap.put(name, fsa);
                        else
                            varMap.replace(name, fsa);
                    } else if (data.contains(this.VALUEPATTERN)) {
                        if (!varMap.containsKey(name))
                            varMap.put(name, data.replaceAll(this.VALUEPATTERN, ""));
                        else
                            varMap.replace(name, data.replaceAll(this.VALUEPATTERN, ""));
                    }

                    break;
                case "run": {
                    Automaton fsa;
                    String varIdentifier = scan.next();
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
                    String value = scan.next();
                    if (value.contains("\"")) {
                        System.out.println(fsa.run(value.replaceAll(this.VALUEPATTERN, "")));
                        break;
                    }
                    if (!isValidIdentifier(value)) {
                        System.err.println("Variable identifier not valid");
                        break;
                    }
                    if (varMap.containsKey(value))
                        System.out.println(fsa.run((String) varMap.get(value)));
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