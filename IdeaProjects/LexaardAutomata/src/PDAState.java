import java.util.ArrayList;
import java.util.List;

public class PDAState {
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
