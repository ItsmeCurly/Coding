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
        this.root = root;
    }

    public int search(int key) {
        return search(root, key);
    }

    private int search(TreeNode node, int key) {
        if (node != null) {
            if (node.getKey() == key) {
                return 1;
            }
            return Math.max(search(node.getChild(), key), search(node.getNextSibling(), key));
        }
        return 0;
    }

    public TreeNode searchN(int key) {
        return searchNode(root, key);
    }

    public TreeNode searchNode(TreeNode node, int key) {
        if (node != null) {
            if (node.getKey() == key) {
                return node;
            }
            TreeNode child = searchNode(node.getChild(), key);
            TreeNode sibling = searchNode(node.getNextSibling(), key);
            if (child != null)
                return child;
            else if (sibling != null)
                return sibling;
        }
        return null;
    }

    public void delete(int k) {
        //TODO
    }

    public void store(TreeNode node) {
        if (search(node.getKey()) <= 0) {
            if (size() == 1) {
                createPair(node);
            }
            insert(node, root);
        }
    }

    private void insert(TreeNode insert, TreeNode node) {
        if (node != null) {
            if (node.getChild().compareTo(insert) > 0) {
                TreeNode parent = createPair(insert);
                node.setNextSibling(parent);
            } else {
                insert(insert, node.getNextSibling());
            }
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
        return "KSearchTree{" +
                "root=" + root.toString() +
                '}';
    }
}