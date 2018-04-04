import java.util.LinkedList;
import java.util.List;

public class Rule implements Comparable<Rule> {
    private String lhs;
    private List<String> rhs;
    private static int counter;

    public Rule() {
        lhs = "R_" + counter;
        rhs = new LinkedList<>();
        counter += 1;
    }

    public Rule(String lhs) {
        this.lhs = lhs;
        rhs = new LinkedList<>();
        counter += 1;

    }

    public Rule(String lhs, List<String> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
        counter += 1;
    }

    public Rule(Rule o) {
        this.lhs = o.lhs;
        this.rhs = new LinkedList<>(o.rhs);
        counter += 1;
    }

    public void addRHS(String[] rhs) {
        for (String s :
                rhs) {
            addRHS(s);
        }
    }


    public void addRHS(List<String> rhs) {
        for (String s : rhs) {
            addRHS(s);
        }
    }

    public void addRHS(String newString) {
        if (!rhs.contains(newString)) {
            rhs.add(newString);
        }
    }

    public List<String> getRHS() {
        return rhs;
    }

    public void setRHS(int index, String s) {
        this.rhs.set(index, s);
    }

    public void setLHS(String lhs) {
        this.lhs = lhs;
    }

    public String getLHS() {
        return lhs;
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
