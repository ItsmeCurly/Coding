import java.util.*;
import java.util.regex.Pattern;

public class CFG {
    private String name;
    private String comment;
    private Map<String, List<String>> rules;
    private List<String> varAlphabet;
    private List<String> termAlphabet;

    public CFG() {
        this("");
    }

    public CFG(String input) {
        rules = new HashMap<>();
        varAlphabet = new LinkedList<>();
        termAlphabet = new LinkedList<>();

        parseCFG(input);
    }

    public void parseCFG(String input) {
        Scanner scan = new Scanner(input);
        name = scan.next();
        comment = scan.nextLine();
        while(scan.hasNextLine()) {
            String ruleText = scan.nextLine();
            Scanner ruleScan = new Scanner(ruleText);
            if (ruleText.contains("->")) {

                String lhs = ruleScan.next();
                addVarAlphabet(lhs);

                List<String> rhs = rules.computeIfAbsent(lhs, k -> new ArrayList<>());

                String unUsed = ruleScan.next();

                String s = ruleScan.nextLine().trim();
                String [] sa = s.split("\\|");

                for(String st : sa) {
                    addTermAlphabet(st);
                }

                rhs.addAll(Arrays.asList(sa));
            }
            else if(ruleScan.next().equals("..")) {
                for(String s : ruleText.split(" ")) {
                    addTermAlphabet(s);
                }
            }
            else {
                for(String s : ruleText.split(" ")) {
                    addVarAlphabet(s);
                }
            }
        }
    }

    private void addTermAlphabet(String s) {
        if(!termAlphabet.contains(s)) {
            termAlphabet.add(s);
        }
    }

    private void addTermAlphabet(Collection<String> c) {
        for(String s : c) {
            if(!termAlphabet.contains(s)) {
                termAlphabet.add(s);
            }
        }
    }

    private void addVarAlphabet(String s) {
        if(!varAlphabet.contains(s)) {
            varAlphabet.add(s);
        }
    }

    private void addVarAlphabet (Collection<String> c) {
        for(String s : c) {
            if(!varAlphabet.contains(s)) {
                varAlphabet.add(s);
            }
        }
    }

    @Override
    public String toString() {
        return rules.toString();
    }
}
