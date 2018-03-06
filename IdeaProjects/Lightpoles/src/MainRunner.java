import java.util.Scanner;

public class MainRunner {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        new Interpreter(scan.nextLine());
        scan.close();
    }
}
