import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GNFA {
    private String comment;
    private List<GNFAState> states;

    public GNFA() {

    }

    public GNFA(List<GNFAState> states) {

    }

    public static GNFA constructGNFA(String input) {
        //TODO MAKE STATES EASIER TO ADD/DELETE
        GNFA gnfa = new GNFA();
        Scanner scan = new Scanner(input);

        Scanner line = new Scanner(scan.nextLine());
        //set comment of GNFA
        gnfa.setComment(line.next());

        //get alphabet of FSA
        List<GNFAState> states = new ArrayList<>();

        line = new Scanner(scan.nextLine());

        while (line.hasNext()) {
            GNFAState st = new GNFAState(line.next());
        }
        for (GNFAState s : states) {
            s.setNextStateTransitions(new ArrayList<>(states.size()));
        }
        //end get alphabet of FSA

        //read in states and transitions
        int i;
        int j = 0;
        //with the states, construct the transitions that exist for each state
        while (scan.hasNextLine()) {
            Scanner sc = new Scanner(scan.nextLine());
            i = 0;
            while (sc.hasNext()) {
                String code = sc.next();
                if (!code.equals("..")) {
                    Regex r1 = new Regex(code);
                    states.get(j).addTransition(i, r1);
                }
            }
            j += 1;
        }
        return gnfa;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
