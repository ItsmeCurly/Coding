import java.util.*;

public class CFG {
    private ArrayList<String> availableNames;
    private String name;
    private String startRule;

    private List<Rule> rules;
    private List<String> varAlphabet;
    private List<String> termAlphabet;

    /**
     * Default constructor of CFG
     */
    public CFG() {
        initNames();
        rules = new ArrayList<>();
        varAlphabet = new LinkedList<>();
        termAlphabet = new LinkedList<>();

        startRule = name = "";
    }

    /**
     * CFG constructor, with string representation of the CFG
     *
     * @param input The string representation
     */
    public CFG(String input) {
        initNames();
        rules = new ArrayList<>();
        varAlphabet = new LinkedList<>();
        termAlphabet = new LinkedList<>();

        parseCFG(input);
    }

    /**
     * Deep clone of a CFG, passed with a currently existing CFG
     * @param oldCFG The old CFG to copy
     */
    public CFG(CFG oldCFG) {
        initNames();   //init names for default rules
        this.name = oldCFG.name;
        this.startRule = oldCFG.startRule;
        List<Rule> temp = new ArrayList<>();
        for (Rule r : oldCFG.rules) {
            temp.add(new Rule(r));
            availableNames.remove(r.getLHS());
        }
        this.rules = temp;
        this.termAlphabet = new ArrayList<>(oldCFG.termAlphabet);
        this.varAlphabet = new ArrayList<>(oldCFG.varAlphabet);
    }

    /**
     * Generates the chomsky normal form representation of a CFG, to be used to check the string
     * generation of a certain string in 2n-1 steps.
     *
     * @param cfg1 The CFG to convert to CNF, will be deep cloned to keep original state of other CFG
     * @return The new CFG, being a CNF representation of cfg1
     */
    public static CFG chomskyNF(CFG cfg1) {
        CFG newCFG = new CFG(cfg1); //deep clone cfg
        //new start S_0 -> S
        boolean startInRHS = false; //if start rule exists in the RHS, must create a new start rule
        for (Rule r : newCFG.rules) {

            for (String rhs : r.getRHS()) {

                for (String rhsSplit : getTerms(rhs)) {

                    if (rhsSplit.equals(newCFG.startRule)) {
                        startInRHS = true;
                    }
                }
            }
        }

        if (startInRHS) { //add new start to ruleset of CFG
            List<String> temp = new LinkedList<>();
            temp.add(newCFG.getRule(newCFG.startRule).getLHS());

            Rule newStartRule = new Rule("S_0", temp);

            newCFG.rules.add(0, newStartRule);

            newCFG.addVarAlphabet(newStartRule.getLHS());
            newCFG.startRule = newStartRule.getLHS();
        }
        //remove epsilon S -> ..

        for (Rule r : newCFG.rules) {   //for every rule, remove the .. transitions and add their RHS to the original rule
            if (r.getRHS().contains("..")) {
                removeEpsilon(r, newCFG.rules);
            }
        }

        newCFG.termAlphabet.remove("..");   //remove the .. term from the alphabet, no longer used
        //remove unit rules S -> S and
        //remove other unit rules A -> B

        for (Rule r : newCFG.rules) {
            removeUnitRules(r, newCFG);
        }

        //remove var > 2

        for (int i = 0; i < newCFG.rules.size(); i++) {
            replaceOversizeVariableLists(newCFG.rules.get(i), newCFG);
        }
        //remove vars of representation aB or Ab
        for (int i = 0; i < newCFG.rules.size(); i++) {
            replaceVariableTerminalPatterns(newCFG.rules.get(i), newCFG);
        }
        //sort the rules into lexicographic order, with the start var at the start
        newCFG.sortRules();
        return newCFG;
    }

    /**
     * Checks if a CFG generates the certain string
     *
     * @param cfg1 The cfg
     * @param w    The string
     * @return Whether cfg1's representation and rules generate w in 2n-1 steps
     */
    public static boolean cfgGen(CFG cfg1, String w) {
        CFG cfgCNF = chomskyNF(cfg1);   //create a chomsky representation of the cfg

        ArrayList<String> current = new ArrayList<>();  //temp arraylist to pass method
        current.add(cfgCNF.startRule);  //add startrule to list

        ArrayList<String> results = cfgCNF.getDerivations(current, 0, (w.length() == 0 ? 1 : w.length()));

        for (int i = 0; i < results.size(); i++) {
            results.set(i, results.get(i).replace(" ", ""));
        }

        return results.contains(w);
    }

