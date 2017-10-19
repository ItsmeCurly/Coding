package Tree;

public class BinaryT<E> {
	private BinaryNode<E> root;
	
	public BinaryT() {
		this(null);
	}
	
	public BinaryT(BinaryNode<E> root) {
		this.setRoot(root);
	}

	/**
	 * @return the root
	 */
	public BinaryNode<E> getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(BinaryNode<E> root) {
		this.root = root;
	}
	
	public void preOrder(BinaryNode<E> node) {
		if(node == null) return;
		System.out.print(node.getElement());
		preOrder(node.getLeft());
		preOrder(node.getRight());
	}
}