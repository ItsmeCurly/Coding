

public class TreeNode implements Comparable<TreeNode> {
    private int key;
    private String data;

    //neighboring treenodes for leftmost-child right-sibling BT
    private TreeNode leftChild;
    private TreeNode rightSibling;

    //default constructor
    public TreeNode() {
        this(0, null);
    }

    //constructor with key and data
    public TreeNode(int key, String data) {
        this.key = key;
        this.data = data;
    }

    //accessors and mutators
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int compareTo(TreeNode o) {
        return 0;
        //TODO
    }

    @Override
    public String toString() {
        return this.key + ": " + this.data;
    }
}
