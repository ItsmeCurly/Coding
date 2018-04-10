import java.util.LinkedList;
import java.util.List;

public class Rule implements Comparable<Rule> {
    private String lhs;
    private List<String> rhs;

    /**
     * Creates a rule with a LHS searched from available names, as must be unique, only has 78 choices
     *
     * @param lhs The LHS variable, either pregenerated or gotten from input
     */
    public Rule(String lhs) {
        this.lhs = lhs;
        this.rhs = new LinkedList<>();
    }

    /**
     * Creates a rule with specified lhs and rh s
     *
     * @param lhs String representing the LHS
     * @param rhs List representing the RHS
     */
    public Rule(String lhs, List<String> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * Creates a rule from the specified rule
     *
     * @param o The other rule
     */
    public Rule(Rule o) {
        this.lhs = o.lhs;
        this.rhs = new LinkedList<>(o.rhs);
    }

    /**
     * Adds an array to the RHS list
     *
     * @param rhs The array to add to the RHS variable
     */
    public void addRHS(String[] rhs) {
        for (String s :
                rhs) {
            addRHS(s);
        }
    }

    /**
     * Adds a list to the RHS
     *
     * @param rhs The list to add to the RHS
     */
    public void addRHS(List<String> rhs) {
        for (String s : rhs) {
            addRHS(s);
        }
    }

    /**
     * Adds to the end of the RHS list
     *
     * @param newString The string to add to RHS
     */
    public void addRHS(String newString) {
        if (!rhs.contains(newString)) {
            rhs.add(newString);
        }
    }

    /**
     * Returns the rhs list
     *
     * @return The RHS variables
     */
    public List<String> getRHS() {
        return rhs;
    }

    /**
     * Sets a certain rhs variable at index index
     * @param index The index to change the rhs
     * @param s The string to change index to
     */
    public void setRHS(int index, String s) {
        this.rhs.set(index, s);
    }

    /**
     * Returns the LHS
     * @return the lhs string
     */
    public String getLHS() {
        return lhs;
    }

    /**
     * Sets the lhs variable
     *
     * @param lhs The new lhs
     */
    public void setLHS(String lhs) {
        this.lhs = lhs;
    }

    public String toString() {
        String result = lhs;

        if (!rhs.isEmpty()) {
            result += " -> ";
        }

        for (String rh : rhs) {
            result += rh + " | ";
        }

        if (!rhs.isEmpty()) {
            result = result.substring(0, result.length() - 3);
        }

        return result;
    }

    @Override
    public int compareTo(Rule o) {
        return getLHS().compareTo(o.getLHS());
    }
}
