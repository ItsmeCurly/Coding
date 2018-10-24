import java.util.*;

public class PDA {
    private List<PDAState> states; //states of PDA
    private Stack<String> stack; //stack of PDA

    private String name;

    private List<String> stackAlphabet;

    private List<Stjring> inputAlphabet;

    /**
     * Instantiates a blank PDA
     */
    public PDA() {
        states = new ArrayList<>();
        stack = new Stack<>();

        stackAlphabet = new ArrayList<>();
        inputAlphabet = new ArrayList<>();
    }

    /**
     * Constructs PDA from string representation
     * @param input Given PDA representation
     * @return PDA from given string representation
     */
    public static PDA constructPDA(String input) {
        PDA pda = new PDA();
        Scanner scan = new Scanner(input);

        pda.name = scan.next();
        scan.nextLine();

        pda.addInputAlphabet(scan.nextLine().split("\\s"));

        pda.addStackAlphabet(scan.nextLine().split("\\s"));

        List<PDAState> states = new ArrayList<>();

        ArrayList<String> stateLines = new ArrayList<>();

        while (scan.hasNextLine()) {
            String stateIdentifier = scan.next();
            PDAState s1;
            s1 = new PDAState(pda);
            if (stateIdentifier.contains("*")) {
                stateIdentifier = stateIdentifier.substring(1, stateIdentifier.length());
                s1.setAcceptState(true);
            }
            s1.setStateIdentifier(stateIdentifier);
            states.add(s1);
            stateLines.add(scan.nextLine());
        }

        for (int i = 0; i < stateLines.size(); i++) {
            String stateLine = stateLines.get(i);
            PDAState pda1 = states.get(i);

            Scanner stateScanner = new Scanner(stateLine);
            int lineCount = 0;
            while (stateScanner.hasNext()) {
                String s = stateScanner.next();
                if (!s.equals("..")) {
                    String[] transitionSplit = s.split(",");
                    for (int i1 = 0; i1 < transitionSplit.length; i1++)
                        transitionSplit[i1] = transitionSplit[i1].trim();
                    for (int j = 0; j < transitionSplit.length; j+=2) {
                        PDAState.Transition newTransition = new PDAState.Transition(
                                pda.inputAlphabet.get(lineCount / pda.stackAlphabet.size()),
                                pda.stackAlphabet.get(lineCount % pda.stackAlphabet.size()),
                                transitionSplit[j+1], getStateByString(transitionSplit[j], states));
                        pda1.addTransition(newTransition);
                    }
                }
                lineCount += 1;
            }
            stateScanner.close();
        }
        pda.states = states;

        scan.close();
        return pda;
    }

    /**
     * Converts a CFG to a PDA given a CFG
     * @param cfg Given CFG
     * @return Converted PDA
     */
    public static PDA cfg2pda(CFG cfg) {
        PDA pda1 = new PDA();
        pda1.name = cfg.getName();
        pda1.inputAlphabet.addAll(cfg.getTermAlphabet());   //copy input alphabet and stack alphabet
        pda1.inputAlphabet.addAll(cfg.getVarAlphabet());
        pda1.stackAlphabet.addAll(cfg.getTermAlphabet());
        pda1.stackAlphabet.addAll(cfg.getVarAlphabet());
        pda1.stackAlphabet.add("$");
        List<PDAState> states = new ArrayList<>();

        PDAState qStart = new PDAState("qS", pda1); //new start state
        PDAState separate = new PDAState("s", pda1); //new separator to add S and $
        PDAState qLoop = new PDAState("qL", pda1); //new loop state
        PDAState qAccept = new PDAState("qA", pda1); //new accept state

        states.add(qStart);
        states.add(separate);
        states.add(qLoop);
        states.add(qAccept);

        qStart.addTransition("..", "..", "$", separate);
        separate.addTransition("..", "..", cfg.getStartRule(), qLoop);
        qLoop.addTransition("..", "$", "..", qAccept);

        for (CFG.Rule r : cfg.getRules()) { //for every rule, make a set of states and transitions that rotate to it
            for (String s : r.getRHS()) {
                String[] terms = CFG.getTerms(s);
                PDAState lastState = qLoop;
                for (int i = terms.length - 1; i >= 0; i--) {
                    PDAState newState;
                    if (i == 0) {
                        newState = qLoop;
                    } else {
                        newState = new PDAState(pda1);
                        states.add(newState);
                    }
                    lastState.addTransition("..", (i == terms.length - 1 ? r.getLHS() : ".."), terms[i], newState);
                    lastState = newState;
                }
            }
        }
        for(String s : cfg.getTermAlphabet()) {
            qLoop.addTransition(s, s, "..", qLoop); //add cfg term transitions
        }
        pda1.states = states;
        return pda1;
    }

