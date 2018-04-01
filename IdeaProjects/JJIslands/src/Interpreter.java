import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter {

    public Interpreter() {
        run();
    }

    public static void main(String[] args) {
        new Interpreter();
    }

    private void run() {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        Pattern pattern = Pattern.compile("[0-9] [0-9]");

        Matcher m = pattern.matcher(input);
        List<String> list = new LinkedList<>();

        while (m.find()) {
            list.add(m.group());
        }
    }
}
