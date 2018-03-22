import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GNFA {
    private String comment;
    private List<GNFAState> states;
    private List<String> alphabet;

    /**
     *
     */
    public GNFA() {
        this("");
    }

    /**
     * @param comment
     */
    public GNFA(String comment) {
        this.comment = comment;
        this.states = new ArrayList<>();
        this.alphabet = new ArrayList<>();
    }

    /**
     * @param states
     */
    public GNFA(List<GNFAState> states) {
        this.states = states;
        this.alphabet = alphabet;
        this.comment = "";
    }

    /**
     * @param states
     * @param alphabet
     * @param comment
     */
    public GNFA(List<GNFAState> states, List<String> alphabet, String comment) {
        this.states = states;
        this.comment = comment;
        this.alphabet = alphabet;
    }

    /**
     * @param other
     */
    public GNFA(GNFA other) {
        constructGNFA(other.toString());
    }

    public static GNFA constructGNFA(String input) {
        GNFA gnfa = new GNFA();
        Scanner scan = new Scanner(input);

        Scanner line = new Scanner(scan.nextLine());
        //set comment of GNFA
        gnfa.setComment(line.next());

        //get states
        List<String> alphabet = new ArrayList<>();

        Scanner line2 = new Scanner(scan.nextLine());

        while (line2.hasNext()) {
            alphabet.add(line2.next());
        }

        List<GNFAState> states = new ArrayList<>();

        line = new Scanner(scan.nextLine());

        while (line.hasNext()) {
            GNFAState st = new GNFAState(line.next());
            states.add(st);
        }
        for (GNFAState s : states) {
            s.setNextStateTransitions(new ArrayList<>(states.size()));
        }

        int i = 0;

        while (scan.hasNextLine()) {
            String temp = scan.nextLine();
            String[] regexes = temp.split(" {2,}");

            for (int k = 1; k < regexes.length; k++) {
                Regex r1;
                if (regexes[k].equals("..")) {
                    r1 = new Regex();
                } else {
                    r1 = new Regex(regexes[k]);
                }
                states.get(i).addTransition(r1);
            }
            i += 1;
        }
        gnfa.setAlphabet(alphabet);
        gnfa.setStates(states);
        gnfa.getStates().get(gnfa.getStates().size() - 1).setAcceptState(true);
        return gnfa;
    }

    /**
     * @param input
     * @return
     */
    public static int getEmbeddedParenthesesLength(String input) {
        int openParenCounter = 0;
        int closeParenCounter = 0;
        int position = 0;
        do {
            if (input.charAt(position) == '(') {
                openParenCounter += 1;
            } else if (input.charAt(position) == ')') {
                closeParenCounter += 1;
            }
            position += 1;
        } while (openParenCounter != closeParenCounter && position != input.length());
        return position;
    }

    public String run(String input) {
//        List<State> currentState = new ArrayList<>();
//        List<State> nextStates = new ArrayList<>();
//
//        currentState.add(getStartState());
//
//        for (char c : input.toCharArray()) {
//
//        }
//        for (State s : currentState)
//            if (s.isAcceptState())
//                return "accept";

        return "reject";
    }

    /**
     * @return
     */
    public List<String> getAlphabet() {
        return alphabet;
    }

    /**
     * @param alphabet
     */
    public void setAlphabet(List<String> alphabet) {
        this.alphabet = alphabet;
    }

    /**
     * @return
     */
    public List<GNFAState> getStates() {
        return states;
    }

    /**
     * @param states
     */
    public void setStates(List<GNFAState> states) {
        this.states = states;
    }

    /**
     * @return
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toString() {
        String result = comment + '\n';
        for (String al : alphabet) {
            result += String.format("%-5s", al);
        }
        result += String.format("\n%-8s", "");
        for (GNFAState gs : states) {
            result += String.format("%" + Automaton.SPACE + "s", gs.getStateID());
        }
        result += '\n';
        for (GNFAState gs : states) {
            result += gs.toString() + '\n';
        }
        return result;
    }
}
