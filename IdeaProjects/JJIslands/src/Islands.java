import java.util.LinkedList;
import java.util.List;

public class Islands {
    private LinkedList<LinkedList<String>> adjList;

    public Islands(List<String> pairs) {
        adjList = new LinkedList<>();
        createIslands(pairs);
    }

    private void createIslands(List<String> pairs) {
        for (String pair : pairs) {
            String[] pairSplit = pair.split(" ");
            if (!containsIsland(pairSplit[0])) {
                LinkedList<String>
                adjList.add(new LinkedList<>());
            }
        }
    }

    private boolean containsIsland(String s) {
    }
}
