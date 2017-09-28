/**
 * This class doesn't serve as a class on itself, it serves as a passerby for the creation of new KSearchTree
 * units within main, and main is the main class that runs the code.
 */
public class RecordManager {
    private KSearchTree kst;

    public RecordManager() {
        this(0);
    }

    public RecordManager(int k) {
        kst = new KSearchTree(k);
    }

    public void makeNew(int k) {
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
        return kst.size();
    }

    public int height() {
        return kst.height();
    }

    @Override
    public String toString() {
        return kst.toString();
    }
}
