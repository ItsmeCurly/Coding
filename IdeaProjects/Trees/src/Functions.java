//import java.util.function.Function;
//
//public class Functions<E> implements F1ToF8<E>{
//
//	public Functions() {
//
//	}
//	@Override
//	public void F1(BinaryT<E> tree) {
//		BinaryNode<E> root = tree.getRoot();
//
//		BinaryNode<E> left = root.getLeft();
//		BinaryNode<E> right = root.getRight();
//
//		root.setLeft(right);
//		root.setRight(left);
//	}
//
//	@Override
//	public void F2(BinaryT<E> tree) {
//		BinaryNode<E> root = tree.getRoot();
//
//		if(root == null)
//			return;
//		BinaryNode<E> left = root.getLeft();
//		BinaryNode<E> right = root.getRight();
//
//		root.setLeft(right);
//		root.setRight(left);
//
//		F2(new BinaryT<E>(left));
//		F2(new BinaryT<E>(right));
//	}
//
//	@Override
//	public void F3(BinaryT<E> tree) {
//		BinaryNode<E> root = tree.getRoot();
//
//		if(root == null)
//			return;
//		BinaryNode<E> left = root.getLeft();
//		BinaryNode<E> right = root.getRight();
//
//		root.setLeft(right);
//		root.setRight(left);
//
//		F3(new BinaryT<E>(right));
//
//	}
//
//	@Override
//	public void F4(BinaryT<E> tree) {
//		BinaryNode<E> root = tree.getRoot();
//
//		if(root == null)
//			return;
//		BinaryNode<E> left = root.getLeft();
//		BinaryNode<E> right = root.getRight();
//
//		root.setLeft(right);
//		root.setRight(left);
//
//		F4(new BinaryT<E>(left));
//	}
//
//	@Override
//	public void F5(BinaryT<E> tree) {
//		BinaryNode<E> root = tree.getRoot();
//
//		E rootElement = root.getElement();
//
//		BinaryNode<E> left = root.getLeft();
//		BinaryNode<E> right = root.getRight();
//
//		E subElement = left.getElement();
//
//		BinaryNode<E> left1 = left.getLeft();
//		BinaryNode<E> right1 = left.getRight();
//
//		root.setElement(subElement);
//
//		tree.getRoot().setLeft(left1);
//
//		tree.getRoot().getRight().setElement(rootElement);
//
//		tree.getRoot().getRight().setLeft(right1);
//		tree.getRoot().getRight().setLeft(right);
//	}
//
//	@Override
//	public void F6(BinaryT<E> tree) {
//		BinaryNode<E> root = tree.getRoot();
//
//		E rootElement = root.getElement();
//
//		BinaryNode<E> left = root.getLeft();
//		BinaryNode<E> right = root.getRight();
//
//		E subElement = right.getElement();
//
//		BinaryNode<E> left1 = right.getLeft();
//		BinaryNode<E> right1 = right.getRight();
//
//		root.setElement(subElement);
//
//		tree.getRoot().getLeft().setElement(rootElement);
//
//		tree.getRoot().getLeft().setLeft(left);
//		tree.getRoot().getLeft().setRight(left1);
//
//		tree.getRoot().setRight(right1);
//	}
//
//	@Override
//	public void F7(BinaryT<E> tree) {
//		BinaryNode<E> root = tree.getRoot();
//
//		E rootElement = root.getElement();
//
//		BinaryNode<E> left = root.getLeft();
//		BinaryNode<E> right = root.getRight();
//
//		E sub1Element = left.getElement();
//
//		BinaryNode<E> left1 = left.getLeft();
//		BinaryNode<E> right1 = left.getRight();
//
//		E sub2Element = right1.getElement();
//
//		BinaryNode<E> left2 = right.getLeft();
//		BinaryNode<E> right2 = right.getRight();
//
//		root.setElement(sub2Element);
//
//		root.getLeft().setElement(sub1Element);
//		root.getLeft().setLeft(left1);
//		root.getLeft().setRight(left2);
//
//		root.getRight().setElement(rootElement);
//		root.getRight().setLeft(right2);
//		root.getRight().setRight(right);
//
//	}
//
//	@Override
//	public void F8(BinaryT<E> tree) {
//		BinaryNode<E> root = tree.getRoot();
//
//		E rootElement = root.getElement();
//
//		BinaryNode<E> left = root.getLeft();
//		BinaryNode<E> right = root.getRight();
//
//		E sub1Element = left.getElement();
//
//		@SuppressWarnings("unused")
//		BinaryNode<E> left1 = left.getLeft();
//		BinaryNode<E> right1 = left.getRight();
//
//		E sub2Element = right1.getElement();
//
//		BinaryNode<E> left2 = right.getLeft();
//		BinaryNode<E> right2 = right.getRight();
//
//		root.setElement(sub2Element);
//
//		root.getLeft().setElement(rootElement);
//		root.getLeft().setLeft(left);
//		root.getLeft().setRight(left2);
//
//		root.getRight().setElement(sub1Element);
//		root.getRight().setLeft(right2);
//		root.getRight().setRight(right1);
//
//	}
//
//	@Override
//	public void G1(BinaryT<E> tree, Function<BinaryT<E>, E> f) {
//		if (tree == null) {
//			return;
//		}
//		G1((BinaryT<E>)new BinaryT<E>(tree.getRoot().getLeft()), f);
//		G1((BinaryT<E>)new BinaryT<E>(tree.getRoot().getRight()), f);
//
//		f.apply((BinaryT<E>) new BinaryT<E>(tree.getRoot().getLeft()));
//		f.apply((BinaryT<E>) new BinaryT<E>(tree.getRoot().getRight()));
//
//		f.apply((BinaryT<E>)tree);
//	}
//
//
//}
