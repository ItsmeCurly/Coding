import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class RandomArrayGen implements DATA {
    private int[] pogos;
    private int doorLength;

    private RandomArrayGen() {
        Random rand = new Random();

        doorLength = rand.nextInt(14) + 3;
        int length = rand.nextInt(doorLength - 2) + 3;

        pogos = new int[length];
        ArrayList<Integer> duplicates = new ArrayList<>();
        for (int i = 0; i < pogos.length; i++) {
            do pogos[i] = rand.nextInt(length) + 1;
            while (duplicates.contains(pogos[i]));

            duplicates.add(pogos[i]);
        }
    }

    public static void main(String[] args) throws IOException {
        PrintWriter pw = new PrintWriter("assets\\inputs.txt");
        for (int i = 0; i < RUNS; i++)
            pw.println(new RandomArrayGen().toString());
        pw.close();
    }

    public String toString() {
        String s = "";
        s += doorLength;
        for (int pogo : pogos) s += " " + pogo;
        return s;
    }
}

