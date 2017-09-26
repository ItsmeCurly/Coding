import java.util.LinkedList;

public class KSearchTree {
    private TreeNode root;
    private int k;

    public KSearchTree() {
        this(0);
    }

    public KSearchTree(int k) {
        this(null, k);
    }

    public KSearchTree(TreeNode root, int k) {
        this.k = k;
        if (root != null) {
            TreeNode parent = createPair(root);
            root = parent;
        }
    }

    public int search(int key) {
        return search(root, key);
    }

    private int search(TreeNode node, int key) {
        if (node != null) {
            if (node.getChild() != null) {
                if (node.getChild().getKey() == key)
                    return 1;
                return search(node.getNextSibling(), key);
            }
        }
        return 0;
    }

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

    public void delete(int key) {
        if (search(key) == 1) {
            if (key == root.getChild().getKey()) {
                if (root.getNextSibling() == null) root = null;
                else setRoot(root.getNextSibling());
            } else delete(root, null, key);
        }
    }

    private void delete(TreeNode node, TreeNode prevNode, int key) {
        if (node.getChild() != null && node.getChild().getKey() == key) prevNode.setNextSibling(node.getNextSibling());
        else delete(node.getNextSibling(), node, key);
    }


    public void store(TreeNode node) {
        if (size() == 0) {
            TreeNode parent = createPair(node);
            setRoot(parent);
        } else insert(node, root, null);
    }

    private void insert(TreeNode insert, TreeNode node, TreeNode prevNode) {
        if (node.getChild().compareTo(insert) == 0) node.setData(insert.getData());
        else if (node.getChild().compareTo(insert) > 0) {
            TreeNode parent = createPair(insert);
            if (prevNode != null) prevNode.setNextSibling(parent);
            parent.setNextSibling(node);
            if (node.getChild().compareTo(root.getChild()) == 0) setRoot(parent);
        } else if (node.getNextSibling() != null) {
            insert(insert, node.getNextSibling(), node);
        } else {
            TreeNode parent = createPair(insert);
            node.setNextSibling(parent);
        }
    }

    private TreeNode createPair(TreeNode node) {
        TreeNode parent = new TreeNode();
        parent.setINode();
        parent.setChild(node);
        return parent;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

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

    public int height() {
        return 0;
    }

    @Override
    public String toString() {
        if (root != null) {
            return root.toString();
        } else return "Empty";
    }
}