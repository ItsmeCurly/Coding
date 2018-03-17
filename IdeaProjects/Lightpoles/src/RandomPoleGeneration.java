import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Class utilized for randomly generating lists of pole lengths
 */
public class RandomPoleGeneration implements CROSS {
    private final int MAXPOLELENGTH = 20;       //max size of single pole to use
    private final int MAXPOLES = 40;            //max amount of poles for JJ to use

    private int[] poleLengths;                  //array to store poles

    /**
     * Randomly generates pole lengths and puts into poleLengths array
     */
    public RandomPoleGeneration() {
        Random rand = new Random();

        poleLengths = new int[rand.nextInt(MAXPOLES) + 1];

        for (int i = 0; i < poleLengths.length; i++) {
            poleLengths[i] = rand.nextInt(MAXPOLELENGTH) + 1;
        }
    }

    /**
     * Needs main function to be run by itself, does not get called by test cases to avoid needless computation
     * @param args Args to function
     */
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

    /**
     * Gets the list representation of the poles
     * @return Returns the list representation of the pole lengths
     */
    public String toString() {
        String s = "";
        for (int pole : poleLengths) s += pole + " ";
        return s;
    }
}
