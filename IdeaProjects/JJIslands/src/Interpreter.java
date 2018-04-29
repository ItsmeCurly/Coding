import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter {

    private Interpreter() {
        run();
    }

    public static void main(String[] args) {
        new Interpreter();
    }

    private void run() {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        List<String> list = new LinkedList<>();
        Scanner inputScan = new Scanner(input);
        while (inputScan.hasNext()) {
            list.add(inputScan.next() + " " + inputScan.next());
        }

        Islands il = new Islands(list);
        System.out.println(il.getResult());
    }
}
