import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * A simple example of using the Tree interface described earlier. We create the
 * tree of Figure 1 and then compare the expected and actual results of some of
 * the methods applied to that tree. The tests are not exhaustive and are only
 * meant to provide a brief example of the use of the interface.
 */
public class TestMyTree {

	private static TreeNode<String> a, b, c, d, e, f, g, h, i, j, k;

	@Test
	public void testMyTree() {
		Tree<String> tree = createFigure18_3Tree();

		// Output format: Expected - Actual
		System.out.println(tree.getPreOrder());
		assertEquals("[a, e, j, k, i, d, h, c, b, g, f]", tree.getPreOrder()
				.toString());

		assertEquals("[f, g, b, c, h, d, i, k, j, e, a]", tree.getPostOrder()
				.toString());

		// Output format: Expected,Actual
		assertEquals(a, tree.getRoot());
		assertEquals(11, tree.size());
		assertEquals(1, k.size());
		assertEquals(4, e.size());
		assertEquals(3, tree.height());
		assertEquals(3, tree.getRoot().height());
		assertEquals(0, tree.height(i));
		assertEquals(1, tree.height(d));
		assertEquals(0, tree.height(h));
		assertEquals(-1, tree.depth(new MyTreeNode<String>("x")));
		assertEquals(0, tree.depth(tree.getRoot()));
		assertEquals(3, tree.depth(k));
		assertEquals(2, tree.depth(g));
		assertEquals(2, tree.depth(h));
		assertEquals(-1, tree.depth(new MyTreeNode<String>("z")));
		assertFalse(tree.isEmpty());
		tree.makeEmpty();
		assertTrue(tree.isEmpty());
	}

	/**
	 * Create the tree from Figure 18.3.
	 * 
	 * @return new Figure 18.3 tree.
	 */
	private static Tree<String> createFigure18_3Tree() {

		a = createNode("a");
		b = createNode("b");
		c = createNode("c");
		d = createNode("d");
		e = createNode("e");
		f = createNode("f");
		g = createNode("g");
		h = createNode("h");
		i = createNode("i");
		j = createNode("j");
		k = createNode("k");


		a.setChild(e);
		e.setChild(j);
		e.setPrevSibling(d);
		j.setChild(k);
		j.setPrevSibling(i);
		d.setChild(h);
		d.setPrevSibling(c);
		c.setPrevSibling(b);
		b.setChild(g);
		g.setPrevSibling(f);

		return new MyTree<String>(a);
	}

	private static <T> TreeNode<T> createNode(T element) {
		return new MyTreeNode<T>(element);
	}
}