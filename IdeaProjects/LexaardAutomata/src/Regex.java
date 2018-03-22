import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Regex {
    public static final String EPSILONCHARACTER = "r.";
    public static final String EMPTYSETCHARACTER = "r/";

    private String code;
    private List<Regex> nextRegex;
    private boolean isOperand;

    private Automaton fsaRepre;

    public Regex() {
        code = "..";
    }

    public Regex(String pre) {
        nextRegex = new ArrayList<>();
        new RegexParser(pre);
    }

    public boolean match(String input) {
        return fsaRepre.run(input).equals("accept");
    }

    public static Regex dfa2Regex(Automaton fsa) {
        //will convert if is nfa, if is already dfa will stay the same
        Automaton dfa = Automaton.nfa2Dfa(fsa);
        GNFA gnfa1 = GNFA.dfa2gnfa(dfa);
        int i = gnfa1.getStates().size() - 1;
        while (gnfa1.hasValidStates()) {
            List<Regex> ownRegexes = new ArrayList<>();
            for (int j = 0; j < gnfa1.getStates().size(); j++) {
                Regex r1 = gnfa1.getStates().get(j).getNextStateTransitions().remove(i - 1);
                ownRegexes.add(r1);
            }
            GNFAState st = gnfa1.getStates().remove(i - 1);
            i -= 1;
        }
        return null;
    }

    public Automaton convertFSA() {
        if (isOperand) {
            switch (code) {
                case "r*":
                    return Automaton.nfaStar(nextRegex.get(0).convertFSA());
                case "r|": {
                    Stack<Automaton> fsarep = new Stack<>();
                    for (Regex r : nextRegex) {
                        Automaton a1 = r.convertFSA();
                        fsarep.push(a1);
                    }
                    while (fsarep.size() > 1) {
                        Automaton a1 = fsarep.pop();
                        Automaton a0 = fsarep.pop();

                        Automaton newA = Automaton.nfaUnion(a0, a1);
                        fsarep.push(newA);
                    }
                    return fsarep.pop();
                }
                case "r.": {
                    Stack<Automaton> fsarep = new Stack<>();
                    for (Regex r : nextRegex) {
                        Automaton a1 = r.convertFSA();
                        fsarep.push(a1);
                    }
                    while (fsarep.size() > 1) {
                        Automaton a1 = fsarep.pop();
                        Automaton a0 = fsarep.pop();

                        Automaton newA = Automaton.nfaConcat(a0, a1);
                        fsarep.push(newA);
                    }
                    return fsarep.pop();
                }
            }
        }
        return addNfa(code);
    }

    private Automaton addNfa(String s) {
        State s0 = new State(false);
        State s1 = new State(true);

        Automaton nfa = new Automaton();

        switch (s) {
            case "r.":
                s0.addTransition(s1, "..");
                nfa.addAlphabet("..");
                break;
            case "r/":
                //do nothing
                break;
            default:
                s0.addTransition(s1, s);

                nfa.addAlphabet(s);
                nfa.addAlphabet("..");
                break;
        }

        nfa.getStates().add(s0);
        nfa.getStates().add(s1);

        nfa.setStartState(s0);

        return nfa;
    }

    @Override
    public String toString() {
        String result = "";
        if (isOperand) {
            result += "(" + code + " ";
            for (Regex r : nextRegex) {
                result += r.toString() + " ";
            }
            result += ")";
        } else {
            return code;
        }
        return result;
    }

    private class RegexParser {

        public RegexParser(String input) {
            parseInput(input);
        }

        private void parseInput(String input) {
            if (input.charAt(0) == '(') {
                ArrayList<String> temp;
                switch (input.substring(1, 3)) {
                    case "r*":
                        isOperand = true;
                        code = "r*";
                        temp = getSplitInput(findInputString(input));
                        for (String s : temp) {
                            nextRegex.add(new Regex(s));
                        }
                        break;
                    case "r|":
                        isOperand = true;
                        code = "r|";
                        temp = getSplitInput(findInputString(input));
                        for (String s : temp) {
                            nextRegex.add(new Regex(s));
                        }
                        break;
                    case "r.":
                        isOperand = true;
                        code = "r.";
                        temp = getSplitInput(findInputString(input));
                        for (String s : temp) {
                            nextRegex.add(new Regex(s));
                        }
                        break;
                }
            } else {
                switch (input) {
                    case "r.":
                        isOperand = false;
                        code = "r.";
                        break;
                    case "r/":
                        isOperand = false;
                        code = "r/";
                        break;
                    default:
                        isOperand = false;
                        code = input;
                        break;
                }
            }
        }

        private ArrayList<String> getSplitInput(String input) {
            ArrayList<String> concatInput = new ArrayList<>();
            String temp = input;
            while (!temp.isEmpty()) {
                switch (temp.charAt(0)) {
                    case '(':
                        int position = GNFA.getEmbeddedParenthesesLength(temp);
                        concatInput.add(temp.substring(0, position));
                        if (temp.length() == position) {
                            temp = "";
                        } else {
                            temp = temp.substring(position + 1);
                        }
                        break;
                    default:
                        concatInput.add(temp.substring(0, 1));
                        if (temp.length() > 1) {
                            temp = temp.substring(2);
                        } else
                            temp = "";
                        break;
                }
            }
            return concatInput;
        }

        private String findInputString(String input) {
            return input.substring(4, input.length() - 1);
        }
    }
}
