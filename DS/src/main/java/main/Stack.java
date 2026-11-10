package main;

public class Stack {
	
	public class Node {
		public int data;
		public Node next;
		public Node(int data) {
			this.data = data;
			this.next = null;
		}
	}

	private Node head = null;

	public void push(int elem) {
		Node node = new Node(elem);
		node.next = head;
		head = node;
	}

	public int pop() {
		if (empty()) Error.fatal("Stack underflow");
		int retval = head.data;
		head = head.next;
		return retval;
	}

	public int top() {
		if (empty()) Error.fatal("Stack underflow");
		return head.data;
	}
	
	public int size() {
		int count = 0;
		for (Node curr = head; curr != null; curr = curr.next)
			count += 1;
		return count;
	}
	
	public boolean empty() {
		return size() == 0;
	}
}