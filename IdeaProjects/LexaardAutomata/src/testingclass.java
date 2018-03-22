import java.util.ArrayList;
import java.util.List;

public class testingclass {

    public static void main(String[] args) {
        List<String> newList = new ArrayList<>();

        newList.add("yes");
        newList.add("no");

        newList.add(0, "si");

        System.out.println(newList);
    }
}
