

public class TreeNode implements Comparable<TreeNode> {
    //node key:data
    private int key;
    private String data;

    private int id; //i node is 0, l node is 1

    //implementation of linked list
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

    /**
     * @param o: the other TreeNode to compare to the original
     * @return: if equal return 0, if other is greater return -1, if greater return 1
     */
    public int compareTo(TreeNode o) {
        if (this.getKey() == o.getKey())
            return 0;
        else if (this.getKey() < o.getKey())
            return -1;
        else
            return 1;
    }

    @Override
    /**
     * @return: return string of the data in the treenode, differing between the i-node and l-node
     */
    public String toString() {
        if (getId() == 0) return "O - Child: " + ((child != null) ? child.toString() : "empty") +
                ((getNextSibling() != null) ? ("\n|\n" + getNextSibling().toString()) : "");
        else return this.key + ": " + this.data;
    }
}
