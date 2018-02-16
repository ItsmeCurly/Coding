import java.util.*;

public class JJ {
    private int[] pogos;
    private int doorLength;

    private int counter;
    private ArrayList<String> output;

    private int[] costs;
    private int minCost;
    private int[] minList;

    JJ(int doorLength, int[] pogos, int[] costs) { //enumeration
        this.doorLength = doorLength;
        this.pogos = pogos;
        this.costs = costs;

        this.counter = 0;
        this.output = new ArrayList<>();

        enumerate(new int[doorLength], 0, 0);
        sortList(output);
        System.out.println("# of Combinations: " + counter + "\n" + printList(output));
    }

    JJ(int doorLength, int[] pogos, int[] costs, LinkedHashMap<Integer, Integer> coins) { //min cost
        this.doorLength = doorLength;
        this.pogos = pogos;
        this.costs = costs;

        minCost = Integer.MAX_VALUE;

        minimumCost(new int[doorLength], 0, 0, coins);
        System.out.println(this.minCost + " " + printList(minList));
    }

    private void enumerate(int[] movements, int currentIndex, int currentCost) { //minCost
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

    private void addEnumeration(int[] movements, int currentCost, int range) { //to print the combinations and #of combos
        counter += 1; //add to combos
        String out = "";
        int[] newArray = Arrays.copyOfRange(movements, 0, range);
        out += currentCost + " ";
        for (int i : newArray)
            out += i + " ";
        output.add(out);
    }

    private void sortList(List<String> list) {
        list.sort(new StringSort());
    }

    private String printList(int[] list) {
        String out = "";
        for (int i : list)
            out += (i > 0 ? i + " " : "");
        return out;
    }

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

    private int sumArr(int[] list) {
        int sum = 0;
        for (int movement : list)
            sum += movement;
        return sum;
    }

    class StringSort implements Comparator {
        public int compare(Object x, Object o) {
            Scanner scan1 = new Scanner((String) x);
            Scanner scan2 = new Scanner((String) o);
            return scan1.nextInt() - scan2.nextInt();
        }
    }
}