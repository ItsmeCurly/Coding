import java.util.*;

public class JJ {
    private int[] pogos;
    private int doorLength;

    private int counter;
    private ArrayList<String> output;

    private int[] costs;
    private int minCost;
    private int[] minList;

    JJ(int doorLength, int[] pogos, int[] costs) { //enumeration constructor
        this.doorLength = doorLength;
        this.pogos = pogos;
        this.costs = costs;

        this.counter = 0;
        this.output = new ArrayList<>();

        enumerate(new int[doorLength], 0, 0);
        sortList(output);
    }

    JJ(int doorLength, int[] pogos, int[] costs, LinkedHashMap<Integer, Integer> coins) { //min cost constructor
        this.doorLength = doorLength;
        this.pogos = pogos;
        this.costs = costs;

        minCost = Integer.MAX_VALUE;

        minimumCost(new int[doorLength], 0, 0, coins);
    }

    /**
     * The recursive algorithm that finds all the enumerations for a given input, specified by E or M
     *
     * @param movements    the current set of movements that JJ has done
     * @param currentIndex current index to put the next pogo movement
     * @param currentCost  current cost of all the movements already done
     */
    private void enumerate(int[] movements, int currentIndex, int currentCost) {
        int sum = sumArr(movements);

        if (doorLength == sum) //the movements = doorlength(reached)
            addEnumeration(movements, currentCost, currentIndex);

        if (doorLength <= sum) //the movements is greater or equal to the doorlength, to end recursion
            return;

        for (int i = 0; i < pogos.length; i++) { //to check all pogo jumps
            int[] newMovements = Arrays.copyOf(movements, movements.length);
            newMovements[currentIndex] = pogos[i];
            enumerate(newMovements, currentIndex + 1, currentCost + costs[i]);
        }
    }

    /**
     * Same as above, except the algorithm finds only the minimum cost set of movements instead of all the enumerations of all paths
     * @param movements the current set of movements that JJ has done
     * @param currentIndex current index to put the next pogo movement
     * @param currentCost current cost of all the movements already done
     * @param coins the coins on JJ's path to the door
     */
    private void minimumCost(int[] movements, int currentIndex, int currentCost, LinkedHashMap<Integer, Integer> coins) {  //coin move
        int sum = sumArr(movements);

        int cost = currentCost;
        if (coins.containsKey(sum))
            cost -= coins.get(sum);

        if (doorLength == sum) //the movements = doorlength(reached)
            if (minCost > cost) {
                minCost = cost;
                minList = movements;
            }
        if (doorLength <= sum) //the movements is greater or equal to the doorlength, to end recursion
            return;

        for (int i = 0; i < pogos.length; i++) { //to check all pogo jumps
            int[] newMovements = Arrays.copyOf(movements, movements.length);
            newMovements[currentIndex] = pogos[i];
            minimumCost(newMovements, currentIndex + 1, cost + costs[i], coins);
        }
    }

    /**
     * Adds an enumeration to be printed out after the program is done
     * @param movements Set of movements to the door
     * @param currentCost Cost of movements to get there
     * @param range The index that ends the movements
     */
    private void addEnumeration(int[] movements, int currentCost, int range) { //to print the combinations and #of combos
        counter += 1; //add to combos
        String out = "";
        int[] newArray = Arrays.copyOfRange(movements, 0, range);
        out += currentCost + " ";
        for (int i : newArray)
            out += i + " ";
        output.add(out);
    }

    /**
     * Sorts the ArrayList lexicographically
     * @param list
     */
    private void sortList(List<String> list) {
        list.sort(new StringSort());
    }

    /**
     * Prints a list
     * @param list The list of primitive integers to be printed
     * @return The int array to be printed
     */
    private String printList(int[] list) {
        String out = "";
        for (int i : list)
            out += (i > 0 ? i + " " : "");
        return out;
    }

    /**
     * Prints an ArrayList in the format that I desire
     * @param list The ArrayList to be printed
     * @return The output
     */
    private String printList(List<String> list) {
        String out = "";
        for (String s : list) {
            Scanner scan1 = new Scanner(s);
            while (scan1.hasNextInt()) {
                int i = scan1.nextInt();
                out += (i > 0 ? i + " " : "");
            }
            scan1.close();
            out += '\n';
        }
        return out;
    }

    /**
     * Helper method to compactly sum an array
     * @param list The array to be summed up
     * @return The sum of the array
     */
    private int sumArr(int[] list) {
        int sum = 0;
        for (int movement : list)
            sum += movement;
        return sum;
    }

    /**
     * Getter of counter
     * @return The number of combinations
     */
    public int getCombos() {
        return counter;
    }

    /**
     * Whether the algorithm should print the output of the algorithm
     * @param move Whether the command is E or M
     */
    public void printOutput(char move) {
        switch (move) {
            case 'E':
                System.out.println("# of Combinations: " + counter + "\n" + printList(output));
                break;
            case 'M':
                System.out.println(this.minCost + " " + printList(minList));
                break;
        }
    }

    /**
     * Comparator to sort the ArrayList
     */
    class StringSort implements Comparator {
        public int compare(Object x, Object o) {
            Scanner scan1 = new Scanner((String) x);
            Scanner scan2 = new Scanner((String) o);
            return scan1.nextInt() - scan2.nextInt();
        }
    }
}