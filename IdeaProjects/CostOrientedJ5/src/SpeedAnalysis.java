import java.util.LinkedHashMap;
import java.util.Scanner;

public class SpeedAnalysis {
    private static Scanner scan;

    private SpeedAnalysis(String input) {
        scan = new Scanner(input);
        char move = scan.next().charAt(0);
        int doorDistance = Integer.parseInt(scan.next());

        String line = scan.nextLine();
        Scanner lineS = new Scanner(line);
        String firstEl = "";

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

        for (int i = 0; i < pog.length; i++) {
            costs[i] = Integer.parseInt(cos[i]);
            pogos[i] = Integer.parseInt(pog[i]);
        }

        switch (move) {
            case 'E':
                new JJ(doorDistance, pogos, costs);
                break;
            case 'M':
                new JJ(doorDistance, pogos, costs, coins);
                break;
        }
        scan.close();
    }

    public static void main(String[] args) {
        scan = new Scanner(System.in);
        System.out.print("Enter the distance to the door, followed by JJ's pogo sticks' distances: ");
        new SpeedAnalysis(scan.nextLine());
        scan.close();
    }
}