import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TestInterpreter {
    private static long[] times = new long[IslandGenerator.RUNS];
    private static int[] islands = new int[IslandGenerator.RUNS];
    private static int[] connections = new int[IslandGenerator.RUNS];
    private static int[] islands_connections = new int[IslandGenerator.RUNS];
    private static int[] longestLengthPath = new int[IslandGenerator.RUNS];

    public static void main(String args[]) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileReader("input\\testInput1.txt"));
        int i = 0;
        while (scan.hasNextLine()) {
            String input = scan.nextLine();
            List<String> list = new LinkedList<>();
            Scanner inputScan = new Scanner(input);
            while (inputScan.hasNext()) {
                list.add(inputScan.next() + " " + inputScan.next());
            }
            long startTime = System.currentTimeMillis();

            Islands il = new Islands(list);

            long endTime = System.currentTimeMillis();

            long elapsedTime = endTime - startTime;
            times[i] = elapsedTime;
            islands[i] = il.getNumIslands();
            connections[i] = il.getNumConnections();
            islands_connections[i] = islands[i] * connections[i];
            longestLengthPath[i] = il.getLongestPath();
            i += 1;
        }
        printResults();
    }

    public static void printResults() {
        try {
            FileWriter fw = new FileWriter(new File("testOutput1.txt"));
            for (int i = 0; i < IslandGenerator.RUNS; i++) {
                fw.write(times[i] + " " + islands[i] + " " + connections[i] + " " + islands_connections[i] + " " + longestLengthPath[i] + '\n');
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
