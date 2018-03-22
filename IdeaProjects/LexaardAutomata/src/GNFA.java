import java.util.*;

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
            ArrayList<Regex> temp = new ArrayList<>();
            for (int i = 0; i < states.size(); i++) {
                temp.add(new Regex());
            }
            s.setNextStateTransitions(temp);
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
                states.get(i).setTransition(r1, k - 1);
            }
            i += 1;
        }

        gnfa.setAlphabet(alphabet);
        gnfa.setStates(states);

        return gnfa;
    }

    public static GNFA dfa2gnfa(Automaton dfa) {
        String[] temp = (dfa.toString()).split("\n");

        GNFA gnfa = new GNFA();
        List<String> alphabet = new ArrayList<>();

        Collections.addAll(alphabet, temp[1].split(" +"));
        alphabet.remove("");

        gnfa.setComment(dfa.getComment());
        gnfa.setAlphabet(alphabet);

        List<GNFAState> states = new ArrayList<>();

        GNFAState newStart = new GNFAState("s", true);
        GNFAState newEnd = new GNFAState("e", true);

        states.add(newStart);

        for (int i = 2; i < temp.length; i++) {
            Scanner sc = new Scanner(temp[i]);
            String sID = sc.next();
            if (sID.contains("*")) sID = sID.substring(1, sID.length());
            GNFAState gs1 = new GNFAState(sID);
            states.add(gs1);
        }

        states.add(newEnd);

        for (GNFAState s : states) {
            ArrayList<Regex> temp1 = new ArrayList<>();
            for (int i = 0; i < states.size(); i++) {
                temp1.add(new Regex());
            }
            s.setNextStateTransitions(temp1);
        }

        for (int i = 2; i < temp.length; i++) {
            String[] transitionIDs = temp[i].split(" +");
            transitionIDs = Arrays.copyOfRange(transitionIDs, 1, transitionIDs.length);
            for (int j = 0; j < transitionIDs.length; j++) {
                Regex r1 = new Regex(alphabet.get(j));
                int k;
                for (k = 0; k < states.size(); k++) {
                    GNFAState gs1 = getStateFromString(states, transitionIDs[j]);
                    if (gs1.compareTo(states.get(k)) == 0)
                        break;
                }
                states.get(i - 1).setTransition(r1, k);
            }
        }
        states.get(0).setTransition(new Regex(Regex.EPSILONCHARACTER + ""), 1);
        for (int i = 0; i < dfa.getStates().size(); i++) {
            if (dfa.getStates().get(i).isAcceptState()) {
                GNFAState gs1 = getStateFromString(states, dfa.getStates().get(i).getStateID());
                gs1.setTransition(new Regex(Regex.EPSILONCHARACTER + ""), states.size() - 1);
            }
        }
        gnfa.setStates(states);
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

    private static GNFAState getStateFromString(List<GNFAState> states, String sta) {
        for (GNFAState st : states) {
            if (st.getStateID().equals(sta)) {
                return st;
            }
        }
        return null;
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

    public boolean hasValidStates() {
        for (GNFAState gs : states) {
            if (!gs.isPHState())
                return true;
        }
        return false;
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
