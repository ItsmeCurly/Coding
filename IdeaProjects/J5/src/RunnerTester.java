import java.io.*;
import java.util.Scanner;

public class RunnerTester {

    private RunnerTester() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("assets\\inputs.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        long[] times = new long[10];
        int[] length = new int[10];
        for (int i = 0; i < 10; i++) {
            String input = null;
            try {
                assert br != null;
                input = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("New input: " + input);
            Scanner scan = new Scanner(input);
            int doorDistance = Integer.parseInt(scan.next());
            String[] pogos = input.split(" ");
            int[] pogoDistances = new int[pogos.length - 1];
            for (int j = 1; j < pogos.length; j++)
                pogoDistances[j - 1] = Integer.parseInt(pogos[j]);
            long startTime = System.nanoTime();
            new JJ(doorDistance, pogoDistances);
            long endTime = System.nanoTime();
            times[i] = endTime - startTime;
            length[i] = doorDistance;
        }
        PrintWriter pw = null;
        try {
            br.close();
            pw = new PrintWriter("assets\\times.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
            pw.print(times[i]);
            pw.println(" : " + length[i]);
        }
        pw.close();
    }

    public static void main(String[] args) {
        new RunnerTester();
    }
}
