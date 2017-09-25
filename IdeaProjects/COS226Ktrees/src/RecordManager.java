public class RecordManager {
    private KSearchTree kst;

    public RecordManager() {
        this(0);
    }

    public RecordManager(int k) {
        kst = new KSearchTree(k);
    }


}
