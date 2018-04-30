import java.util.*;

public class PDA {
    private List<PDAState> states;
    private Stack<String> stack;

    private String name;

    private List<String> stackAlphabet;

    private List<String> inputAlphabet;

    /**
     *
     */
    public PDA() {
        states = new ArrayList<>();
        stack = new Stack<>();

        stackAlphabet = new ArrayList<>();
        inputAlphabet = new ArrayList<>();
    }

    /**
     * @param input
     * @return
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
            s1 = new PDAState();
            if (stateIdentifier.contains("*")) {
                stateIdentifier = stateIdentifier.substring(1, stateIdentifier.length());
                s1.setAcceptState(true);
            }
            s1.setStateIdentifier(stateIdentifier);
            states.add(s1);
            s1.setParentPDA(pda);
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

                    PDAState.Transition newTransition = new PDAState.Transition(
                            pda.inputAlphabet.get(lineCount / pda.inputAlphabet.size()),
                            pda.stackAlphabet.get(lineCount % pda.stackAlphabet.size()),
                            transitionSplit[1], getStateByString(transitionSplit[0], states));
                    pda1.addTransition(newTransition);
                }
                lineCount += 1;
            }
            stateScanner.close();
        }
        pda.states = states;


        scan.close();
        System.out.println(pda);
        return pda;
    }

    public static PDA cfg2pda(CFG cfg) {
        PDA pda1 = new PDA();
        List<PDAState> states = new ArrayList<>();

        PDAState qStart = new PDAState("qStart");
        PDAState separate = new PDAState();
        PDAState qLoop = new PDAState("qLoop");
        PDAState qAccept = new PDAState("qAccept");

        states.add(qStart);
        states.add(separate);
        states.add(qLoop);
        states.add(qAccept);

        qStart.addTransition("..", "..", "$", separate);
        qStart.addTransition("..", "..", cfg.getStartRule(), qLoop);
        qLoop.addTransition("..", "$", "..", qAccept);

        for (CFG.Rule r : cfg.getRules()) {
            for (String s : r.getRHS()) {
                String[] terms = CFG.getTerms(s);
                PDAState lastState = qLoop;
                for (int i = terms.length - 1; i >= 0; i--) {
                    PDAState newState;
                    if (i == 0) {
                        newState = qLoop;
                    } else {
                        newState = new PDAState();
                        states.add(newState);
                    }
                    lastState.addTransition("..", (i == terms.length - 1 ? r.getLHS() : ".."), terms[i], newState);
                    lastState = newState;
                }
            }
        }
        return pda1;
    }

    public static CFG pda2cfg(PDA pda) {
        CFG cfg1 = new CFG();

        return cfg1;
    }

    /**
     * @param state
     * @param stateList
     * @return
     */
    public static PDAState getStateByString(String state, List<PDAState> stateList) {
        for (PDAState ps : stateList) {
            if (ps.getStateIdentifier().equals(state)) {
                return ps;
            }
        }
        return null;
    }

    static <T> void addIfNotPresent(Collection<T> c, T element) {
        if (!c.contains(element) && !element.equals("")) {
            c.add(element);
        }
    }

    static <T> void addIfNotPresent(Collection<T> c, T[] array) {
        for (T e : array) {
            addIfNotPresent(c, e);
        }
    }

    private void addInputAlphabet(String[] split) {
        addIfNotPresent(inputAlphabet, split);
    }

    private void addStackAlphabet(String[] split) {
        addIfNotPresent(stackAlphabet, split);
    }

    /**
     * @return
     */
    public List<String> getStackAlphabet() {
        return stackAlphabet;
    }

    /**
     * @return
     */
    public List<String> getInputAlphabet() {
        return inputAlphabet;
    }

    public String toString() {
        String result = name + "\n";
        result += "    ";
        List<String> inputAlphabet1 = getInputAlphabet();
        List<String> stackAlphabet1 = getStackAlphabet();
        for (int i = 0; i < inputAlphabet1.size() * getStackAlphabet().size(); i++) {
            String s = inputAlphabet1.get(i / inputAlphabet1.size());
            result += String.format("%-10s", s);
        }
        result += "\n";
        result += "    ";
        for (int i = 0; i < inputAlphabet1.size() * getStackAlphabet().size(); i++) {
            String s = stackAlphabet1.get(i % stackAlphabet1.size());
            result += String.format("%-10s", s);
        }
        result += "\n";
        for (PDAState pdaS : states) {
            result += pdaS + "\n";
        }
        return result;
    }

    public static class PDAState {
        private static int counter = 0;
        private List<Transition> transitions;
        private String stateIdentifier;
        private PDA parentPDA;
        private boolean acceptState;

        /**
         *
         */
        public PDAState() {
            this.transitions = new ArrayList<>();
            this.stateIdentifier = ++counter + "";
            this.acceptState = false;
        }

        /**
         * @param identifier
         */
        public PDAState(String identifier) {
            this.stateIdentifier = identifier;
        }

        public void addTransition(String input, String pop, String push, PDAState nextState) {
            addTransition(new Transition(input, pop, push, nextState));
        }

        /**
         * @param t
         */
        public void addTransition(Transition t) {
            transitions.add(t);
        }

        /**
         * @return
         */
        public List<Transition> getTransitions() {
            return transitions;
        }

        /**
         * @return
         */
        public boolean isAcceptState() {
            return acceptState;
        }

        /**
         * @param b
         */
        public void setAcceptState(boolean b) {
            this.acceptState = b;
        }

        /**
         * @return
         */
        public String getStateIdentifier() {
            return stateIdentifier;
        }

        /**
         * @param stateIdentifier
         */
        public void setStateIdentifier(String stateIdentifier) {
            this.stateIdentifier = stateIdentifier;
        }

        @Override
        public String toString() {
            String result = String.format("%-4s", (isAcceptState() ? "*" : "") + stateIdentifier);
            for (int i = 0; i < (parentPDA.getInputAlphabet().size() * parentPDA.getStackAlphabet().size()); i++) {
                List<Transition> tList = getTransitions(
                        parentPDA.getInputAlphabet().get(i / parentPDA.getInputAlphabet().size()),
                        parentPDA.getStackAlphabet().get(i % parentPDA.getStackAlphabet().size()));
                String temp = "";
                for (Transition t : tList) {
                    temp += t.getNextState().getStateIdentifier() + "," + t.getPush() + " ";
                }
                if (temp.isEmpty()) {
                    temp = "..";
                }
                result += String.format("%-10s", temp);
            }
            return result;
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

        public void setParentPDA(PDA pda) {
            this.parentPDA = pda;
        }

        public static class Transition {
            private String input;
            private String pop;
            private String push;
            private PDAState nextState;

            public Transition(String input, String pop, String push, PDAState nextState) {
                this.input = input;
                this.pop = pop;
                this.push = push;
                this.nextState = nextState;
            }

            public String getInput() {
                return input;
            }

            public void setInput(String input) {
                this.input = input;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPush() {
                return push;
            }

            public void setPush(String push) {
                this.push = push;
            }

            public PDAState getNextState() {
                return nextState;
            }

            public void setNextState(PDAState nextState) {
                this.nextState = nextState;
            }

            public String toString() {
                return input + ", " + pop + "->" + push + ", " + nextState.stateIdentifier;
            }
        }
    }
}
