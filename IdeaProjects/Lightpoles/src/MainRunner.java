import java.util.Scanner;

/**
 * Runner class for main algorithm, does not do test cases
 */
public class MainRunner {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        new Interpreter(scan.nextLine());
        scan.close();
    }
}
