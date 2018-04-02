import java.util.*;

public class Islands {
    private Map<Integer, List<Integer>> nextIslands;
    private int startIsland;

    private LinkedList<Integer> longestRoute;

    public Islands(List<String> pairs) {
        nextIslands = new HashMap<>();
        longestRoute = new LinkedList<>();
        createIslands(pairs);
        //System.out.println(nextIslands);

        LinkedList<Integer> route = new LinkedList<>();
        route.add(startIsland);

        findPath(copyMapping(nextIslands), route);
        System.out.println(longestRoute.size() - 1);
        for (int i : longestRoute) {
            System.out.println(i);
        }

    }

    private void createIslands(List<String> pairs) {
        for (int i = 0; i < pairs.size(); i++) {

            String[] pairSplit = pairs.get(i).split(" ");
            if (i == 0) {
                startIsland = Integer.parseInt(pairSplit[0]);
            }
            List<Integer> next = nextIslands.computeIfAbsent(Integer.parseInt(pairSplit[0]), k -> new ArrayList<>());

            if (!next.contains(Integer.parseInt(pairSplit[1]))) {
                next.add(Integer.parseInt(pairSplit[1]));
            }

            List<Integer> next2 = nextIslands.computeIfAbsent(Integer.parseInt(pairSplit[1]), k -> new ArrayList<>());

            if (!next2.contains(Integer.parseInt(pairSplit[0]))) {
                next2.add(Integer.parseInt(pairSplit[0]));
            }
        }
    }

    private void findPath(Map<Integer, List<Integer>> temp, LinkedList<Integer> currentRoute) {

        if (currentRoute.getLast() == startIsland && currentRoute.size() > longestRoute.size()) {
            longestRoute = currentRoute;
        }

        //System.out.println(currentRoute);
        for (int i : temp.get(currentRoute.getLast())) {

            LinkedList<Integer> copyRoute = new LinkedList<>(currentRoute);
            copyRoute.add(i);

            Map<Integer, List<Integer>> copyTemp = copyMapping(temp);

            removeSlide(currentRoute.getLast(), i, copyTemp);

            findPath(copyTemp, copyRoute);
        }
    }

    private Map<Integer, List<Integer>> copyMapping(Map<Integer, List<Integer>> mapping) {
        Map<Integer, List<Integer>> temp = new HashMap<>();

        for (int i : mapping.keySet()) {
            temp.put(i, new ArrayList<>(mapping.get(i)));
        }
        return temp;
    }

    private void removeSlide(Integer first, Integer last, Map<Integer, List<Integer>> copyTemp) {
        List<Integer> movableIslands = copyTemp.get(first);
        movableIslands.remove(last);

        List<Integer> otherIslands = copyTemp.get(last);
        otherIslands.remove(first);
    }
}
