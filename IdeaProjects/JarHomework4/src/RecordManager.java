package Gui;

/**
 * This class doesn't serve as a class on itself, it serves as a passerby for the creation of new KSearchTree
 * units within main, and main is the main class that runs the code.
 */
public class RecordManager {
    private Gui.KSearchTree kst;

    public RecordManager() {
    }

    public RecordManager(int k) {
        kst = new Gui.KSearchTree(k);
    }

    public void makeNew(int k) {
        kst = new Gui.KSearchTree(k);
    }

    public Gui.KSearchTree getKst() {
        return kst;
    }

    public int search(int key) {
        return kst.search(key);
    }

    public void store(Gui.TreeNode node) {
        kst.store(node);
    }

    public Gui.TreeNode searchNode(int key) {
        return kst.searchN(key);
    }

    public boolean delete(int key) {
        return kst.delete(key);
    }

    public int size() {
        return kst.size();
    }

    public int height() {
        return kst.height();
    }

    @Override
    public String toString() {
        return (kst != null) ? kst.toString() : "No tree";
    }
}
