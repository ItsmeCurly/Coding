import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Interpreter implements CROSS {
    /**
     * Interpreter that runs from MainRunner.java, consists of a single line of input that will print the results to the console
     * @param line - the line of input that denotes the user input of pole lengths
     */
    public Interpreter(String line) {
        String[] lengths = line.split(" ");

        int[] poleLengths = new int[lengths.length];

        for (int i = 0; i < poleLengths.length; i++) {
            poleLengths[i] = Integer.parseInt(lengths[i]);
        }

        Poles poles = new Poles(poleLengths);
        poles.printResults();
    }

    /**
     * Interpreter constructor called from TestRunner.java, which passes in a file for input lines
     * @param file The file that contains the test cases randomly generated from RandomPoleGeneration.java
     */
    public Interpreter(File file) {
        long[] times = new long[RUNS];          //for printing to output
        int[] numPoles = new int[RUNS];
        int[] combinations = new int[RUNS];
        int[] calls = new int[RUNS];

        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        for (int t = 0; t < RUNS; t++) {        //should run for RUNS amount of times
            String line = fileScanner.nextLine();

            String[] input = line.split(" ");
            int[] poleLengths = new int[input.length];  //get input from line, break into array for input into poles

            for (int i = 0; i < poleLengths.length; i++) {
                poleLengths[i] = Integer.parseInt(input[i]);
            }

            long startTime = System.nanoTime();

            Poles poles = new Poles(poleLengths);

            long endTime = System.nanoTime();

            long elongatedTime = endTime - startTime;       //get elapsed time for algorithm

            times[t] = elongatedTime;                   //data to print
            numPoles[t] = poles.getNumPoles();
            combinations[t] = poles.getCombinations();
            calls[t] = poles.getCalls();
        }

        PrintWriter pw = null;      //print out to output file
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
