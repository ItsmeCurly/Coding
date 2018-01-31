import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Runner {

    public Runner(String input) {
        Graph<Character> graph = new Graph<>();
        StringTokenizer st = new StringTokenizer(input,"(");
        while(st.hasMoreTokens())
            addNode(st.nextToken(), graph);
        findCliques(graph);
    }

    private void addNode(String s, Graph<Character> graph) {
        String input = s;
        while(input.contains(")")) {
            input = input.substring(0, input.length()-1);
        }
        input = input.trim();
        char data = input.charAt(0);
        if(input.length() == 1) //SINGLE NODE
            graph.addNode(data);
        else {
            graph.addNode(data);
            char otherData = input.charAt(2);
            graph.addNode(otherData);
            graph.addConnection(data, otherData);
        }
    }

    public static void findCliques(Graph<Character> graph) {
        Graph<Character> clique = new Graph<>();
        Graph<Character> antiClique = new Graph<>();
        ArrayList<Graph.Connection<Character>> list;
        while (graph.hasMoreNodes()) {
            char data = graph.getNodes().get(0);
            if(graph.getNodeDegree(data) > graph.getNodes().size()/2) {
                clique.addNode(data);
                list = graph.removeNonConnections(data);
                for (Graph.Connection<Character> conn : list) {
                    graph.getNodes().remove(conn.getOtherData(data));
                }
                list = graph.removeConnections(data);
                for (Graph.Connection<Character> conn : list) {
                    char newData = conn.getOtherData(data);
                    graph.removeNode(newData);
                    clique.addNode(newData);
                    clique.addConnection(conn);
                }
                graph.removeNode(data);
                //DISCARD ALL NODES THAT AREN'T CONNECTED
                //ADD ALL NODES THAT ARE CONNECTED
            }
            else {
                antiClique.addNode(data);
                list = graph.removeConnections(data);
                for(Graph.Connection<Character> conn : list)
                    graph.getNodes().remove(conn.getOtherData(data));
                graph.removeNode(data);
                //DISCARD ALL NODES THAT ARE CONNECTED
                //KEEP NODES THAT AREN'T CONNECTED IN GRAPH
            }
        }
        System.out.println("Cliques : " + clique);
        System.out.println("Anticliques : " + antiClique);
    }

    public static void main(String [] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the graph by dictations stated in question: ");
        String s = scan.nextLine();
        scan.close();
        new Runner(s);
    }
}