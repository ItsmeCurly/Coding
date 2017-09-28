import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;


public class main {
    public static void main(String[] args) throws IOException {
        RecordManager rm = new RecordManager();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));   //reader from input
        StringTokenizer st;                                                         //tokenizer for input
        String line, delimiter = " ";                                               //line and delimiter
        boolean go = true;
        while (go) {    //endless loop for commands, q for sentinel
            System.out.print("Enter a command for the RecordManager to handle(q to quit): ");
            line = br.readLine();
            st = new StringTokenizer(line, delimiter);

            go = getInput(st, rm);
        }
    }

    private static boolean getInput(StringTokenizer st, RecordManager rm) {
        String command = st.nextToken();
        if (command.equalsIgnoreCase("q")) {    //leave
            return false;
        } else if (command.equals("c")) {
            rm.makeNew(Integer.parseInt(st.nextToken()));
            System.out.println();
        } else if (command.equals("s")) {
            int key = Integer.parseInt(st.nextToken());
            String data = st.nextToken();
            rm.store(new TreeNode(key, data));
            System.out.println();
        } else if (command.equals("e")) {
            int key = Integer.parseInt(st.nextToken());
            System.out.println(rm.search(key));
        } else if (command.equals("r")) {
            try {
                int key = Integer.parseInt(st.nextToken());
                TreeNode find = rm.searchNode(key);
                if (find != null) {
                    String data = find.getData();
                    System.out.print((data != null) ? data : "");
                }
            } catch (NumberFormatException e) {
                System.err.print("key input not as integer");
            } finally {
                System.out.println();
            }
        } else if (command.equals("d")) {
            int key = Integer.parseInt(st.nextToken());
            rm.delete(key);
        } else if (command.equals("xs")) {
            System.out.println(rm.size());
        } else if (command.equals("xh")) {
            System.out.println(rm.height());
        } else if (command.equals("xp")) {
            System.out.println(rm);
        } else {
            System.out.println("Unrecognized command");
        }
        return true;    //continue loop
    }
}
