import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Poles {
    private final int MAXSIZE = 3;  //max size of array (number of poles possible)
    private int[] poleLengths;      //array to statically hold all pole lengths
    private Set<int[]> outputSet;   //output set containing all lists
    private int calls;              //for output in test cases
    private int combinations;       //number of combinations found for poles

    /**
     * Constructor for poles, takes in a array of pole lengths and runs the recursive algorithm
     * @param poleLengths
     */
    public Poles(int[] poleLengths) {
        this.poleLengths = poleLengths;
        this.combinations = 0;
        this.calls = 0;
        this.outputSet = new HashSet<>();    //initialize

        Arrays.sort(poleLengths);       //sort beforehand, otherwise this doesn't function

        getPoles(this.poleLengths, new int[]{0, 0, 0}, 0);
    }

    /**
     * Recursive algorithm, the meat and potatoes of the project. Takes in static pole lengths and recursively enumerates
     * through all possible combinations of the pole lengths, adding all combinations that result in a correct arrangement of poles
     * @param poles The array of pole lengths, to be dynamic array as JJ uses poles and cannot be reused
     * @param theseMoves This set of poles that JJ is using to align the light
     * @param poleNumber The polenumber that JJ is currently at, to be either 0, 1, 2
     */
    public void getPoles(int[] poles, int[] theseMoves, int poleNumber) {
        if (isLine(theseMoves)) {       //check if the current poles that JJ has arranged are a line
            if (!setContains(outputSet, theseMoves))    //if true, check if in set already, if it isn't, add and increment combinations regardless
                outputSet.add(theseMoves);
            combinations += 1;
        }

        if (poleNumber >= MAXSIZE) {        //stop if trying to add when theseMoves is full
            return;
        }
        //now that I think about it, I don't think these are actually ever true since the array is sorted to be incrementing no matter what
        if (theseMoves[1] != 0 && theseMoves[0] > theseMoves[1]) {  //if first index is greater than second and second isn't 0, it isn't possible
            return;
        }

        if (theseMoves[2] != 0 && theseMoves[1] > theseMoves[2]) {  //if second index is greater than third, this isn't possible
            return;
        }

        for (int i = 0; i < poles.length; i++) { //to check all pole lists
            int[] newMoves = Arrays.copyOf(theseMoves, theseMoves.length);          //create new arrays as pass by reference, don't want modifications on original
            int[] newPoles = Arrays.copyOfRange(poles, i + 1, poles.length);

            newMoves[poleNumber] = poles[i];    //add new pole to current spot to add to

            calls += 1;         //add to calls, number of recursive calls made for test case analysis

            getPoles(newPoles, newMoves, poleNumber + 1);   //recursive call of algorithm
        }
    }

    /**
     * Check to see if the current theseMoves array (current setup of poles) constitutes a line
     * @param thesePoles Current pole setup of JJ
     * @return Whether the setup is either a line of non-negative slope.
     */
    private boolean isLine(int[] thesePoles) {
        for (int i = 0; i < 3; i++)
            if (thesePoles[i] == 0)
                return false;
        int slope1 = thesePoles[1] - thesePoles[0];
        int slope2 = thesePoles[2] - thesePoles[1];
        return (slope1 == slope2) && slope1 >= 0 && slope2 >= 0;
    }

    /**
     * Prints the set in a suitable representation
     * @param outputSet Set to print
     * @return String representation of set of arrays
     */
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

    /**
     * Helper method to determine whether a certain array is in a set
     * @param set Set to check for containing
     * @param add The new array to add, checking whether the set has it or not
     * @return Whether the set set contains the new int array add
     */
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
    //getters and setters
    public void printResults() {
        System.out.println(printSet(outputSet));
    }

    public int getNumPoles() {
        return poleLengths.length;
    }

    public int getCombinations() {
        return combinations;
    }

    public int getCalls() {
        return calls;
    }
}
