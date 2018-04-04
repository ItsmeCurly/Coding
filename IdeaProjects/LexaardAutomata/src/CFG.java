import java.util.*;

public class CFG {
    private String name;
    private String comment;
    private String startRule;
    private List<Rule> rules;
    private List<String> varAlphabet;
    private List<String> termAlphabet;

    public CFG() {
        Rule.initNames();
        rules = new ArrayList<>();
        varAlphabet = new LinkedList<>();
        termAlphabet = new LinkedList<>();

        startRule = name = comment = "";
    }

    public CFG(String input) {
        Rule.initNames();
        rules = new ArrayList<>();
        varAlphabet = new LinkedList<>();
        termAlphabet = new LinkedList<>();

        parseCFG(input);
    }

    /**
     * Deep clone
     *
     * @param oldCFG
     */
    public CFG(CFG oldCFG) {
        Rule.initNames();
        this.name = oldCFG.name;
        this.comment = oldCFG.comment;
        this.startRule = oldCFG.startRule;
        List<Rule> temp = new ArrayList<>();
        for (Rule r : oldCFG.rules) {
            temp.add(new Rule(r));
        }
        this.rules = temp;
        this.termAlphabet = new ArrayList<>(oldCFG.termAlphabet);
        this.varAlphabet = new ArrayList<>(oldCFG.varAlphabet);
    }

    public static CFG chomskyNF(CFG cfg1) {
        CFG newCFG = new CFG(cfg1);
        //new start S_0 -> S
        boolean startInRHS = false;
        for (Rule r : newCFG.rules) {

            for (String rhs : r.getRHS()) {

                for (String rhsSplit : getTerms(rhs)) {

                    if (rhsSplit.equals(newCFG.startRule)) {
                        startInRHS = true;
                    }
                }
            }
        }

        if (startInRHS) {
            List<String> temp = new LinkedList<>();
            temp.add(newCFG.getRule(newCFG.startRule).getLHS());

            Rule newStartRule = new Rule("S_0", temp);

            newCFG.rules.add(0, newStartRule);

            newCFG.addVarAlphabet(newStartRule.getLHS());
            newCFG.startRule = newStartRule.getLHS();
        }
        //remove epsilon S -> ..

        for (Rule r : newCFG.rules) {
            if (r.getRHS().contains("..")) {
                removeEpsilon(r, newCFG.rules);
            }
        }

        newCFG.termAlphabet.remove("..");
        //remove unit rules S -> S

        //remove other unit rules A -> B

        for (Rule r : newCFG.rules) {
            removeUnitRules(r, newCFG);
        }

        //remove var > 2

        for (int i = 0; i < newCFG.rules.size(); i++) {
            replaceOversizeVariableLists(newCFG.rules.get(i), newCFG);
        }

        for (int i = 0; i < newCFG.rules.size(); i++) {
            replaceVariableTerminalPatterns(newCFG.rules.get(i), newCFG);
        }
        newCFG.sortRules();
        return newCFG;
    }

    public static boolean cfgGen(CFG cfg1, String w) {
        CFG cfgCNF = chomskyNF(cfg1);

        ArrayList<String> current = new ArrayList<>();
        current.add(cfgCNF.startRule);

        ArrayList<String> results = cfgCNF.getDerivations(current, 0, (w.length() == 0 ? 1 : w.length()));

        for (int i = 0; i < results.size(); i++) {
            results.set(i, results.get(i).replace(" ", ""));
        }

        System.out.println(results);

        return results.contains(w);
    }

    private static void removeEpsilon(Rule r, List<Rule> rules) {
        List<String> ruleRhs = r.getRHS();
        ruleRhs.remove("..");

        for (Rule rule : rules) {
            for (int i = 0; i < rule.getRHS().size(); i++) {
                String sArr = rule.getRHS().get(i);

                StringTokenizer st = new StringTokenizer(sArr);
                int loc = 0;
                while (st.hasMoreTokens()) {
                    String token = st.nextToken();
                    if (token.equals(r.getLHS())) {
                        String newString = "";
                        if (loc != 0) {
                            newString += sArr.substring(0, loc - 1);
                        }
                        if (loc + r.getLHS().length() < sArr.length()) {
                            newString += sArr.substring(loc + r.getLHS().length() + 1);
                        }
                        if (newString.equals("")) {
                            newString = "..";
                        }
                        rule.addRHS(newString);
                    }
                    loc += token.length() + 1;
                }
            }
        }
        for (Rule rule : rules) {
            if (rule.getRHS().contains("..")) {
                removeEpsilon(rule, rules);
            }
        }
    }

    private static void removeUnitRules(Rule r, CFG cfg) {
        for (int i = 0; i < r.getRHS().size(); i++) {
            if (r.getRHS().get(i).length() == 1 && getNumVars(r.getRHS().get(i), cfg) == 1) {
                String s = r.getRHS().remove(i);
                Rule rule = cfg.getRule(s);
                if (cfg.rules.contains(cfg.getRule(s)) && !rule.equals(r)) {
                    removeUnitRules(rule, cfg);
                }
                r.addRHS(rule.getRHS());
                i -= 1;
            }
        }
    }

