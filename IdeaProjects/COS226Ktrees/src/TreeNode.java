

public class TreeNode implements Comparable<TreeNode> {
    private int key;
    private String data;

    private int id; //i node is 0, l node is 1
    //neighboring treenodes for leftmost-child right-sibling BT
    private TreeNode nextSibling;
    private TreeNode child;
    private TreeNode parent;
    private TreeNode previousSibling;

    //default constructor
    public TreeNode() {
        this(0, null);
    }

    //constructor with key and data
    public TreeNode(int key, String data) {
        this.key = key;
        this.data = data;
        this.id = 1;
    }

    //accessors and mutators
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setINode() {
        setId(0);
    }

    public void setLNode() {
        setId(1);
    }

    public TreeNode getPreviousSibling() {
        return previousSibling;
    }

    public void setPreviousSibling(TreeNode previousSibling) {
        this.previousSibling = previousSibling;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public TreeNode getChild() {
        return child;
    }

    public void setChild(TreeNode child) {
        this.child = child;
        child.setParent(this);
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getNextSibling() {
        return nextSibling;
    }

    public void setNextSibling(TreeNode nextSibling) {
        if (this.nextSibling != nextSibling) {
            nextSibling.setNextSibling(this.nextSibling);
            this.nextSibling = nextSibling;
        }

    }

    public int compareTo(TreeNode o) {
        if (this.getKey() == o.getKey())
            return 0;
        else if (this.getKey() < o.getKey())
            return -1;
        else
            return 1;
    }

    @Override
    public String toString() {
        return this.key + ": " + this.data;
    }
}
