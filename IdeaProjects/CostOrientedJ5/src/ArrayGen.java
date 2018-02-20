import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Scanner;

public class ArrayGen {
    private static char move;
    private int[] pogos;
    private int[] costs;
    private int doorLength;
    private LinkedHashMap<Integer, Integer> coins;

    /**
     * Randomly generates sets of arrays set
     */
    public ArrayGen() {
        Random rand = new Random();
        doorLength = rand.nextInt(15) + 5;

        int length = rand.nextInt(doorLength - 2) + 3;

        costs = new int[length];
        pogos = new int[length];

        ArrayList<Integer> duplicates = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            do {
                costs[i] = rand.nextInt(10) + 1;
                pogos[i] = rand.nextInt(length) + 1;
            }
            while (duplicates.contains(pogos[i]));

            duplicates.add(pogos[i]);
        }
        ArrayList<Integer> duplicates_ = new ArrayList<>();
        if (move == 'M') {
            int coinsLength = rand.nextInt(doorLength / 5);
            coins = new LinkedHashMap<>();
            for (int i = 0; i < coinsLength; i++) {
                int location;
                do {
                    location = rand.nextInt(doorLength);
                    int amount = rand.nextInt(10) + 1;
                    coins.put(location, amount);
                }
                while (duplicates_.contains(location));
            }
        }
    }

    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);
            move = scan.next().toUpperCase().charAt(0);
            PrintWriter pw = new PrintWriter("assets\\RuntimeAnalysis\\" + Character.toString(move).toLowerCase() + "arrays.txt");
            for (int i = 0; i < DATA.RUNS; i++) {
                pw.write(new ArrayGen().toString());
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        String s = Character.toString(move);
        s += " " + doorLength;
        for (int i = 0; i < pogos.length; i++) {
            s += " " + costs[i] + " " + pogos[i];
        }
        if (move == 'M') {
            for (int i = 0; i < pogos.length; i++) {
                if (coins.containsKey(i))
                    s += " -" + coins.get(i) + " " + i;
            }

        }
        s += '\n';
        return s;
    }
}
