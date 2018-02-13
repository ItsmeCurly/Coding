import java.util.ArrayList;
import java.util.List;

public class Automaton {
    private String comment;
    private List<Character> alphabet = new ArrayList<Character>();
    private ArrayList<ArrayList<String>> states = new ArrayList<>();
    private ArrayList<String> accept = new ArrayList<>();

    public Automaton() {
        this("", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public Automaton(String comment, List<Character> alphabet, ArrayList<ArrayList<String>> transitions, ArrayList<String> accept) {
        this.comment = comment;
        this.alphabet = alphabet;
        this.states = transitions;
        this.accept = accept;
    }

    public String run(String s) {
        ArrayList<String> currentState = this.states.get(0);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int index = this.alphabet.indexOf(c);
            String sta = currentState.get(index + 1);
            for (ArrayList<String> state : this.states) {
                if (state.get(0).equals(sta)) {
                    currentState = state;
                }
            }
        }
        for (String acceptState : accept) {
            if (currentState.get(0).equals(acceptState)) {
                return "accept";
            }
        }
        return "reject";
    }

    public void addAlphabet(char c) {
        this.alphabet.add(c);
    }

    public void setDesc(String desc) {
        this.comment = desc;
    }

    public void setTransitions(ArrayList<ArrayList<String>> transitions) {
        this.states = transitions;
    }

    public void addAccept(String s) {
        this.accept.add(s);
    }

    @Override
    public String toString() {
        return this.comment + '\n' + formattedTable();
    }

    private String formattedTable() {
        String defaultSpace = "  ";
        String outputTable = "";
        ArrayList<String> strings = new ArrayList<>();
        String _temp = "";
        for (char c : alphabet) {

        }

        for (int i = 0; i < states.size(); i++) {
            String temp = "";
            ArrayList<String> stateTransitions = states.get(i);
            String state = stateTransitions.get(0);

        }
        return outputTable;
    }
}