import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Rule {
    private String lhs;
    private List<String> rhs;

    public Rule() {
        lhs = "";
        rhs = new LinkedList<>();
    }

    public Rule(String lhs) {
        this.lhs = lhs;
        rhs = new LinkedList<>();

    }

    public void addRHS(String [] addRhs) {
        rhs.addAll(Arrays.asList(addRhs));
    }

    public List<String> getRHS() {
        return rhs;
    }

    public void setRHS(List<String> rhs) {
        this.rhs = rhs;
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
}
