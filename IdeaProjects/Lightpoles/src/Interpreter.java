import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Interpreter implements CROSS {

    public Interpreter(String line) {
        String[] lengths = line.split(" ");

        int[] poleLengths = new int[lengths.length];

        for (int i = 0; i < poleLengths.length; i++) {
            poleLengths[i] = Integer.parseInt(lengths[i]);
        }

        Poles poles = new Poles(poleLengths);
        poles.printResults();
    }

    public Interpreter(File file) {
        long[] times = new long[RUNS];
        int[] numPoles = new int[RUNS];
        int[] combinations = new int[RUNS];
        int[] calls = new int[RUNS];

        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        for (int t = 0; t < RUNS; t++) {
            String line = fileScanner.nextLine();

            String[] input = line.split(" ");
            int[] poleLengths = new int[input.length];

            for (int i = 0; i < poleLengths.length; i++) {
                poleLengths[i] = Integer.parseInt(input[i]);
            }

            long startTime = System.nanoTime();

            Poles poles = new Poles(poleLengths);

            long endTime = System.nanoTime();

            long elongatedTime = endTime - startTime;

            times[t] = elongatedTime;
            numPoles[t] = poles.getNumPoles();
            combinations[t] = poles.getCombinations();
            calls[t] = poles.getCalls();
        }

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(outputFileString);
            fileScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < RUNS; i++) {
            pw.print(times[i]);
            pw.println(" " + numPoles[i] + " " + combinations[i] + " " + calls[i]);
        }
        pw.close();
    }
}
