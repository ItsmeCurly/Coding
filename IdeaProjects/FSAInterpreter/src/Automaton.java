import java.util.ArrayList;
import java.util.List;

public class Automaton {
    private String desc;
    private List<Character> alphabet = new ArrayList<Character>();
    private ArrayList<ArrayList<String>> states = new ArrayList<>();
    private ArrayList<String> accept = new ArrayList<>();

    public Automaton() {
        this("", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public Automaton(String desc, List<Character> alphabet, ArrayList<ArrayList<String>> transitions, ArrayList<String> accept) {
        this.desc = desc;
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

    public List<Character> getAlphabet() {
        return this.alphabet;
    }

    public void setAlphabet(List<Character> alphabet) {
        this.alphabet = alphabet;
    }

    public void addAlphabet(char c) {
        this.alphabet.add(c);
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ArrayList<ArrayList<String>> getTransitions() {
        return this.states;
    }

    public void setTransitions(ArrayList<ArrayList<String>> transitions) {
        this.states = transitions;
    }

    public ArrayList<String> getAccept() {
        return this.accept;
    }

    public void setAccept(ArrayList<String> accept) {
        this.accept = accept;
    }

    public void addAccept(String s) {
        this.accept.add(s);
    }

    @Override
    public String toString() {
        return "Automata{" +
                "desc='" + this.desc + '\'' +
                ", alphabet=" + this.alphabet +
                ", transitions=" + this.states +
                ", accept=" + this.accept +
                '}';
    }


//    public static class Transition {
//        private String start;
//        private String character;
//        private String end;
//        public Transition(String start, String character, String end) {
//
//        }
//    }

}
