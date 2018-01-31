import java.io.*;
import java.util.Scanner;

public class RunnerTester implements DATA {

    private RunnerTester() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("assets\\inputs.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        long[] times = new long[RUNS];
        int[] length = new int[RUNS];
        int[] length_ = new int[RUNS];
        int[] combos = new int[RUNS];

        for (int i = 0; i < RUNS; i++) {
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
            scan.close();
            String[] pogos = input.split(" ");
            int[] pogoDistances = new int[pogos.length - 1];
            for (int j = 1; j < pogos.length; j++)
                pogoDistances[j - 1] = Integer.parseInt(pogos[j]);
            long startTime = System.currentTimeMillis();
            JJ jj = new JJ(doorDistance, pogoDistances);
            long endTime = System.currentTimeMillis();
            times[i] = endTime - startTime;
            length[i] = doorDistance;
            length_[i] = pogoDistances.length;
            combos[i] = jj.getCombos();
        }
        PrintWriter pw = null;
        try {
            br.close();
            pw = new PrintWriter("assets\\times.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < RUNS; i++) {
            pw.print(times[i]);
            pw.println(" " + length[i] + " " + length_[i] + " " + combos[i]);
        }
        pw.close();
    }

    public static void main(String[] args) {
        new RunnerTester();
    }
}
