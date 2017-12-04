package Gui;

public class KSearchTree {
    private Gui.TreeNode root;  //the root of the tree
    private int k;  // k value of tree

    public KSearchTree() {
        this(0);
    }

    public KSearchTree(int k) {
        this(null, k);
    }

    public KSearchTree(Gui.TreeNode root, int k) {
        this.k = k;
        if (root != null) { //if root is not null, set root as l-node's parent(created here)
            Gui.TreeNode parent = createPair(root);
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

    private int search(Gui.TreeNode node, int key) {
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
    public Gui.TreeNode searchN(int key) {
        return searchNode(root, key);
    }

    public Gui.TreeNode searchNode(Gui.TreeNode node, int key) {
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

    private boolean delete(Gui.TreeNode node, Gui.TreeNode prevNode, int key) {
        if (node.getChild() != null && node.getChild().getKey() == key)
            prevNode.setNextSibling(node.getNextSibling());
        else return delete(node.getNextSibling(), node, key);
        return true;
    }

    /**
     * @param node: the node to store
     */
    public void store(Gui.TreeNode node) {
        if (size() == 0) {
            Gui.TreeNode parent = createPair(node);
            setRoot(parent);
        } else insert(node, root, null);
    }

    private void insert(Gui.TreeNode insert, Gui.TreeNode node, Gui.TreeNode prevNode) {
        if (node.getChild().compareTo(insert) == 0) {
            System.out.println("a");
            node.getChild().setData(insert.getData());
        } else if (node.getChild().compareTo(insert) > 0) {
            Gui.TreeNode parent = createPair(insert);
            if (prevNode != null) prevNode.setNextSibling(parent);
            parent.setNextSibling(node);
            if (node.getChild().compareTo(root.getChild()) == 0)
                setRoot(parent);
        } else if (node.getNextSibling() != null) {
            insert(insert, node.getNextSibling(), node);
        } else {
            Gui.TreeNode parent = createPair(insert);
            node.setNextSibling(parent);
        }
    }

    private Gui.TreeNode createPair(Gui.TreeNode node) {    //creates a pair of nodes, i-node and l-node
        Gui.TreeNode parent = new Gui.TreeNode();
        parent.setINode();
        parent.setChild(node);
        return parent;
    }

    /**
     * @return root: the root of the tree
     */
    public Gui.TreeNode getRoot() {
        return root;
    }

    /**
     * @param root: the root to set
     */
    public void setRoot(Gui.TreeNode root) {
        this.root = root;
    }

    /**
     * @return: the size of the tree
     */
    public int size() {
        Gui.TreeNode node = root;
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
        Gui.TreeNode node = root;
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