    /**
     * Converts given pda to a cfg
     * @param pda Given PDA to convert
     * @return CFG representing given PDA
     */
    public static CFG pda2cfg(PDA pda) {
        CFG cfg1 = new CFG();
        cfg1.setName(pda.name);
        PDA copyPDA = constructPDA(pda.toString());
        //make a normalized PDA
        PDAState pdaStateAccept = new PDAState("qAccept", copyPDA); //only one accept state
        copyPDA.states.add(pdaStateAccept);
        pdaStateAccept.setAcceptState(true);

        PDAState pdaStateTemp = new PDAState("qTemp", copyPDA); //attach each accept state to temp, then temp to accept pops $
        copyPDA.states.add(pdaStateTemp);

        for(PDAState pdaS : copyPDA.states) {
            if(pdaS.isAcceptState()) {
                pdaS.setAcceptState(false);
                pdaS.addTransition("..", "..", "..", pdaStateTemp);
            }
        }

        PDAState pdaStateNewStart = new PDAState("qStart", copyPDA); //new start state to old start state
        pdaStateNewStart.addTransition("..", "..", "$", copyPDA.states.get(0));
        copyPDA.states.add(0, pdaStateNewStart);

        for(String s : copyPDA.stackAlphabet) {
            pdaStateTemp.addTransition("..", s, "..", pdaStateTemp); //pop all stack elements
        }
        pdaStateTemp.addTransition("..", "$", "..", pdaStateAccept);

        for(PDAState pdaState : copyPDA.states) {
            for (Iterator<PDAState.Transition> iterator = pdaState.getTransitions().iterator(); iterator.hasNext(); ) {
                PDAState.Transition transition = iterator.next();
                if ((!transition.getPop().equals("..")) && (!transition.getPush().equals(".."))) {
                    PDAState newTemp = new PDAState(copyPDA);
                    pdaState.removeTransition(transition);
                    pdaState.addTransition(transition.getInput(), transition.getPop(), "..", newTemp);
                    newTemp.addTransition("..", "..", transition.getPush(), transition.getNextState());
                    copyPDA.states.add(newTemp);
                }
            }
        }

        List<PDAState> states1 = copyPDA.states;

        List<String> list = new ArrayList<>();
        list.add("A" + 1 + "_" + states1.size());
        cfg1.setStartRule("S");
        cfg1.addRule(new CFG.Rule("S", list));
        /*
        for every quadruple pqrs, add aAr_sb if conditions specified by theorem is met
        for every triple pqr, add Ap_qAq_r to Ap_r
        for every Ap_p, add epsilon
         */
        for (int i = 1; i <= states1.size(); i++) {

            String temp = "A" + i + "_" + i;
            List<String> epsilonList = new ArrayList<>();
            epsilonList.add("..");
            CFG.Rule rule = cfg1.getRule(temp);
            rule.addRHS(epsilonList);
            cfg1.addRule(rule);
            for(int j = 1; j <= states1.size(); j++) {

                String temp2 = "A" + i + "_" + j;
                List<String> list2 = new ArrayList<>();
                for(int k = 1; k <= states1.size(); k++) {

                    list2.add("A" + i + "_" + k + "A" + k + "_" + j);
                    for (int l = 1; l <= states1.size(); l++) {

                        for(PDAState.Transition t1 : states1.get(i - 1).getTransitions()) {

                            for(PDAState.Transition t2 : states1.get(k - 1).getTransitions()) {
                                if(t1.getPop().equals("..")
                                        && t1.getNextState().equals(states1.get(j - 1))
                                        && t1.getPush().equals(t2.getPop())
                                        && t2.getNextState().equals(states1.get(l - 1))
                                        && t2.getPush().equals("..")
                                        ) {
                                    String temp3 = "A" + i + "_" + l;
                                    List<String> list3 = new ArrayList<>();
                                    list3.add(t1.getInput() + "A" + j + "_" + k + t2.getInput());
                                    CFG.Rule newRule = cfg1.getRule(temp3);
                                    rule.addRHS(list3);
                                    cfg1.addRule(newRule);
                                }
                            }
                        }
                    }
                }
                CFG.Rule rule1 = cfg1.getRule(temp2);
                rule1.addRHS(list2);
                cfg1.addRule(rule1);
            }
        }

        return cfg1;
    }

