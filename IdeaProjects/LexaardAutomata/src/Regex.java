import java.util.ArrayList;
import java.util.Stack;

public class Regex {
    private String code;
    private Automaton fsaRepre;

    private Stack<Automaton> stackNfa;
    private Stack<Character> stackOps;

    public Regex(String pre) {
        new RegexParser(pre);
        stackNfa = new Stack<>();
        stackOps = new Stack<>();
    }

    public Regex(String actualRegex, int filler) {
        this.code = actualRegex;
        stackNfa = new Stack<>();
        stackOps = new Stack<>();
    }

    public boolean match(String input) {
        return fsaRepre.run(input).equals("accept");
    }

    public Automaton convertFSA() {
        stackNfa.clear();
        stackOps.clear();

        for (int i = 0; i < code.length(); i++) {
            if (isInputCharacter(code.charAt(i))) {
                addNfa(code.charAt(i));
            } else if (stackOps.isEmpty()) {
                stackOps.push(code.charAt(i));
            } else if (code.charAt(i) == '(') {
                stackOps.push(code.charAt(i));
            } else if (code.charAt(i) == ')') {
                while (stackOps.get(stackOps.size() - 1) != '(') {
                    doOp();
                }
                stackOps.pop();
            } else {
                while (!stackOps.isEmpty() &&
                        precedence(code.charAt(i), stackOps.get(stackOps.size() - 1))) {
                    doOp();
                }
                stackOps.push(code.charAt(i));
            }
        }
        while (!stackOps.isEmpty()) doOp();

        Automaton completeNFA = stackNfa.pop();

        completeNFA.getStates().get(completeNFA.getStates().size() - 1).setAcceptState(true);

        return completeNFA;
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

    private void addNfa(char c) {
        State s0 = new State(false);
        State s1 = new State(true);
        s0.addTransition(s1, c + "");

        Automaton nfa = new Automaton();
        nfa.addAlphabet(c);
        nfa.addAlphabet("..");

        nfa.getStates().add(s0);
        nfa.getStates().add(s1);

        nfa.setStartState(s0);

        stackNfa.add(nfa);
    }

    private boolean isInputCharacter(char c) {
        return (int) c >= (int) 'a' && (int) c <= (int) 'z';
    }

    private String concatExpand(String code) {
        String ret = "";
        for (int i = 0; i < code.length() - 1; i++) {
            if (isInputCharacter(code.charAt(i)) && isInputCharacter(code.charAt(i + 1))) {
                ret += code.charAt(i) + ".";

            } else if (isInputCharacter(code.charAt(i)) && code.charAt(i + 1) == '(') {
                ret += code.charAt(i) + ".";

            } else if (code.charAt(i + 1) == ')' && isInputCharacter(code.charAt(i))) {
                ret += code.charAt(i) + ".";

            } else if (code.charAt(i + 1) == '*' && code.charAt(i + 1) == '(') {
                ret += code.charAt(i) + ".";

            } else if (code.charAt(i + 1) == '*' && isInputCharacter(code.charAt(i))) {
                ret += code.charAt(i) + ".";

            } else if (code.charAt(i + 1) == ')' && code.charAt(i + 1) == '(') {
                ret += code.charAt(i) + ".";

            } else
                ret += code.charAt(i);

        }
        ret += code.charAt(ret.length() - 1);
        return ret;
    }

    @Override
    public String toString() {
        return code;
    }

    private class RegexParser {

        public RegexParser(String input) {
            code = parseInput(input);
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
                            return "ε";
                        case "r/":
                            return "∅";
                        default:
                            return input;
                    }
                }
            }
            return "a";
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
                        int position = getEmbeddedParenthesesLength(temp);
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

        private int getEmbeddedParenthesesLength(String input) {
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

        private String findInputString(String input) {
            return input.substring(4, input.length() - 1);
        }
    }
}