    private static void replaceOversizeVariableLists(Rule rule, CFG newCFG) {
        for (int i = 0; i < rule.getRHS().size(); i++) {
            String rhs1 = rule.getRHS().get(i);
            StringTokenizer st = new StringTokenizer(rhs1);
            String s = st.nextToken();
            if (getNumTerms(rhs1) > 2 || getNumTerminals(rhs1, newCFG) > 1) {
                String convert = rhs1.substring(s.length() + 1).trim();
                Rule r = createRule(newCFG, convert);

                rule.setRHS(i, rhs1.substring(0, s.length()) + " " + r.getLHS());
            }
        }
    }

    private static void replaceVariableTerminalPatterns(Rule rule, CFG newCFG) {
        for (int i = 0; i < rule.getRHS().size(); i++) {
            String rhs1 = rule.getRHS().get(i);
            StringTokenizer st = new StringTokenizer(rhs1);
            String s = st.nextToken();
            if (getNumVars(rhs1, newCFG) > 0 && getNumTerminals(rhs1, newCFG) > 0) {
                if (newCFG.isVar(rhs1.substring(0, s.length()))) {
                    String convert = rhs1.substring(s.length() + 1).trim();
                    Rule r = createRule(newCFG, convert);

                    rule.setRHS(i, rhs1.substring(0, s.length()) + " " + r.getLHS());
                } else {
                    String convert = rhs1.substring(0, s.length()).trim();
                    Rule r = createRule(newCFG, convert);

                    rule.setRHS(i, r.getLHS() + " " + rhs1.substring(s.length() + 1));
                }
            }
        }
    }

    private static int getNumVars(String s, CFG cfg) {
        int count = 0;
        for (String st : getTerms(s)) {
            if (cfg.varAlphabet.contains(st)) count += 1;
        }
        return count;
    }

    private static Rule createRule(CFG newCFG, String convert) {
        Rule r = new Rule();
        boolean containsRHS = false;
        for (Rule rule1 : newCFG.rules) {
            if (rule1.getRHS().size() == 1 && rule1.getRHS().contains(convert)) {
                containsRHS = true;
                r = rule1;
            }
        }
        if (!containsRHS) {
            r.addRHS(convert);
            newCFG.addRule(r);
            newCFG.addVarAlphabet(r.getLHS());
        }
        return r;
    }

    private static int getNumTerminals(String s, CFG cfg) {
        int count = 0;
        for (String st : getTerms(s)) {
            if (cfg.termAlphabet.contains(st)) count += 1;
        }
        return count;
    }

    private static int getNumTerms(String s) {
        return getTerms(s).length;
    }

    private static String[] getTerms(String s) {
        return s.split(" ");
    }

    private ArrayList<String> getDerivations(ArrayList<String> currentDerivations, int numberDerivation, int w_length) {
        if (numberDerivation == 2 * w_length - 1) return currentDerivations;

        ArrayList<String> nextDerivations = new ArrayList<>();

        for (String derivation : currentDerivations) {

            int currentIndex = 0;

            for (String splitDerivation : getTerms(derivation)) {

                if (isVar(splitDerivation)) {

                    Rule r = getRule(splitDerivation);

                    for (String patternString : r.getRHS()) {
                        String s = "";

                        s += derivation.substring(0, currentIndex);

                        s += patternString;

                        if (currentIndex + splitDerivation.length() < derivation.length()) {
                            s += derivation.substring(currentIndex + splitDerivation.length());
                        }

                        nextDerivations.add(s);
                    }
                }

                currentIndex += splitDerivation.length() + 1;

            }

        }

        return getDerivations(nextDerivations, numberDerivation + 1, w_length);
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
            } else if (!ruleText.contains("..")) {
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

                ruleScan.next();

                String s = ruleScan.nextLine().trim();
                String[] sa = s.split(" \\| ");

                for(String st : sa) {
                    for (String str : getTerms(st)) {
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
                for (String s : getTerms(ruleText)) {
                    addTermAlphabet(s);
                }
            }
            else {
                for (String s : getTerms(ruleText)) {
                    addVarAlphabet(s);
                }
            }
        }
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

    private void addVarAlphabet(String s) {
        if(!varAlphabet.contains(s)) {
            varAlphabet.add(s);
        }
    }

    private void sortRules() {
        List<Rule> sortedRules = new ArrayList<>(rules);
        Collections.sort(sortedRules);
        sortedRules.remove(getRule(startRule));
        sortedRules.add(0, getRule(startRule));
        rules = sortedRules;
    }

    private String printList(ArrayList<String> list) {
        String result = "";
        for (String s : list) {
            result += s + " ";
        }
        return result;
    }

    public Rule getRule(String lhs) {
        for (Rule r : rules) {
            if (r.getLHS().equals(lhs)) {
                return r;
            }
        }
        return new Rule();
    }

    public boolean isVar(String st) {
        for (String var : varAlphabet) {
            if (var.equals(st)) return true;
        }
        return false;
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
                for (String st : getTerms(s)) {
                    termAlphabet.remove(st);
                }
            }
        }

        if (varAlphabet.contains(startRule)) {
            list.add(0, printList(varAlphabet));
        } else {
            if (!varAlphabet.isEmpty())
                list.add(printList(varAlphabet));
        }

        if (!termAlphabet.isEmpty()) {
            list.add(printList(termAlphabet));
        }

        String result = "";
        result += name + " " + comment + '\n';
        for (String s : list) {
            result += s + '\n';
        }

        return result;
    }
}
