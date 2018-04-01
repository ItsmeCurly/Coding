import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Regex {
    public static final String EPSILONCHARACTER = "r.";
    public static final String EMPTYSETCHARACTER = "r/";

    private String code;    //tree representation
    private List<Regex> nextRegex;
    private boolean isOperand;

    private Automaton fsaRepre;

    /**
     * Default constructor that sets the regex to .., denoting a null regular expression
     */
    public Regex() {
        code = "..";
    }

    /**
     * @param pre The prefix code of the regular expression, denoted by the homework's syntax
     */
    public Regex(String pre) {
        nextRegex = new ArrayList<>();
        new RegexParser(pre);
    }

    /**
     * Checks whether the certain input string matches the regex's code, will be run on the FSA Representation and will output the desired outcome
     * @param input The input string to check whether the regex matches it
     * @return T/F whether the candidate string is a match with the regex string
     */
    public boolean match(String input) {
        if(fsaRepre == null) {
            fsaRepre = convertFSA();
        }
        return fsaRepre.run(input).equals("accept");
    }

    /**
     * Converts a dfa into a regular expression(not completed)
     * @param fsa The FSA to convert into a regex(will be auto-converted into a DFA)
     * @return The regex that this dfa represents
     */
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

    /**
     * @return FSA of the regex's code, utilizing its code and an ArrayList of the regex's that follow the operand(or code)
     */
    public Automaton convertFSA() {
        if (isOperand) {
            switch (code) {
                case "r*":
                    return Automaton.nfaStar(nextRegex.get(0).convertFSA());
                case "r|": {
                    Stack<Automaton> fsarep = new Stack<>();
                    for (Regex r : nextRegex) {
                        Automaton a1 = r.convertFSA();  //create new regex and push for union
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
                case "r.": {    //parse and concat all new regex
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
        return addNfa(code);    //create new nfa with certain transition, base case
    }

    /**
     * Helper method to create an NFA for a certain string input s, which connects two states with one connection
     * @param s The transition's character, being one valid character for a regex
     * @return The NFA consisting of one connection
     */
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

    /**
     * Regex parser engine that parses the regex into a tree format
     */
    private class RegexParser {

        RegexParser(String input) {
            parseInput(input);
        }

        private void parseInput(String input) {
            if (input.charAt(0) == '(') {
                ArrayList<String> temp;
                switch (input.substring(1, 3)) {
                    //is an operand and set to node of a tree with arraylist of next regexes
                    case "r*":
                        isOperand = true;   //is an operand
                        code = "r*";
                        temp = getSplitInput(findInputString(input));   //splits input up into sections and returns arraylist
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
                    //is a terminal character, set to certain code
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

        /**
         * Works like a regex of indeterminant length, could be changed to regex - will probably change later
         * @param input The input of a regex that is split into multiple sections
         * @return ArrayList of the inputs split into different indices
         */
        private ArrayList<String> getSplitInput(String input) {
            ArrayList<String> concatInput = new ArrayList<>();
            String temp = input;
            //get input that is split up into sections
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

        /**
         * Returns an input string for a certain input that contains a specifier(gets input between parens and after an operand
         * @param input The input to find a string after an operand
         * @return The raw string within the operand
         */
        private String findInputString(String input) {
            return input.substring(4, input.length() - 1);
        }
    }
}
