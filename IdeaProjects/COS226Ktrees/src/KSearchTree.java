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
        if (search(k) == 1) {
            delete(root, k);
        }
    }

    private void delete(TreeNode node, int k) {
        if (node.getChild().getKey() == k) deleteNode(node);
        else delete(node.getNextSibling(), k);
    }

    private void deleteNode(TreeNode node) {

    }


    public void store(TreeNode node) {
        if (search(node.getKey()) == 0) {
            if (size() == 1) {
                TreeNode parent = createPair(root);
                setRoot(parent);
            }
            if (node.getKey() < root.getChild().getKey()) {
                TreeNode parent = createPair(node);
                parent.setNextSibling(root);
                setRoot(parent);
            }
            insert(node, root);
        }
    }

    private void insert(TreeNode insert, TreeNode node) {
        if (node.getChild().compareTo(insert) > 0) {
            TreeNode parent = createPair(insert);
            node.setNextSibling(parent);
        } else if (node.getNextSibling() != null) {
            insert(insert, node.getNextSibling());
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