public class INTERVIEWtreetester {
    public static void main(String []args) {
        T<String> tree = new T<String>();

        Node<String> a = new Node<String>("Start");
        Node<String> b = new Node<String>("A1");
        Node<String> c = new Node<String>("D1");
        Node<String> d = new Node<String>("E1");
        Node<String> e = new Node<String>("A2");
        Node<String> f = new Node<String>("B1");
        Node<String> g = new Node<String>("Find Me");
        Node<String> h = new Node<String>("B2");
        Node<String> i = new Node<String>("C1");

        a.addChild(b);
        a.addChild(e);
        b.addChild(c);
        c.addChild(d);
        e.addChild(f);
        f.addChild(g);
        e.addChild(h);
        h.addChild(i);

        tree.setRoot(a);

        System.out.println(tree.search("Find Me"));
    }
}
