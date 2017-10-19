package Tree;

public class BinaryNode<E> {
	private BinaryNode<E> left;
	private BinaryNode<E> right;
	
	private E element;
	
	public BinaryNode() {
		this(null, null, null);
	}
	
	public BinaryNode(E element) {
		this(element, null, null);
	}
	
	public BinaryNode(E element, BinaryNode<E> left, BinaryNode<E> right) {
		this.setElement(element);
		this.left = left;
		this.right = right;
	}
	
	public BinaryNode<E> getLeft() {
		return left;
	}
	
	public void setLeft(BinaryNode<E> newLeft) {
		left = newLeft;
	}
	
	public BinaryNode<E> getRight() {
		return right;
	}
	
	public void setRight(BinaryNode<E> newRight) {
		left = newRight;
	}

	/**
	 * @return the element
	 */
	public E getElement() {
		return element;
	}

	/**
	 * @param element the element to set
	 */
	public void setElement(E element) {
		this.element = element;
	}
}
