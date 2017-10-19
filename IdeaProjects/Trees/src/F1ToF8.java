package Tree;

import java.util.function.Function;

public interface F1ToF8<E> {
	public void F1(BinaryT<E> tree);
	public void F2(BinaryT<E> tree);
	public void F3(BinaryT<E> tree);
	public void F4(BinaryT<E> tree);
	public void F5(BinaryT<E> tree);
	public void F6(BinaryT<E> tree);
	public void F7(BinaryT<E> tree);
	public void F8(BinaryT<E> tree);
	public void G1(BinaryT<E> tree, Function<BinaryT<E>, E> f);
}