    /**
     * Removes the epsilon transitions from a certin rule
     *
     * @param r     The rule
     * @param rules The set of rules of the CFG to modify
     */
    private static void removeEpsilon(Rule r, List<Rule> rules) {
        List<String> ruleRhs = r.getRHS();  //get rhs for epsilon
        ruleRhs.remove("..");

        for (Rule rule : rules) {   //for every rule in ruleset, if there is an reference to the LHS of the removed .. transition
            for (int i = 0; i < rule.getRHS().size(); i++) {    //add everything from that rule to the previous rule
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
                removeEpsilon(rule, rules); //if a new .. is added, must recursively do the epsilon removal for the new rules with the .. transition
            }
        }
    }

    /**
     * remove the rules A -> B by adding all of the RHS rules to the first LHS
     *
     * @param r   The rule to check if it contains any unit rules
     * @param cfg The CFG that contains the rule
     */
    private static void removeUnitRules(Rule r, CFG cfg) {
        for (int i = 0; i < r.getRHS().size(); i++) {
            if (r.getRHS().get(i).length() == 1 && getNumVars(r.getRHS().get(i), cfg) == 1) { //if is a unit rule
                String s = r.getRHS().remove(i); //remove the unit rule
                Rule rule = cfg.getRule(s); //get the removed rules RHS and add it to the original
                if (cfg.rules.contains(cfg.getRule(s)) && !rule.equals(r)) {
                    removeUnitRules(rule, cfg);
                }
                r.addRHS(rule.getRHS());
                i -= 1; //decrement, so that all the new rules are checked as well for unit rules
            }
        }
    }

    /**
     * Remove all variable lists that are greater than 2 terms
     *
     * @param rule   CFG.Rule to check if there are any oversize terms
     * @param newCFG The CFG to check the rule for
     */
    private static void replaceOversizeVariableLists(Rule rule, CFG newCFG) {
        for (int i = 0; i < rule.getRHS().size(); i++) {
            String rhs1 = rule.getRHS().get(i);
            StringTokenizer st = new StringTokenizer(rhs1);
            String s = st.nextToken();
            if (getNumTerms(rhs1) > 2 || getNumTerminals(rhs1, newCFG) > 1) {   //if vars > 2 or terms > 1, create a new var in format AB or aB
                String convert = rhs1.substring(s.length() + 1).trim();
                Rule r = createRule(newCFG, convert);

                rule.setRHS(i, rhs1.substring(0, s.length()) + " " + r.getLHS()); //convert the rule
            }
        }
    }

