import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Regex {
    protected final static char EPSILONCHARACTER = 'ε';
    protected final static char EMPTYSETCHARACTER = '∅';

    private String prefixCode;
    private String infixCode;

    private Automaton fsaRepre;

    private Stack<Automaton> stackNfa;
    private Stack<Character> stackOps;

    public Regex() {
        infixCode = null;
        prefixCode = null;
        //will throw error on convertfsa
    }

    public Regex(String pre) {
        this.prefixCode = pre;
        new RegexParser(pre);
        stackNfa = new Stack<>();
        stackOps = new Stack<>();
    }

    public Regex(String actualRegex, int filler) {
        this.infixCode = actualRegex;
        stackNfa = new Stack<>();
        stackOps = new Stack<>();
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
            List<Regex> ownRegex = new ArrayList<>();
            for (int j = 0; j < gnfa1.getStates().size(); j++) {
                Regex r1 = gnfa1.getStates().get(j).getNextStateTransitions().remove(i - 1);
                ownRegex.add(r1);
            }
            GNFAState st = gnfa1.getStates().remove(i - 1);
            i -= 1;
        }
        return null;
    }

    public void concatReg(Regex newReg) {
        infixCode += "." + newReg.infixCode;
    }

    public void unionReg(Regex newReg) {
        infixCode += "|" + newReg.infixCode;
    }

    private void doOp() {
        if (stackOps.size() > 0) {
            char charAt = stackOps.pop();
            Automaton temp;
            switch (charAt) {
                case ('|'):
                    temp = stackNfa.pop();
                    stackNfa.push(Automaton.nfaConcat(stackNfa.pop(), temp));
                    break;
                case ('.'):
                    temp = stackNfa.pop();
                    stackNfa.push(Automaton.nfaConcat(stackNfa.pop(), temp));
                    break;
                case ('*'):
                    stackNfa.push(Automaton.nfaStar(stackNfa.pop()));
                    break;
                default:
                    System.err.println("Unknown symbol");
                    break;
            }
        }
    }

    private boolean precedence(char first, char second) {
        return first == second || first != '*' && (second == '*' || first != '.' && (second == '.' || first != '|'));
    }

    public Automaton convertFSA() {
        if (!isValidRegex()) {
            return null;
        }

        stackNfa.clear();
        stackOps.clear();

        for (int i = 0; i < infixCode.length(); i++) {
            if (isInputCharacter(infixCode.charAt(i))) {
                addNfa(infixCode.charAt(i));
            } else if (stackOps.isEmpty()) {
                stackOps.push(infixCode.charAt(i));
            } else if (infixCode.charAt(i) == '(') {
                stackOps.push(infixCode.charAt(i));
            } else if (infixCode.charAt(i) == ')') {
                while (stackOps.get(stackOps.size() - 1) != '(') {
                    doOp();
                }
                stackOps.pop();
            } else {
                while (!stackOps.isEmpty() &&
                        precedence(infixCode.charAt(i), stackOps.get(stackOps.size() - 1))) {
                    doOp();
                }
                stackOps.push(infixCode.charAt(i));
            }
        }
        while (!stackOps.isEmpty()) doOp();

        Automaton completeNFA = stackNfa.pop();

        completeNFA.getStates().get(completeNFA.getStates().size() - 1).setAcceptState(true);

        return completeNFA;
    }

    private void addNfa(char c) {
        State s0 = new State(false);
        State s1 = new State(true);

        Automaton nfa = new Automaton();
        if (c == EPSILONCHARACTER) {
            s0.addTransition(s1, "..");
            nfa.addAlphabet("..");
        } else if (c == EMPTYSETCHARACTER) {
            //do nothing
        } else {
            s0.addTransition(s1, c + "");

            nfa.addAlphabet(c);
            nfa.addAlphabet("..");
        }

        nfa.getStates().add(s0);
        nfa.getStates().add(s1);

        nfa.setStartState(s0);

        stackNfa.add(nfa);
    }

    private boolean isInputCharacter(char c) {
        return ((int) c >= (int) 'a' && (int) c <= (int) 'z') || ((int) c >= (int) '0' && (int) c <= '9');
    }

    private boolean isOperand(char c) {
        return c == '*' || c == '.' || c == '|';
    }

    private boolean isOtherCharacter(char c) {
        return c == EPSILONCHARACTER || c == EMPTYSETCHARACTER;
    }

    private boolean isValidRegex() {
        for (int i = 0; i < infixCode.length(); i++) {
            char c = infixCode.charAt(i);
            if (!(isInputCharacter(c) || isOperand(c) || isOtherCharacter(c) || c == '(' || c == ')'))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return (prefixCode != null ? prefixCode : "..");
    }

    private class RegexParser {

        public RegexParser(String input) {
            infixCode = parseInput(input);
        }

        private String parseInput(String input) {
            while (!input.isEmpty()) {
                if (input.charAt(0) == '(') {
                    switch (input.substring(1, 3)) {
                        case "r*":
                            return "(" + parseInput(findInputString(input)) + ")*";
                        case "r|":
                            return "(" + parseInputUnion(findInputString(input)) + ")";
                        case "r.":
                            return "(" + parseInputConcat(findInputString(input)) + ")";
                    }
                } else {
                    switch (input) {
                        case "r.":
                            return EPSILONCHARACTER + "";
                        case "r/":
                            return EMPTYSETCHARACTER + "";
                        default:
                            return input;
                    }
                }
            }
            return "";
        }

        private String parseInputUnion(String foo) {
            ArrayList<String> concatInput = getSplitInput(foo);
            String ret = "";
            for (String s : concatInput) {
                ret += parseInput(s) + "|";
            }
            ret = ret.substring(0, ret.length() - 1);

            return ret;
        }

        private String parseInputConcat(String foo) {
            ArrayList<String> concatInput = getSplitInput(foo);
            String ret = "";
            for (String s : concatInput) {
                ret += parseInput(s) + ".";
            }
            ret = ret.substring(0, ret.length() - 1);

            return ret;
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
