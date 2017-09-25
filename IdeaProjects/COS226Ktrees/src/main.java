import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;


public class main {
    public static void main(String[] args) throws IOException {
        RecordManager rm = new RecordManager();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        String line, delimiter = " ";
        boolean go = true;
        while (go) {
            System.out.print("Enter a command for the RecordManager to handle(q to quit): ");
            line = br.readLine();
            st = new StringTokenizer(line, delimiter);

            getInput(st, rm);
        }
    }

    private static boolean getInput(StringTokenizer st, RecordManager rm) {
        String command = st.nextToken();
        if (command.equalsIgnoreCase("q")) {
            return false;
        } else if (command.equals("c")) {
            rm = new RecordManager(Integer.parseInt(st.nextToken()));
            System.out.println();
        } else if (command.equals("s")) {
            int k = Integer.parseInt(st.nextToken());
            String d = st.nextToken();
            rm.store(new TreeNode(k, d));
            System.out.println();
        } else if (command.equals("e")) {
            int k = Integer.parseInt(st.nextToken());
            System.out.println(rm.search(k));
        } else if (command.equals("r")) {
            int k = Integer.parseInt(st.nextToken());
            System.out.println(rm.searchNode(k));
        } else if (command.equals("d")) {
            int k = Integer.parseInt(st.nextToken());
            rm.delete(k);
        } else if (command.equals("xs")) {
        } else if (command.equals("xh")) {
        } else if (command.equals("xp")) {
        } else {
            System.out.println("Unrecognized command");
        }
        return true;
    }
}
