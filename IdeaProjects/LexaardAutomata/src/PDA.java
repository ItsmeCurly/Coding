import java.util.*;

public class PDA {
    private List<PDAState> states;
    private Stack<String> stack;

    private String name;

    private List<String> stackAlphabet;

    private List<String> inputAlphabet;

    /**
     *
     */
    public PDA() {
        states = new ArrayList<>();
        stack = new Stack<>();

        stackAlphabet = new ArrayList<>();
        inputAlphabet = new ArrayList<>();
    }

    /**
     * @param input
     * @return
     */
    public static PDA constructPDA(String input) {
        PDA pda = new PDA();
        Scanner scan = new Scanner(input);

        pda.name = scan.next();
        scan.nextLine();

        pda.addInputAlphabet(scan.nextLine().split("\\s"));

        pda.addStackAlphabet(scan.nextLine().split("\\s"));

        List<PDAState> states = new ArrayList<>();

        ArrayList<String> stateLines = new ArrayList<>();

        while (scan.hasNextLine()) {
            String stateIdentifier = scan.next();
            PDAState s1;
            s1 = new PDAState();
            if (stateIdentifier.contains("*")) {
                stateIdentifier = stateIdentifier.substring(1, stateIdentifier.length());
                s1.setAcceptState(true);
            }
            s1.setStateIdentifier(stateIdentifier);
            states.add(s1);
            s1.setParentPDA(pda);
            stateLines.add(scan.nextLine());
        }

        for (int i = 0; i < stateLines.size(); i++) {
            String stateLine = stateLines.get(i);
            PDAState pda1 = states.get(i);

            Scanner stateScanner = new Scanner(stateLine);
            int lineCount = 0;
            while (stateScanner.hasNext()) {
                String s = stateScanner.next();
                if (!s.equals("..")) {
                    String[] transitionSplit = s.split(",");
                    for (int i1 = 0; i1 < transitionSplit.length; i1++)
                        transitionSplit[i1] = transitionSplit[i1].trim();

                    PDAState.Transition newTransition = new PDAState.Transition(
                            pda.inputAlphabet.get(lineCount / pda.inputAlphabet.size()),
                            pda.stackAlphabet.get(lineCount % pda.stackAlphabet.size()),
                            transitionSplit[1], getStateByString(transitionSplit[0], states));
                    pda1.addTransition(newTransition);
                }
                lineCount += 1;
            }
            stateScanner.close();
        }
        pda.states = states;


        scan.close();
        System.out.println(pda);
        return pda;
    }

    /**
     * @param state
     * @param stateList
     * @return
     */
    public static PDAState getStateByString(String state, List<PDAState> stateList) {
        for (PDAState ps : stateList) {
            if (ps.getStateIdentifier().equals(state)) {
                return ps;
            }
        }
        return null;
    }

    static <T> void addIfNotPresent(Collection<T> c, T element) {
        if (!c.contains(element) && !element.equals("")) {
            c.add(element);
        }
    }

    static <T> void addIfNotPresent(Collection<T> c, T[] array) {
        for (T e : array) {
            addIfNotPresent(c, e);
        }
    }

    private void addInputAlphabet(String[] split) {
        addIfNotPresent(inputAlphabet, split);
    }

    private void addStackAlphabet(String[] split) {
        addIfNotPresent(stackAlphabet, split);
    }

    /**
     * @return
     */
    public List<String> getStackAlphabet() {
        return stackAlphabet;
    }

    /**
     * @return
     */
    public List<String> getInputAlphabet() {
        return inputAlphabet;
    }

    public String toString() {
        String result = name + "\n";
        result += "    ";
        List<String> inputAlphabet1 = getInputAlphabet();
        List<String> stackAlphabet1 = getStackAlphabet();
        for (int i = 0; i < inputAlphabet1.size() * getStackAlphabet().size(); i++) {
            String s = inputAlphabet1.get(i / inputAlphabet1.size());
            result += String.format("%-10s", s);
        }
        result += "\n";
        result += "    ";
        for (int i = 0; i < inputAlphabet1.size() * getStackAlphabet().size(); i++) {
            String s = stackAlphabet1.get(i % stackAlphabet1.size());
            result += String.format("%-10s", s);
        }
        result += "\n";
        for (PDAState pdaS : states) {
            result += pdaS + "\n";
        }
        return result;
    }
}
