import java.util.*;

public class CFG {
    private String name;
    private String comment;
    private String startRule;
    private List<Rule> rules;
    private List<String> varAlphabet;
    private List<String> termAlphabet;

    public CFG() {
        this("");
    }

    public CFG(String input) {
        rules = new ArrayList<>();
        varAlphabet = new LinkedList<>();
        termAlphabet = new LinkedList<>();

        parseCFG(input);
        System.out.println(this);
    }

    public void parseCFG(String input) {
        Scanner scan = new Scanner(input);
        name = scan.next();
        comment = scan.nextLine().trim();
        int i = 0;
        while (scan.hasNextLine()) {

            String ruleText = scan.nextLine();
            Scanner ruleScan = new Scanner(ruleText);
            if (ruleText.contains("->")) {
                String lhs = ruleScan.next();
                addVarAlphabet(lhs);
                if (i == 0) {
                    startRule = lhs;
                }
            } else if (ruleText.contains("..")) {

            } else {
                String start = ruleScan.next();
                if (i == 0)
                    startRule = start;
            }
            i += 1;
        }
        scan = new Scanner(input);

        scan.nextLine();

        while(scan.hasNextLine()) {
            String ruleText = scan.nextLine();
            Scanner ruleScan = new Scanner(ruleText);
            if (ruleText.contains("->")) {

                String lhs = ruleScan.next();
                addVarAlphabet(lhs);

                Rule r = getRule(lhs);

                r.setLHS(lhs);

                String unUsed = ruleScan.next();

                String s = ruleScan.nextLine().trim();
                String [] sa = s.split("\\|");

                for(String st : sa) {
                    for (String str : st.split(" ")) {
                        if (!isVar(str)) {
                            addTermAlphabet(str);
                        }
                    }
                }
                r.addRHS(sa);
                addRule(r);
                sortRules();
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

    private void sortRules() {

    }

    private Rule getRule(String lhs) {
        for (Rule r : rules) {
            if (r.getLHS().equals(lhs)) {
                return r;
            }
        }
        return new Rule();
    }

    private boolean isVar(String st) {
        for (String var : varAlphabet) {
            if (var.equals(st)) return true;
        }
        return false;
    }

    private void addRule(Rule r) {
        if (!rules.contains(r)) {
            rules.add(r);
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

    private String printList(ArrayList<String> list) {
        String result = "";
        for (String s : list) {
            result += s + " ";
        }
        return result;
    }

    @Override
    public String toString() {
        ArrayList<String> list = new ArrayList<>();

        ArrayList<String> varAlphabet = new ArrayList<>(this.varAlphabet);
        ArrayList<String> termAlphabet = new ArrayList<>(this.termAlphabet);

        for (Rule r : rules) {
            list.add(r.toString());
            varAlphabet.remove(r.getLHS());
            for (String s : r.getRHS()) {
                for (String st : s.split(" ")) {
                    termAlphabet.remove(st);
                }
            }
        }

        if (varAlphabet.contains("S")) {
            list.add(0, printList(varAlphabet));
        } else {
            if (!varAlphabet.isEmpty())
                list.add(printList(varAlphabet));
        }

        if (!termAlphabet.isEmpty()) {
            list.add(printList(termAlphabet));
        }

        String result = "";

        for (String s : list) {
            result += s + '\n';
        }

        return result;
    }
}
