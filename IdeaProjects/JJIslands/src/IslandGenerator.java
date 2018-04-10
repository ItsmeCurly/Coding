import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class IslandGenerator {
    public static final int RUNS = 100;
    private List<Integer> islands;
    private Map<Integer, List<Integer>> islandMapping;
    private Random rand;

    public IslandGenerator() {
        rand = new Random();
        islands = new ArrayList<>();
        int numberIslands = rand.nextInt(10) + 5;
        for (int i = 0; i < numberIslands; i++) {
            islands.add(i);
        }

        initMapping(numberIslands);
        List<Integer> list = islandMapping.get(0);
        Islands.addIfNotPresent(list, rand.nextInt(numberIslands) + 1); //ensures at least one connection
        for (int i = 0; i < numberIslands; i++) {
            generateRandomConnections(islandMapping, numberIslands);
        }
    }

    public static void main(String[] args) {
        FileWriter fw;
        try {
            fw = new FileWriter(new File("input\\testInput.txt"));
            for (int i = 0; i < RUNS; i++) {
                fw.write(new IslandGenerator().toString() + '\n');
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //new IslandGenerator();
    }

    private void initMapping(int numberIslands) {
        islandMapping = new HashMap<>();
        for (int i = 0; i < numberIslands; i++) {
            islandMapping.put(i, new LinkedList<>());
        }
    }

    private void generateRandomConnections(Map<Integer, List<Integer>> islandMapping, int numIslands) {
        for (int startIsland : islandMapping.keySet()) {
            List<Integer> list = islandMapping.get(startIsland);
            for (int endIsland : islandMapping.keySet()) {
                if (startIsland != endIsland) {
                    int num = rand.nextInt(1000);
                    if (num < 50 - numIslands * 3) {
                        List<Integer> list2 = islandMapping.get(endIsland);
                        if (!list2.contains(startIsland)) {
                            Islands.addIfNotPresent(list, endIsland);
                        }
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
