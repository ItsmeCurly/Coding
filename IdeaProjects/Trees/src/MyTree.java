package Tree;

import java.util.LinkedList;
import java.util.List;

public class MyTree<E> implements Tree<E> {
	private TreeNode<E> root;
	
	public MyTree (TreeNode<E> root) {
		this.root = (MyTreeNode<E>)root;
	}
	
	public TreeNode<E> getRoot() {
		return root;
	}

	/**	
	 * @return The number of nodes in this tree which will be >= 0.
	 */
	public int size() {
		return getPreOrder().size();
	}

	/**
	 * @return The length of the path from the root node to the deepest leaf, if
	 *         the tree is nonempty, and 0 otherwise.
	 * 
	 * @see #height(TreeNode node) 
	 * @see #depth(TreeNode node) 
	 */
	public int height() {
		return root.height();
	}

	/**
	 * @return The elements in the tree in an order that ensures parents
	 * are displayed before any of their children have been displayed.
	 */
	public List<TreeNode<E>> getPreOrder() {
		return root.getPreOrder();
	}

	/**
	 * @return The elements in the tree in an order that ensures parents
	 * are displayed after all their children have been displayed.
	 */
	public List<TreeNode<E>> getPostOrder() {
		return root.getPostOrder();
	}

	/**
	 * Removes all nodes from this tree.
	 */
	public void makeEmpty() {
		root = null;
	}

	/**
	 * @return true if this tree has no nodes.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * The height of a node in the tree is the length of the path from the node
	 * to the deepest leaf in its subtree. See page 596 of the textbook.
	 * 
	 * @param node
	 *            The node whose height is desired; it must not be null.
	 * @return The height of the node in the tree or -1 if the node is not in
	 *         the tree.
	 */
	public int height(TreeNode<E> node) {
		return node.height();
	}

	/**
	 * The depth of a node in the tree is the length of the path from the root
	 * to the node. See page 596 of the textbook.
	 * 
	 * @param node
	 *            The node whose depth is desired; it must not be null.
	 * @return The depth of the node in the tree or -1 if the node is not in the
	 *         tree.
	 */
	public int depth(TreeNode<E> node) {
		List<TreeNode<E>> nodes = new LinkedList<TreeNode<E>>();
		nodes.add(root);
		return depth(nodes, 0, node.getElement());
	}
	
	public int depth(List<TreeNode<E>> sameLevelNodes, int level, E element) {
		List<TreeNode<E>> children = new LinkedList<TreeNode<E>>();
        for (TreeNode<E> node : sameLevelNodes) {
            if (node.getElement() == element)
                return level;
            if (node.hasChildren())
                children.addAll(node.getChildren());
        }
        if (!children.isEmpty())
            return depth(children, level + 1, element);
        
        return -1;
	}
}

