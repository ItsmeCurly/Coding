import java.util.LinkedList;
import java.util.List;

public class T<E> {
	private Node<E> root;   //root of the tree
	
	public T() {
		this(null);
	}
	
	public T(Node<E> root) {
		this.setRoot(root);
	}

	/**
	 * @return the root
	 */
	public Node<E> getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(Node<E> root) {
		this.root = root;
	}

    /**
     *
     * @param element the element to be searching for
     * @return whether the search was successful or not
     */
	public boolean search(E element) {
        List<Node<E>> nodes = new LinkedList<Node<E>>();
        nodes.add(root);
		return search(nodes, element);
	}

    /**
     *
     * @param children list of the node's children to search
     * @param element the element to be searching for
     * @return whether the search was successful or not
     */
	private boolean search(List<Node<E>> children, E element)
    {
        List<Node<E>> list = new LinkedList<>();

        for (Node<E> node : children) {
            System.out.println(node.getElement());
            if (node.getElement().equals(element))
                return true;
            if (node.hasChildren())
                list.addAll(node.getChildren());
        }
        if (!children.isEmpty())
            return search(list, element);

        return false;
    }
}