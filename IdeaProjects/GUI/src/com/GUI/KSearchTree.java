package com.GUI;

public class KSearchTree {
    private TreeNode root;  //the root of the tree
    private int k;  // k value of tree

    public KSearchTree() {
        this(0);
    }

    public KSearchTree(int k) {
        this(null, k);
    }

    public KSearchTree(TreeNode root, int k) {
        this.k = k;
        if (root != null) { //if root is not null, set root as l-node's parent(created here)
            TreeNode parent = createPair(root);
            root = parent;
        }
    }

    /**
     * @param key: the key of the root to search
     * @return return 1 if found, 0 if not
     */
    public int search(int key) {
        return search(root, key);
    }

    private int search(TreeNode node, int key) {
        if (node != null)
            if (node.getChild() != null) {
                if (node.getChild().getKey() == key)
                    return 1;
                return search(node.getNextSibling(), key);
            }
        return 0;
    }

    /**
     * @param key: the key of the root to search
     * @return return node if found, null if not
     */
    public TreeNode searchN(int key) {
        return searchNode(root, key);
    }

    public TreeNode searchNode(TreeNode node, int key) {
        if (node != null) {
            if (node.getChild().getKey() == key) {
                return node.getChild();
            }
            return searchNode(node.getNextSibling(), key);
        }
        return null;
    }

    /**
     * @param key: key of node to find
     */
    public boolean delete(int key) {
        if (search(key) == 1) {
            if (key == root.getChild().getKey()) {
                if (root.getNextSibling() == null) root = null;
                else setRoot(root.getNextSibling());
                return true;
            } else return delete(root, null, key);
        } else
            return false;
    }

    private boolean delete(TreeNode node, TreeNode prevNode, int key) {
        if (node.getChild() != null && node.getChild().getKey() == key)
            prevNode.setNextSibling(node.getNextSibling());
        else return delete(node.getNextSibling(), node, key);
        return true;
    }

    /**
     * @param node: the node to store
     */
    public void store(TreeNode node) {
        if (size() == 0) {
            TreeNode parent = createPair(node);
            setRoot(parent);
        } else insert(node, root, null);
    }

    private void insert(TreeNode insert, TreeNode node, TreeNode prevNode) {
        if (node.getChild().compareTo(insert) == 0)
            node.setData(insert.getData());
        else if (node.getChild().compareTo(insert) > 0) {
            TreeNode parent = createPair(insert);
            if (prevNode != null) prevNode.setNextSibling(parent);
            parent.setNextSibling(node);
            if (node.getChild().compareTo(root.getChild()) == 0)
                setRoot(parent);
        } else if (node.getNextSibling() != null) {
            insert(insert, node.getNextSibling(), node);
        } else {
            TreeNode parent = createPair(insert);
            node.setNextSibling(parent);
        }
    }

    private TreeNode createPair(TreeNode node) {    //creates a pair of nodes, i-node and l-node
        TreeNode parent = new TreeNode();
        parent.setINode();
        parent.setChild(node);
        return parent;
    }

    /**
     * @return root: the root of the tree
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * @param root: the root to set
     */
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /**
     * @return: the size of the tree
     */
    public int size() {
        TreeNode node = root;
        int size = 0;
        while (node != null) {
            size += 1;
            if (node.getChild() != null) size += 1;
            node = node.getNextSibling();
        }
        return size;
    }

    /**
     * @return: the height of the tree
     */
    public int height() {
        TreeNode node = root;
        int height = 0;
        while (node != null) {
            height += 1;
            node = node.getNextSibling();
        }
        return height;
    }

    @Override
    public String toString() {
        if (root != null) {
            return root.toString();
        } else return "Empty";
    }
}