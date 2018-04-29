import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class IslandGenerator2 {
    private static final int RUNS = 5;
    private Map<Integer, List<Integer>> islandMapping;

    public IslandGenerator2(int numberIslands) {
        Random rand = new Random();

        islandMapping = new HashMap<>();
        initMapping(islandMapping, numberIslands);
        generateRandomConnections(islandMapping);
        System.out.println(islandMapping);
    }

    public static void main(String[] args) {
        FileWriter fw;
        try {
            fw = new FileWriter(new File("input\\testInput1.txt"));
            for (int i = 3; i < RUNS + 3; i++) {
                fw.write(new IslandGenerator2(i).toString() + '\n');
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //new IslandGenerator();
    }

    private void initMapping(Map<Integer, List<Integer>> islandMapping, int numberIslands) {
        for (int i = 0; i < numberIslands; i++) {
            islandMapping.put(i, new LinkedList<>());
        }
    }

    private void generateRandomConnections(Map<Integer, List<Integer>> islandMapping) {
        for (int startIsland : islandMapping.keySet()) {
            List<Integer> list = islandMapping.get(startIsland);
            for (int endIsland : islandMapping.keySet()) {
                if (startIsland != endIsland) {
                    List<Integer> list2 = islandMapping.get(endIsland);
                    if (!list2.contains(startIsland)) {
                        Islands.addIfNotPresent(list, endIsland);
                    }
                }
            }
        }
        for (int i : islandMapping.keySet()) {
            List<Integer> list = islandMapping.get(i);
            Collections.sort(list);
        }
    }

    public String toString() {
        String result = "";
        for (int startIsland : islandMapping.keySet()) {
            List<Integer> list = islandMapping.get(startIsland);
            for (int endIsland : list) {
                result += startIsland + " " + endIsland + " ";
            }
        }
        return result;
    }
}
