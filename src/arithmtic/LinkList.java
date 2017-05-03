package arithmtic;

public class LinkList<T> {
	// private Node<T> head;
	// 链表节点类
	private static class Node<T> {
		T data;// 节点数据
		Node<T> next;// 下一个节点

		public Node(T data) {
			this.data = data;
			this.next = null;
		}

	}
}
