import java.util.ArrayList;

public class Regex {
    private String code;
    private Automaton fsaRepre;

    public Regex() {
        this(null);
    }

    public Regex(String pre) {
        new RegexParser(pre);
    }

    public boolean match(String input) {
        return fsaRepre.run(input).equals("accept");
    }

    @Override
    public String toString() {
        return code;
    }

    public Automaton getFSARepresentation() {
        return fsaRepre;
    }

    public void findFSArepre() {
        //TODO find fsa repre of regex
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
                            return "^$";
                        case "r/":
                            return null;
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
                ret += parseInput(s);
            }

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
