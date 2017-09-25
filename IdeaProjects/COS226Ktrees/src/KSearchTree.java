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
        this.root = root;
        this.k = k;
    }

    public void store(TreeNode node) {
        if (search(node.getKey()) > 0) {
            insert(node);
        }
    }

    private void insert(TreeNode node) {
        //TODO
    }


    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public int search(int key) {
        return 0;
        //TODO
    }

    private int search(TreeNode node, int key) {
        //TODO
        return 0;
    }

    public TreeNode searchNode(int key) {
        return new TreeNode();
        //TODO
    }

    public void delete(int k) {
        //TODO
    }

    public int size() {
        return 0;
    }

    public int height() {
        return 0;
    }



    @Override
    public String toString() {
        return "KSearchTree{" +
                "root=" + root +
                '}';
    }
}