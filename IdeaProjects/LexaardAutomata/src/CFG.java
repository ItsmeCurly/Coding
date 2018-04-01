import java.util.*;

public class CFG {
    private String name;
    private String comment;
    private List<Rule> rules;
    private List<String> varAlphabet;
    private List<String> termAlphabet;

    public CFG() {
        this("");
    }

    public CFG(String input) {
        rules = new LinkedList<>();
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
                Rule r = new Rule();

                String lhs = ruleScan.next();
                addVarAlphabet(lhs);

                r.setLHS(lhs);

                if(rules.contains(r)) {
                    r = getRule(lhs);
                }

                String temp = ruleScan.next();

                String s = ruleScan.nextLine().trim();
                String [] sa = s.split("\\|");

                for(String st : sa) {
                    addTermAlphabet(st);
                }
                r.addRHS(sa);
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

    public Rule getRule(String lhs) {
        for(Rule r : rules) {
            if(r.getLHS().equals(lhs)) {
                return r;
            }
        }
        return null;
    }


    public boolean containsRule(String lhs) {
        for(Rule r : rules) {
            if(r.getLHS().equals(lhs)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return rules.toString();
    }
}
