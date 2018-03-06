import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class RandomPoleGeneration implements CROSS {
    private final int MAXPOLELENGTH = 15;
    private final int MAXPOLES = 20;

    private int[] poleLengths;

    public RandomPoleGeneration() {
        Random rand = new Random();

        poleLengths = new int[rand.nextInt(MAXPOLES) + 1];

        for (int i = 0; i < poleLengths.length; i++) {
            poleLengths[i] = rand.nextInt(MAXPOLELENGTH) + 1;
        }
    }

    public static void main(String[] args) {
        PrintWriter pw = null;

        try {
            pw = new PrintWriter(fileString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < RUNS; i++) {
            pw.println(new RandomPoleGeneration());
        }

        pw.close();
    }

    public String toString() {
        String s = "";
        for (int pole : poleLengths) s += " " + pole;
        return s;
    }
}
