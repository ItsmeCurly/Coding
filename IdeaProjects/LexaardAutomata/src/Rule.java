import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Rule {
    private String lhs;
    private List<String> rhs;

    public Rule() {
        lhs = "";
        rhs = new LinkedList<>();
        parseRule();
    }

    private void parseRule() {

    }

    public String toString() {
        String result = lhs;

        if(!rhs.isEmpty()) {
            result += " -> ";
        }
        for (String rh : rhs) {
            result += rh + " | ";
        }
        if(!rhs.isEmpty()) {
            result = result.substring(result.length() - 2);
        }
        return result;
    }

    public void addRHS(String [] addRhs) {
        rhs.addAll(Arrays.asList(addRhs));
    }

    public void setLHS(String lhs) {
        this.lhs = lhs;
    }

    public String getLHS() {
        return lhs;
    }
}
