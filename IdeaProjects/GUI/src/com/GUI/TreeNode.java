package com.GUI;

public class TreeNode implements Comparable<TreeNode> {
    //node key:data pair
    private int key;
    private String data;

    private int id; //i node is 0, l node is 1

    //implementation of linked list
    private TreeNode nextSibling;
    private TreeNode child;

    //default constructor
    public TreeNode() {
        this(0, null);
        this.id = 0;
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
    }

    public TreeNode getNextSibling() {
        return nextSibling;
    }

    public void setNextSibling(TreeNode nextSibling) {
        this.nextSibling = nextSibling;
    }


    /**
     * @param o: the other com.GUI.TreeNode to compare to the original
     * @return int:  if equal return 0, if other is greater return -1, if greater return 1
     */
    public int compareTo(TreeNode o) {
        return Integer.compare(getKey(), o.getKey());
    }

    private String print(int depth) {
        String space = "";
        for (int i = 0; i < depth; i++) space += "   ";
        if (getId() == 0)
            return "O -- " + ((child != null) ? child.toString() : "empty") +
                    ((getNextSibling() != null) ? ("\n" + space + getNextSibling().print(depth + 1)) : "");
        else return this.key + " " + this.data;
    }

    @Override
    /**
     * @return: return string of the data in the treenode, differing between the i-node and l-node
     */
    public String toString() {
        return print(1);

    }
}
