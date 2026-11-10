package main;

public class Queue {

	public class Node {
		public int data;
		public Node next;
		public Node(int data) {
			this.data = data;
		}
	}

	private Node head = null;
	private Node tail = null;
	private int count = 0;

	public void enqueue(int elem) {
		Node node = new Node(elem);
		if (empty()) {
			head = node;
			tail = node;
		} else {
			tail.next = node;
			tail = node;
		}
		count++;
	}
	
	public int dequeue() {
		if (empty()) Error.error("Queue underflow.");
		int elem = head.data;
		head = head.next;
		count--;
		return elem;
	}

	public int front() {
		if (empty()) Error.error("Queue underflow");
		return head.data;
	}
	
	public int size() {
		return count;
	}
	
	public boolean empty() {
		return size() == 0;
	}
}