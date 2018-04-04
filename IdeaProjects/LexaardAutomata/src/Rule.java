import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Rule implements Comparable<Rule> {
    private static ArrayList<String> availableNames;
    private String lhs;
    private List<String> rhs;

    public Rule() {
        lhs = availableNames.remove(0);
        rhs = new LinkedList<>();
    }

    public Rule(String lhs, List<String> rhs) {
        this.lhs = lhs;
        availableNames.remove(lhs);
        this.rhs = rhs;
    }

    public Rule(Rule o) {
        this.lhs = o.lhs;
        availableNames.remove(lhs);
        this.rhs = new LinkedList<>(o.rhs);
    }

    public static void initNames() {
        availableNames = new ArrayList<>();
        for (int i = 65; i <= 90; i++) {
            availableNames.add((char) i + "");
        }
        int i = availableNames.size();
        for (int j = 0; j < i; j++) {
            availableNames.add(availableNames.get(j) + "*");
            availableNames.add(availableNames.get(j) + '\'');
        }
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
