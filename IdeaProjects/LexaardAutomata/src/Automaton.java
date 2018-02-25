import java.util.*;

public class Automaton {
    private final int SPACE = -20;
    private ArrayList<String> alphabet;
    private List<State> states;
    private String comment;

    public Automaton() {
        this.states = new LinkedList<>();
        this.comment = "";
        this.alphabet = new ArrayList<>();
    }

    public Automaton(LinkedList<State> states) {
        this.states = states;
        this.comment = "";
        this.alphabet = new ArrayList<>();
    }

    public Automaton(Automaton other) {
        this.alphabet = other.getAlphabet();
        this.states = other.getStates();
        this.comment = other.getComment();
    }

    public String run(String input) {
        ArrayList<State> currentState = new ArrayList<>();
        ArrayList<State> nextStates = new ArrayList<>();

        currentState.add(states.get(0));

        Scanner scan = new Scanner(input);

        for (char c : input.toCharArray()) {
            for (State s : currentState) {
                ArrayList<State> current = s.getNextState().get(Character.toString(c));
                if (current != null) {
                    for (State st : s.getNextState().get(Character.toString(c))) {
                        if (st != null) {
                            nextStates.add(st);
                        }
                    }
                }
                if (!s.getAllTransitions("..").isEmpty()) {
                    nextStates.addAll(s.getAllTransitions(".."));
                }
            }
            currentState.clear();
            currentState.addAll(nextStates);
            nextStates.clear();
        }
        for (State s : currentState)
            if (s.isAcceptState())
                return "accept";

        return "reject";
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        String result = String.format("%" + SPACE + "s", "");
        for (String anAlphabet : alphabet) {
            result += String.format("%" + SPACE + "s", anAlphabet);
        }
        result += "\n";
        for (State state : states) {
            Map<String, ArrayList<State>> nextState = state.getNextState();
            result += String.format("%" + SPACE + "s", (state.isAcceptState() ? "*" : "") + state.getStateID());
            for (String anAlphabet : alphabet) {
                String str = "";
                ArrayList<State> stateTransitions = nextState.get(anAlphabet);
                if (stateTransitions == null) {
                    result += String.format("%" + SPACE + "s", "..");
                    continue;
                }
                for (State st : stateTransitions) {
                    str += st.getStateID() + ",";
                }
                str = str.substring(0, str.length() - 1);
                result += String.format("%" + SPACE + "s", str);
            }
            result += "\n";
        }
        return result;
    }

    public boolean isDeterministic() {
        if (alphabet.contains("..")) {
            return false;
        }
        for (String str : alphabet) {
            for (State s : states) {
                ArrayList<State> current = s.getNextState().get(str);
                if (current != null && current.size() > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(ArrayList<String> alphabet) {
        this.alphabet = alphabet;
    }

    public void sortStates() {
        states = Interpreter.asSortedList(states);
    }
}