    /**
     * @param state PDAState to find, given unique identifier
     * @param stateList List of states to search in
     * @return State given a certain string identifier
     */
    public static PDAState getStateByString(String state, List<PDAState> stateList) {
        for (PDAState ps : stateList) {
            if (ps.getStateIdentifier().equals(state)) {
                return ps;
            }
        }
        return null;
    }

    /**
     * Adds element to list if not already in
     * @param c Collection to add element to
     * @param element The element to add
     * @param <T> Type of element to add
     */
    static <T> void addIfNotPresent(Collection<T> c, T element) {
        if (!c.contains(element) && !element.equals("")) {
            c.add(element);
        }
    }

    /**
     * Adds all elements to of array to list
     * @param c Collection to add elements to
     * @param array Array of elements to add
     * @param <T> Type of elements in array
     */
    static <T> void addIfNotPresent(Collection<T> c, T[] array) {
        for (T e : array) {
            addIfNotPresent(c, e);
        }
    }

    /**
     * Add elements of array to input alphabet
     * @param split Array of input alphabet
     */
    private void addInputAlphabet(String[] split) {
        addIfNotPresent(inputAlphabet, split);
    }

    /**
     * Add elements of array to stack alphabet
     * @param split Array of stack alphabet
     */
    private void addStackAlphabet(String[] split) {
        addIfNotPresent(stackAlphabet, split);
    }

    /**
     *
     * @return Stack alphabet of PDA
     */
    public List<String> getStackAlphabet() {
        return stackAlphabet;
    }

    /**
     * @return Input alphabet of PDA
     */
    public List<String> getInputAlphabet() {
        return inputAlphabet;
    }

    public String toString() {
        String result = name + "\n";
        result += "    ";
        List<String> inputAlphabet1 = getInputAlphabet();
        List<String> stackAlphabet1 = getStackAlphabet();
        for (int i = 0; i < inputAlphabet1.size() * stackAlphabet1.size(); i++) {
            String s = inputAlphabet1.get(i / stackAlphabet1.size());
            result += String.format("%-6s", s);
        }
        result += "\n";
        result += "    ";
        for (int i = 0; i < inputAlphabet1.size() * stackAlphabet1.size(); i++) {
            String s = stackAlphabet1.get(i % stackAlphabet1.size());
            result += String.format("%-6s", s);
        }
        result += "\n";
        for (PDAState pdaS : states) {
            result += pdaS + "\n";
        }
        return result;
    }

    /**
     * State of PDA
     */
    public static class PDAState {
        private static int counter = 0;
        private List<Transition> transitions;
        private String stateIdentifier;
        private PDA parentPDA;
        private boolean acceptState;

