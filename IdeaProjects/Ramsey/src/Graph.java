import java.util.ArrayList;
import java.util.Iterator;

public class Graph<E> {
    private ArrayList<E> nodes = new ArrayList<>();
    private ArrayList<Connection<E>> connections = new ArrayList<>();

    public Graph() {    //default constructor

    }

    public void addNode(E data) {   //adds a node with E data to the graph
        if (!containsNode(data))
            nodes.add(data);
    }

    public boolean containsNode(E data) {   //checks if the graph contains the node data
        return nodes.contains(data);
    }

    public ArrayList<E> getNodes() {    //returns the nodes
        return nodes;
    }

    public void removeNode(E data) {    //removes a node from the graph
        nodes.remove(data);
    }

    public int getNodeDegree(E data) {  //returns a node's degree
        int counter = 0;
        if(containsNode(data)) {
            for(Connection<E> conn : connections) {
                if(conn.singleDataMatch(data)) {
                    counter += 1;
                }
            }
            return counter;
        }
        return -1;
    }
    public boolean hasMoreNodes() { //returns if the graph has any nodes
        return nodes.size() > 0;
    }

    public Connection<E> findConnection(E data, E otherData) {  //finds the connection object in the arraylist from data and otherdata
        for(Connection<E> conn : connections) {
            if(conn.doubleDataMatch(data, otherData)) {
                return conn;
            }
        }
        return null;
    }

    public boolean containsConnection(E data, E otherData) {    //checks if graph has connection between data and otherdata
        return connections.contains(findConnection(data, otherData));
    }

    public ArrayList<Connection<E>> removeConnections(E data) { //remove all connections for a certain node with E data
        Iterator<Connection<E>> iter = connections.iterator();
        ArrayList<Connection<E>> list = new ArrayList<>();
        for ( ;iter.hasNext(); ) {
            Connection<E> conn = iter.next();
            if(conn.singleDataMatch(data)) {
                iter.remove();
                list.add(conn);
            }
        }
        return list;
    }

    public ArrayList<Connection<E>> removeNonConnections(E data) {  //remove connections that AREN'T connected to data
        Iterator<Connection<E>> iter = connections.iterator();
        ArrayList<Connection<E>> list = new ArrayList<>();
        for ( ;iter.hasNext(); ) {
            Connection<E> conn = iter.next();
            if(!conn.singleDataMatch(data)) {
                if(!(isConnected(data, conn.getData()) || isConnected(data, conn.getOtherData()))){
                    iter.remove();
                    list.add(conn);
                }
            }
        }
        return list;
    }

    private boolean isConnected(E data, E otherData) {  //checks if two data points are connected together
        return containsConnection(data, otherData);
    }

    public void addConnection(E original, E other) {    //adds a connection to the graph
        if(!containsConnection(original, other))
            connections.add(new Connection<E>(original, other));
    }

    public void addConnection(Connection<E> connection) {   //adds a preexisting connection to the graph
        if(!containsConnection(connection.getData(), connection.getOtherData()))
            connections.add(connection);
    }

    @Override
    public String toString() { //prints the graph in the specified format from the homework
        ArrayList<E> newNodes = new ArrayList<>();
        newNodes.addAll(nodes);
        ArrayList<E> connect = new ArrayList<>();
        for(Connection<E> conn : this.connections) {
            if(!connect.contains(conn.getData()))
                connect.add(conn.getData());
            if(!connect.contains(conn.getOtherData()))
                connect.add(conn.getOtherData());
        }

        for(E data : connect) {
            Iterator<E> iter = newNodes.iterator();
            for(;iter.hasNext();) {
                if(data.equals(iter.next())) {
                    iter.remove();
                }
            }
        }
        String s = connect.size() > 0 || newNodes.size() > 0 ? "(" : "";
        for(Connection<E> conn : connections) {
            s += "(" + conn.getData() + " " + conn.getOtherData() + ")";
        }
        for(E data : newNodes) {
            s += "(" + data + ")";
        }
        s += connect.size() > 0 || newNodes.size() > 0 ? ")" : "";
        return s;
    }

    public static class Connection<E> { //helper class for connections in the graph
        private E data;
        private E otherData;

        Connection(E data, E otherData) {

            if((char)data < (char)otherData) { //orders data in lexicon order for ease later
                this.data = data;
                this.otherData = otherData;
            }
            else {
                this.otherData = data;
                this.data = otherData;
            }
        }
        //returns that for one data point that this connection contains this
        boolean singleDataMatch(E data) {
            return data.equals(this.data) || data.equals(this.otherData);
        }
        //returns that for two data points that this connection matches them
        boolean doubleDataMatch(E data, E otherData) {
            return (data.equals(this.data) || data.equals(this.otherData)) && (otherData.equals(this.data) || otherData.equals(this.otherData));
        }
        //given a connection's data, return the other side of the connection's data
        public E getOtherData(E data) {
            if(data == this.data)
                return otherData;
            return data;
        }
        //returns the data variable
        E getData() {
            return data;
        }
        //returns the otherData variable
        E getOtherData() {
            return otherData;
        }

        @Override
        public String toString() { //connection toString
            return "Connection: " + data + " - " + otherData;
        }
    }
}
