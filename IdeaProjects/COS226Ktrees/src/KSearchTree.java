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
        if (search(node) > -1) {
            insert(node);
        }
    }

    private void insert(TreeNode node) {

    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public int search(TreeNode node) {
        int k = node.getKey();

        return -1;
    }


    @Override
    public String toString() {
        return "KSearchTree{" +
                "root=" + root +
                '}';
    }
}