import java.util.Scanner;

public class Runner implements DATA {
    private static Scanner scan;

    private Runner(String input) {
        scan = new Scanner(input);
        int doorDistance = Integer.parseInt(scan.next());
        String[] pogos = input.split(" ");
        int[] pogoDistances = new int[pogos.length - 1];
        for (int i = 1; i < pogos.length; i++)
            pogoDistances[i - 1] = Integer.parseInt(pogos[i]);
        new JJ(doorDistance, pogoDistances);
    }

    public static void main(String[] args) {
        scan = new Scanner(System.in);
        System.out.print("Enter the distance to the door, followed by JJ's pogo sticks' distances: ");
        new Runner(scan.nextLine());
    }
}
