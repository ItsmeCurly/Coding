import java.util.ArrayList;
import java.util.Iterator;

public class Graph<E> {
    private ArrayList<E> nodes = new ArrayList<>();
    private ArrayList<Connection<E>> connections = new ArrayList<>();

    public Graph() {

    }

    public void addNode(E data) {
        if (!containsNode(data))
            nodes.add(data);
    }

    public boolean containsNode(E data) {
        return nodes.contains(data);
    }

    public ArrayList<E> getNodes() {
        return nodes;
    }

    public void removeNode(E data) {
        nodes.remove(data);
    }

    public int getNodeDegree(E data) {
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
    public boolean hasMoreNodes() {
        return nodes.size() > 0;
    }
    public ArrayList<Connection<E>> findConns(E data) {
        ArrayList<Connection<E>> connect = new ArrayList<>();
        for(Connection<E> conn : connections) {
            if(conn.singleDataMatch(data)) {
                connect.add(conn);
            }
        }
        return connect;
    }

    public Connection<E> findConnection(E data, E otherData) {
        for(Connection<E> conn : connections) {
            if(conn.doubleDataMatch(data, otherData)) {
                return conn;
            }
        }
        return null;
    }

    public boolean containsConnection(E data, E otherData) {
        return connections.contains(findConnection(data, otherData));
    }

    public ArrayList<Connection<E>> removeConnections(E data) {
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

    public ArrayList<Connection<E>> removeNonConnections(E data) {
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

    private boolean isConnected(E data, E otherData) {
        return containsConnection(data, otherData);
    }

    public void addConnection(E original, E other) {
        if(!containsConnection(original, other))
            connections.add(new Connection<E>(original, other));
    }

    public void addConnection(Connection<E> connection) {
        if(!containsConnection(connection.getData(), connection.getOtherData()))
            connections.add(connection);
    }

    public ArrayList<Connection<E>> getConnections() {
        return connections;
    }

//    @Override
//    public String toString() {
//        return "Graph{" +
//                "nodes=" + nodes +
//                ", connections=" + connections +
//                '}';
//    }
    @Override
    public String toString() {
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

    public static class Connection<E> {
        private E data;
        private E otherData;

        public Connection(E data, E otherData) {

            if((char)data < (char)otherData) {
                this.data = data;
                this.otherData = otherData;
            }
            else {
                this.otherData = data;
                this.data = otherData;
            }
        }

        public boolean singleDataMatch(E data) {
            return data.equals(this.data) || data.equals(this.otherData);
        }

        public boolean doubleDataMatch(E data, E otherData) {
            return (data.equals(this.data) || data.equals(this.otherData)) && (otherData.equals(this.data) || otherData.equals(this.otherData));
        }

        public E getOtherData(E data) {
            if(data == this.data)
                return otherData;
            return data;
        }

        public E getData() {
            return data;
        }

        public E getOtherData() {
            return otherData;
        }

        @Override
        public String toString() {
            return "Connection: " + data + " - " + otherData;
        }
    }
}
