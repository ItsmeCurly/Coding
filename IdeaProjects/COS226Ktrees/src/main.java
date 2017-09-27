import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;


public class main {
    public static void main(String[] args) throws IOException {
        RecordManager rm = new RecordManager();

//        rm.getKst().store(new TreeNode(5, "b"));
//        rm.getKst().store(new TreeNode(2, "b"));
//        rm.getKst().store(new TreeNode(3, "b"));
//        rm.getKst().store(new TreeNode(4, "b"));
//        rm.getKst().store(new TreeNode(6, "b"));
//        rm.getKst().store(new TreeNode(7, "b"));
//        rm.getKst().store(new TreeNode(7, "b"));
//        rm.getKst().store(new TreeNode(11, "b"));
//        rm.getKst().store(new TreeNode(15, "b"));
//        rm.getKst().store(new TreeNode(16, "b"));
//        rm.getKst().store(new TreeNode(0, "b"));
//
//        System.out.println("\n");
//        System.out.println(rm.toString());


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        String line, delimiter = " ";
        boolean go = true;
        while (go) {
            System.out.print("Enter a command for the RecordManager to handle(q to quit): ");
            line = br.readLine();
            st = new StringTokenizer(line, delimiter);

            go = getInput(st, rm);
        }
    }

    private static boolean getInput(StringTokenizer st, RecordManager rm) {
        String command = st.nextToken();
        if (command.equalsIgnoreCase("q")) {
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
        return true;
    }
}