    /**
     * Changes patterns A -> aB and A -> Ba to be two variables
     *
     * @param rule   CFG.Rule to check if there are any rules in the format previously specified
     * @param newCFG The CFG to check the rule for
     */
    private static void replaceVariableTerminalPatterns(Rule rule, CFG newCFG) {
        for (int i = 0; i < rule.getRHS().size(); i++) { //same as last, except the check is against form aB or Ab
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

    /**
     * Get the number of variables in the CFG for the certain string
     * @param s The string to check the number of variables
     * @param cfg The cfg, containing the variable alphabet
     * @return The number of variables
     */
    private static int getNumVars(String s, CFG cfg) {
        int count = 0;
        for (String st : getTerms(s)) {
            if (cfg.varAlphabet.contains(st)) count += 1;
        }
        return count;
    }

    /**
     * Creates a rule with the specified RHS
     * @param newCFG The cfg to add the rule to
     * @param convert The only RHS term on the rule
     * @return A new rule with a generated LHS pointing to the specified RHS
     */
    private static Rule createRule(CFG newCFG, String convert) {
        Rule r = new Rule(newCFG.getAvailableName());
        boolean containsRHS = false;
        for (Rule rule1 : newCFG.rules) {
            if (rule1.getRHS().size() == 1 && rule1.getRHS().contains(convert)) { //create rule only if rule does not exist already, and only if RHS is only the new RHS
                containsRHS = true;
                r = rule1;
            }
        }
        if (!containsRHS) { //add rule if new rule is generated
            r.addRHS(convert);
            newCFG.addRule(r);
            newCFG.addVarAlphabet(r.getLHS());
        }
        return r;
    }

    /**
     * Copy of getNumVars except returns number of terminals
     * @param s The string to check the number of terminals
     * @param cfg The cfg, containing the terminal alphabet
     * @return The number of terminals in the string
     */
    private static int getNumTerminals(String s, CFG cfg) {
        int count = 0;
        for (String st : getTerms(s)) {
            if (cfg.termAlphabet.contains(st)) count += 1;
        }
        return count;
    }

    /**
     * Get the number of terms in a string
     * @param s The string to get the number of terms
     * @return The number of terms in a string
     */
    private static int getNumTerms(String s) {
        return getTerms(s).length;
    }

    /**
     * Returns the string split into an array, as the representation is x_y_z, with _ meaning space
     * @param s The string to return the terms for
     * @return The termset
     */
    public static String[] getTerms(String s) {
        return s.split(" ");
    }

    private String getAvailableName() {
        return availableNames.remove(0);
    }

    /**
     * Initializes the names for a new CFG to use as rules
     */
    public void initNames() {
        availableNames = new ArrayList<>();
        for (int i = 65; i <= 90; i++) {
            availableNames.add((char) i + "");
        }
        int i = availableNames.size();
        for (int j = 0; j < i; j++) {
            availableNames.add(availableNames.get(j) + "*");
            availableNames.add(availableNames.get(j) + '\'');
        }
    }

    /**
     * Given a string and CFG, return the number of derivations for a certain amount of derivations when used with CNF
     * @param currentDerivations Used to save the derivations through recursive pass
     * @param numberDerivation The derivation number, to be used to check 2n-1
     * @param w_length The string length, to be used to check against 2n-1
     * @return The derivations that can be derived in 2n-1 steps
     */
    private ArrayList<String> getDerivations(ArrayList<String> currentDerivations, int numberDerivation, int w_length) {
        if (numberDerivation == 2 * w_length - 1) return currentDerivations;    //return the derivations if steps = 2n-1

        ArrayList<String> nextDerivations = new ArrayList<>(); //store nextDerivations of next step

        for (String derivation : currentDerivations) {  //for every currentDerivation of the CNF, compute all the possible next steps the derivation can take

            int currentIndex = 0;

            for (String splitDerivation : getTerms(derivation)) { //split the derivation up into terms, then if they are a variable add all the RHS rules to the next derivations and pass it to the next step

                if (isVar(splitDerivation)) {

                    Rule r = getRule(splitDerivation);

                    for (String patternString : r.getRHS()) {
                        String s = "";

                        s += derivation.substring(0, currentIndex);

                        s += patternString;

                        if (currentIndex + splitDerivation.length() < derivation.length()) {
                            s += derivation.substring(currentIndex + splitDerivation.length());
                        }

                        nextDerivations.add(s); //add the new derivation to the next derivations
                    }
                }

                currentIndex += splitDerivation.length() + 1;   //for proper formatting

            }

        }

        return getDerivations(nextDerivations, numberDerivation + 1, w_length);
    }

    /**
     * Parses a string and generates a CFG from it
     * @param input The string representation of the CFG
     */
    public void parseCFG(String input) {
        Scanner scan = new Scanner(input);
        name = scan.next(); //get name and comment, not to sure about the usefulness or correctness of this

        String temp = scan.nextLine();

        int i = 0;
        while (scan.hasNextLine()) {    //first scan all of the rule LHS to store varAlphabets when scanning the rest of the RHS
            //by doing this, we can also store the new terms that are used
            String ruleText = scan.nextLine();
            Scanner ruleScan = new Scanner(ruleText);
            if (ruleText.contains("->")) {
                String lhs = ruleScan.next();
                addVarAlphabet(lhs);
                if (i == 0) {
                    startRule = lhs;    //set startrule if existing rule, if not do other case
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

        while (scan.hasNextLine()) {     //rescan through all the rules,
            String ruleText = scan.nextLine();
            Scanner ruleScan = new Scanner(ruleText);
            if (ruleText.contains("->")) {

                String lhs = ruleScan.next();
                availableNames.remove(lhs);
                addVarAlphabet(lhs);

                Rule r = getRule(lhs);      //get the rule for a certain LHS, if doesn't exist returns a new rule

                r.setLHS(lhs);

                ruleScan.next();

                String s = ruleScan.nextLine().trim();
                String[] sa = s.split(" \\| ");

                for(String st : sa) {
                    for (String str : getTerms(st)) {
                        if (!isVar(str)) {
                            addTermAlphabet(str);   //add to term alphabet if not a variable
                        }
                    }
                }
                r.addRHS(sa);
                addRule(r); //add rule and sort the new states
                sortRules();
            } else if (ruleScan.next().equals("..")) {
                for (String s : getTerms(ruleText)) {
                    addTermAlphabet(s); //for the term list
                }
            } else {
                for (String s : getTerms(ruleText)) {
                    addVarAlphabet(s);  //for the variable list
                }
            }
        }
    }

    /**
     * Adds a rule to the CFG
     * @param r The rule to add
     */
    private void addRule(Rule r) {
        if (!rules.contains(r)) {
            rules.add(r);
        }
    }

    /**
     * Adds to the term alphabet if it does not exist
     * @param s The term to add to the alphabet
     */
    private void addTermAlphabet(String s) {
        if(!termAlphabet.contains(s)) {
            termAlphabet.add(s);
        }
    }

    /**
     * Adds to the variable alphabet if it does not exist
     * @param s The term to add to the alphabet
     */
    private void addVarAlphabet(String s) {
        if(!varAlphabet.contains(s)) {
            varAlphabet.add(s);
        }
    }

    /**
     * Sorts the rules in lexicographic order
     */
    private void sortRules() {
        List<Rule> sortedRules = new ArrayList<>(rules);
        Collections.sort(sortedRules);  //sort in lexicographic order by compareTo
        sortedRules.remove(getRule(startRule)); //add start rule to beginning of list
        sortedRules.add(0, getRule(startRule));
        rules = sortedRules;    //set new rules
    }

    /**
     * Helper method to print a list
     * @param list The list to print
     * @return The printed result of the list
     */
    private String printList(ArrayList<String> list) {
        String result = "";
        for (String s : list) {
            result += s + " ";
        }
        return result;
    }

    /**
     * Searches the CFG's rules for the specified LHS rule
     * @param lhs The LHS of the rule to be found
     * @return The rule, null if does not exist
     */
    public Rule getRule(String lhs) {
        for (Rule r : rules) {
            if (r.getLHS().equals(lhs)) {
                return r;
            }
        }
        return new Rule(getAvailableName());
    }

    /**
     * Checks if a certain string is a variable in the CFG varAlphabet
     * @param st The string to check if is a variable
     * @return If a string is a variable in the alphabet
     */
    public boolean isVar(String st) {
        for (String var : varAlphabet) {
            if (var.equals(st)) return true;
        }
        return false;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public String getStartRule() {
        return startRule;
    }

    @Override
    public String toString() {
        ArrayList<String> list = new ArrayList<>(); //prints a string representation of the CFG, following the format presented by the homework

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
        result += name + '\n';
        for (String s : list) {
            result += s + '\n';
        }

        return result;
    }

    public static class Rule implements Comparable<Rule> {
        private String lhs;
        private List<String> rhs;

        /**
         * Creates a rule with a LHS searched from available names, as must be unique, only has 78 choices
         *
         * @param lhs The LHS variable, either pregenerated or gotten from input
         */
        public Rule(String lhs) {
            this.lhs = lhs;
            this.rhs = new LinkedList<>();
        }

        /**
         * Creates a rule with specified lhs and rh s
         *
         * @param lhs String representing the LHS
         * @param rhs List representing the RHS
         */
        public Rule(String lhs, List<String> rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        /**
         * Creates a rule from the specified rule
         *
         * @param o The other rule
         */
        public Rule(Rule o) {
            this.lhs = o.lhs;
            this.rhs = new LinkedList<>(o.rhs);
        }

        /**
         * Adds an array to the RHS list
         *
         * @param rhs The array to add to the RHS variable
         */
        public void addRHS(String[] rhs) {
            for (String s :
                    rhs) {
                addRHS(s);
            }
        }

        /**
         * Adds a list to the RHS
         *
         * @param rhs The list to add to the RHS
         */
        public void addRHS(List<String> rhs) {
            for (String s : rhs) {
                addRHS(s);
            }
        }

        /**
         * Adds to the end of the RHS list
         *
         * @param newString The string to add to RHS
         */
        public void addRHS(String newString) {
            if (!rhs.contains(newString)) {
                rhs.add(newString);
            }
        }

        /**
         * Returns the rhs list
         *
         * @return The RHS variables
         */
        public List<String> getRHS() {
            return rhs;
        }

        /**
         * Sets a certain rhs variable at index index
         *
         * @param index The index to change the rhs
         * @param s     The string to change index to
         */
        public void setRHS(int index, String s) {
            this.rhs.set(index, s);
        }

        /**
         * Returns the LHS
         *
         * @return the lhs string
         */
        public String getLHS() {
            return lhs;
        }

        /**
         * Sets the lhs variable
         *
         * @param lhs The new lhs
         */
        public void setLHS(String lhs) {
            this.lhs = lhs;
        }

        public String toString() {
            String result = lhs;

            if (!rhs.isEmpty()) {
                result += " -> ";
            }

            for (String rh : rhs) {
                result += rh + " | ";
            }

            if (!rhs.isEmpty()) {
                result = result.substring(0, result.length() - 3);
            }

            return result;
        }

        @Override
        public int compareTo(Rule o) {
            return getLHS().compareTo(o.getLHS());
        }
    }
}
