import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Interpreter {
    public Interpreter() {
        this.run();
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
                    String data = scan.nextLine().trim();

                    if (data.equals("fsa")) {
                        Automaton fsa = new Automaton();
                        fsa.setDesc(scan.nextLine());
                        String line = scan.nextLine();
                        Scanner fsaScan = new Scanner(line);
                        while (fsaScan.hasNext())
                            fsa.addAlphabet(fsaScan.next().trim().charAt(0));
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
                        varMap.put(name, fsa);
                    } else if (data.contains("\"")) {
                        varMap.put(name, data.substring(1, data.length() - 1));
                    }

                    break;
                case "run": {
                    Automaton fsa = (Automaton) varMap.get(scan.next());
                    String x = scan.next();
                    if (x.contains("\"")) {
                        fsa.run(x);
                    } else if (varMap.containsKey(x)) {
                        fsa.run((String) varMap.get(x));
                    }

                    break;
                }
                default:
                    System.err.println("Invalid input");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        new Interpreter();
    }
}
