import java.util.List;
import java.util.LinkedList;

public class Node<E> {
	private List<Node<E>> children;
	private E element;
	public Node() {
		this(null, new LinkedList<Node<E>>());
	}
	
	public Node(E element) {
		this(element, new LinkedList<Node<E>>());
	}
	
	public Node(E element, List<Node<E>> children) {
		this.setElement(element);
		this.children = children;
	}

	public LinkedList<Node<E>> getChildren() {
		return (LinkedList<Node<E>>)children;
	}

	public void setChildren(List<Node<E>> children) {
		this.children = children;
	}

	public void addChild(Node<E> child) {
		children.add(child);
	}

	public boolean hasChildren() {
		return !children.isEmpty();
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
