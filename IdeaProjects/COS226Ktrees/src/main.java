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
        while (true) {
            System.out.print("Enter a command for the RecordManager to handle: ");
            line = br.readLine();
            st = new StringTokenizer(line, delimiter);
            getInput(st, rm);
        }
    }

    private static void getInput(StringTokenizer st, RecordManager rm) {
        String command = st.nextToken();

        if (command.equals("c")) {
            rm = new RecordManager(Integer.parseInt(st.nextToken()));
        } else if (command.equals("s")) {
            int k = Integer.parseInt(st.nextToken());
            String d = st.nextToken();
        }
    }
}