        /**
         * @param identifier Identifier of state
         * @param parentPDA Parent PDA of state
         */
        public PDAState(String identifier, PDA parentPDA) {
            this.stateIdentifier = identifier;
            this.parentPDA = parentPDA;
            this.transitions = new ArrayList<>();
            this.acceptState = false;
        }

        /**
         * @param parentPDA Parent PDA of state
         */
        public PDAState(PDA parentPDA) {
            this.parentPDA = parentPDA;
            this.stateIdentifier = ++counter + "";
            this.transitions = new ArrayList<>();
            this.acceptState = false;
        }

        /**
         * Adds a transition to the state
         * @param input Input character
         * @param pop Pop character
         * @param push Push character
         * @param nextState Next state in transition
         */
        public void addTransition(String input, String pop, String push, PDAState nextState) {
            addTransition(new Transition(input, pop, push, nextState));
        }

        /**
         * Adds a transition to the state
         * @param t Adds the transition to the list
         */
        public void addTransition(Transition t) {
            transitions.add(t);
        }

        /**
         * Removes a transition from the list of transitions
         * @param transition Transition to remove from the state's transitions
         */
        public void removeTransition(Transition transition) {
            transitions.remove(transition);
        }

        private List<Transition> getTransitions(String input, String pop) {
            List<Transition> list = new ArrayList<>();
            for (Transition t : transitions) {
                if (t.getInput().equals(input) && t.getPop().equals(pop)) {
                    list.add(t);
                }
            }
            return list;
        }

        /**
         * @return Returns the transitions of the state
         */
        public List<Transition> getTransitions() {
            return transitions;
        }

        /**
         * @return Whether the state is an accept state
         */
        public boolean isAcceptState() {
            return acceptState;
        }

        /**
         * @param b Set accept state to true/false
         */
        public void setAcceptState(boolean b) {
            this.acceptState = b;
        }

        /**
         * @return Returns the state's identifier
         *
         */
        public String getStateIdentifier() {
            return stateIdentifier;
        }

        /**
         * @param stateIdentifier Sets the identifier of the state
         */
        public void setStateIdentifier(String stateIdentifier) {
            this.stateIdentifier = stateIdentifier;
        }

        @Override
        public String toString() {
            String result = String.format("%-4s", (isAcceptState() ? "*" : "") + stateIdentifier);
            for (int i = 0; i < (parentPDA.getInputAlphabet().size() * parentPDA.getStackAlphabet().size()); i++) {
                List<Transition> tList = getTransitions(
                        parentPDA.getInputAlphabet().get(i / parentPDA.getStackAlphabet().size()),
                        parentPDA.getStackAlphabet().get(i % parentPDA.getStackAlphabet().size()));
                String temp = "";
                for (Transition t : tList) {
                    temp += t.getNextState().getStateIdentifier() + "," + t.getPush() + ",";
                }
                if (temp.isEmpty()) {
                    temp = "..";
                }
                if(temp.contains(",")) {
                    temp = temp.substring(0, temp.length() - 1);
                    temp+= " ";
                }
                result += String.format("%-6s", temp);
            }
            return result;
        }

        public static class Transition {
            private String input;
            private String pop;
            private String push;
            private PDAState nextState;

            /**
             * @param input Input character
             * @param pop Pop character
             * @param push Push character
             * @param nextState Next state of transition
             */
            public Transition(String input, String pop, String push, PDAState nextState) {
                this.input = input;
                this.pop = pop;
                this.push = push;
                this.nextState = nextState;
            }

            /**
             * @return Input character
             */
            public String getInput() {
                return input;
            }

            /**
             * @return Pop character
             */
            public String getPop() {
                return pop;
            }

            /**
             * @return Push character
             */
            public String getPush() {
                return push;
            }

            /**
             * @return Next state in the transition
             */
            public PDAState getNextState() {
                return nextState;
            }

            public String toString() {
                return input + ", " + pop + "->" + push + ", " + nextState.stateIdentifier;
            }
        }
    }
}
