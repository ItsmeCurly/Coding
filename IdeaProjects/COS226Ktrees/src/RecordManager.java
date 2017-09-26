public class RecordManager {
    private KSearchTree kst;

    public RecordManager() {
        this(0);
    }

    public RecordManager(int k) {
        kst = new KSearchTree(k);
    }

    public KSearchTree getKst() {
        return kst;
    }

    public int search(int key) {
        return kst.search(key);
    }

    public void store(TreeNode node) {
        kst.store(node);
    }

    public TreeNode searchNode(int key) {
        return kst.searchN(key);
    }

    public void delete(int key) {
        kst.delete(key);
    }

    public int size() {

    }

    public int height() {

    }

    @Override
    public String toString() {
        return kst.toString();
    }
}
