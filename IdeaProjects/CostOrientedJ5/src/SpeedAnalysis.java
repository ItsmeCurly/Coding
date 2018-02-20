import java.io.*;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class SpeedAnalysis {
    /**
     * Recycled code from homework 1, uses System.currentTimeMillis() to get runtime and subtracts from end time
     * The algorithm will print to etimes.txt or mtimes.txt whether the command was E vs M
     */
    private SpeedAnalysis() {
        Scanner scan2 = new Scanner(System.in);
        char move = scan2.next().toUpperCase().charAt(0);

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("assets\\RuntimeAnalysis\\" + Character.toString(move).toLowerCase() + "arrays.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        long[] times = new long[DATA.RUNS];
        int[] length = new int[DATA.RUNS];
        int[] length_ = new int[DATA.RUNS];
        int[] combos = new int[DATA.RUNS];

        switch (move) {
            case 'E':
                for (int i = 0; i < DATA.RUNS; i++) {
                    String input = null;
                    try {
                        assert br != null;
                        input = br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scanner lineS = new Scanner(input);
                    lineS.next();
                    int doorDistance = lineS.nextInt();
                    String firstEl;

                    LinkedHashMap<Integer, Integer> coins = new LinkedHashMap<>();
                    String po = "";
                    String co = "";

                    while (lineS.hasNext()) {
                        if ((firstEl = lineS.next()).contains("-"))
                            coins.put(lineS.nextInt(), Integer.parseInt(firstEl.replace("-", "")));
                        else {
                            co += firstEl + " ";
                            po += lineS.next() + " ";
                        }
                    }

                    String[] cos = co.split(" ");
                    String[] pog = po.split(" ");

                    int[] costs = new int[cos.length];
                    int[] pogos = new int[pog.length];

                    for (int j = 0; j < pog.length; j++) {
                        costs[j] = Integer.parseInt(cos[j]);
                        pogos[j] = Integer.parseInt(pog[j]);
                    }

                    long startTime = System.currentTimeMillis();
                    JJ jj = new JJ(doorDistance, pogos, costs);
                    long endTime = System.currentTimeMillis();
                    times[i] = endTime - startTime;
                    length[i] = doorDistance;
                    length_[i] = pogos.length;
                    combos[i] = jj.getCombos();
                    System.out.println(i);
                }
                PrintWriter pw = null;
                try {
                    br.close();
                    pw = new PrintWriter("assets\\RuntimeAnalysis\\" + Character.toString(move).toLowerCase() + "times.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < DATA.RUNS; i++) {
                    pw.print(times[i]);
                    pw.println(" " + length[i] + " " + length_[i] + " " + combos[i]);
                }
                pw.close();

                break;
            case 'M':
                for (int i = 0; i < DATA.RUNS; i++) {
                    String input = null;
                    try {
                        assert br != null;
                        input = br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scanner lineS = new Scanner(input);

                    lineS.next();
                    int doorDistance = lineS.nextInt();
                    String firstEl;

                    LinkedHashMap<Integer, Integer> coins = new LinkedHashMap<>();
                    String po = "";
                    String co = "";

                    while (lineS.hasNext()) {
                        if ((firstEl = lineS.next()).contains("-"))
                            coins.put(lineS.nextInt(), Integer.parseInt(firstEl.replace("-", "")));
                        else {
                            co += firstEl + " ";
                            po += lineS.next() + " ";
                        }
                    }

                    String[] cos = co.split(" ");
                    String[] pog = po.split(" ");

                    int[] costs = new int[cos.length];
                    int[] pogos = new int[pog.length];

                    for (int j = 0; j < pog.length; j++) {
                        costs[j] = Integer.parseInt(cos[j]);
                        pogos[j] = Integer.parseInt(pog[j]);
                    }

                    long startTime = System.currentTimeMillis();
                    JJ jj = new JJ(doorDistance, pogos, costs, coins);
                    long endTime = System.currentTimeMillis();
                    times[i] = endTime - startTime;
                    length[i] = doorDistance;
                    length_[i] = pogos.length;
                    combos[i] = jj.getCombos();
                }
                pw = null;
                try {
                    br.close();
                    pw = new PrintWriter("assets\\RuntimeAnalysis\\" + Character.toString(move).toLowerCase() + "times.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < DATA.RUNS; i++) {
                    pw.print(times[i]);
                    pw.println(" " + length[i] + " " + length_[i] + " " + combos[i]);
                }
                pw.close();
                break;
        }
    }

    public static void main(String[] args) {
        new SpeedAnalysis();
    }
}