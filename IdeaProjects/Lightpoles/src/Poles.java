import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Poles {
    private int[] poleLengths;
    private Set<int[]> outputSet;

    public Poles(int[] poleLengths) {
        this.poleLengths = poleLengths;
        outputSet = new HashSet<>();
        Arrays.sort(poleLengths);
        getPoles(this.poleLengths, new int[]{0, 0, 0}, 0);
    }

    public void getPoles(int[] poles, int[] theseMoves, int poleNumber) {
        if (isLine(theseMoves)) {
            if (!setContains(outputSet, theseMoves))
                outputSet.add(theseMoves);
        }

        if (poleNumber > 2) {
            return;
        }

        if (theseMoves[1] != 0 && theseMoves[0] > theseMoves[1]) {
            return;
        }

        if (theseMoves[2] != 0 && theseMoves[1] > theseMoves[2]) {
            return;
        }

        for (int i = 0; i < poles.length; i++) { //to check all pogo jumps
            int[] newMoves = Arrays.copyOf(theseMoves, theseMoves.length);
            int[] newPoles = Arrays.copyOfRange(poles, i + 1, poles.length);
            newMoves[poleNumber] = poles[i];
            getPoles(newPoles, newMoves, poleNumber + 1);
        }
    }

    private boolean isLine(int[] thesePoles) {
        for (int i = 0; i < 3; i++)
            if (thesePoles[i] == 0)
                return false;
        int slope1 = thesePoles[1] - thesePoles[0];
        int slope2 = thesePoles[2] - thesePoles[1];
        return (slope1 == slope2) && slope1 >= 0 && slope2 >= 0;
    }

    private String printSet(Set<int[]> outputSet) {
        String result = "";
        Iterator iter = outputSet.iterator();
        while (iter.hasNext()) {
            for (int i : (int[]) iter.next()) {
                result += i + " ";
            }
            result += '\n';
        }
        return result;
    }

    private boolean setContains(Set<int[]> set, int[] add) {
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            boolean contains = true;
            int[] temp = (int[]) iter.next();
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] != add[i]) {
                    contains = false;
                    break;
                }
            }
            if (contains) return true;
        }
        return false;
    }

    public void printResults() {
        System.out.println(printSet(outputSet));
    }

    public int getNumPoles() {
        return poleLengths.length;
    }
}
