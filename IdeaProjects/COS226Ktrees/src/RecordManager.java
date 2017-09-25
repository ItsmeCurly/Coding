public class RecordManager {
    private KSearchTree kst;

    public RecordManager() {
        this(0);
    }

    public RecordManager(int k) {
        kst = new KSearchTree(k);
    }

    public int search(int k) {
        return kst.search(k);
    }

    public void store(TreeNode node) {
        kst.store(node);
    }

    public TreeNode searchNode(int k) {
        return kst.searchNode(k);
    }

    public void delete(int k) {
        kst.delete(k);
    }
}
