import java.util.LinkedList;
import java.util.List;


public class MyTreeNode<E> implements TreeNode<E> {
	E element;
	
	TreeNode<E> lastChild;
	TreeNode<E> prevSibling;
	
	public MyTreeNode(E elem) {
		this.element = elem;
	}
	
	public E getElement() {
		return element;
	}

	/**
	 * Set this node's element to the argument.
	 * 
	 * @param elem
	 *            The element encapsulated by this node.
	 */
	public void setElement(E elem) {
		element = elem;
	}

	/**
	 * Makes the given node a child of this node.
	 * 
	 * @param child
	 *            The node to add as a child; it must not be null.
	 */
	public void setChild(TreeNode<E> child) {
		lastChild = (MyTreeNode<E>)child;
	}

	/**
	 * @return The child that is directly below this node or null if none
	 *         exists.
	 */
	public TreeNode<E> getLastChild() { 
		return lastChild;
	}
	
	/**
	 * @return All of this node's descendents that are directly below this node
	 *         in the tree's hierarchy. Children in the list should be ordered
	 *         according to the next sibling relationship.
	 */
	public List<TreeNode<E>> getChildren() {
		List<TreeNode<E>> childrenList = new LinkedList<TreeNode<E>>();
		TreeNode<E> temp = this.getLastChild();
		
		while (temp != null) {
			childrenList.add(temp);
			temp = temp.getPrevSibling();
		}
		return childrenList;
	}

	/**
	 * Makes the known next sibling of this node sibling.
	 * 
	 * @param sibling
	 *            Will become this node's sibling; it must not be null.
	 */
	public void setPrevSibling(TreeNode<E> sibling) {
		this.prevSibling = (MyTreeNode<E>)sibling;
	}

	/**
	 * @return The sibling of this node or null if none exists.
	 */
	public TreeNode<E> getPrevSibling() {
		return this.prevSibling;
	}

	/**
	 * @return The number of nodes belonging to this node's subtree, including
	 *         this node.
	 */
	public int size() {
		return getPreOrder().size();
	}

	/**
	 * The height of any node is 1 more than the height of its maximum-height
	 * child.
	 * 
	 * @return The height of this node in the tree.
	 */
	public int height() {
		return height(this) - 1;
	}
	
	private int height(TreeNode<E> node) {
		if (node == null) 
			return 0;
		int height = 0;
		for(TreeNode<E> child: node.getChildren())
			height = Math.max(height, height(child));
		return height + 1;
	}
	
	
	/**
	 * @return The elements in the subtree rooted at this node in an order that
	 *         ensures parents occur before any of their children.
	 */
	public List<TreeNode<E>> getPreOrder() {
		List<TreeNode<E>> preOrder = new LinkedList<TreeNode<E>>();
		getPreOrder(this, preOrder);
		return preOrder;
	}
	
	private void getPreOrder(TreeNode<E> node, List<TreeNode<E>> preOrder) {
		if(node == null) 
			return;
		preOrder.add(node);
		
		for(TreeNode<E> child : node.getChildren())
			getPreOrder(child, preOrder);
	}

	/**
	 * @return The elements in the subtree rooted at this node in an order that
	 *         ensures parents occur after all of their children.
	 */
	public List<TreeNode<E>> getPostOrder() {
		List<TreeNode<E>> postOrder = new LinkedList<TreeNode<E>>();
		getPostOrder(this, postOrder);
		
		return postOrder;
	}
	
	private void getPostOrder(TreeNode<E> node, List<TreeNode<E>> postOrder) {
		if(node == null) 
			return;
		for(TreeNode<E> child : node.getChildren())
			getPostOrder(child, postOrder);
		
		postOrder.add(node);
		
		
	}
	public boolean hasChildren() {
		return !getChildren().isEmpty();
	}
	public String toString() {
		return element.toString();
	}
}
