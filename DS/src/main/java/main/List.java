package main;

public class List {
	
	public class Node {
		public Object data;
		public Node next;
		public Node(Object data) {this.data = data;}
		public Node(Object data, Node next) {
			this.data = data;
			this.next = next;
		}
	}
	
	private Node head = null;
	private Node tail = null;
	
	public void addHead(Object data) {
		if (empty()) head = tail = new Node(data);
		else head = new Node(data, head);
	}
	
	public void addTail(Object data) {
		if (empty()) {
			head = tail = new Node(data);
		} else {
			Node node = new Node(data);
			tail.next = node;
			tail = node;
		}
	}
	
	public void deleteHead() {
		if (empty()) Error.fatal("List is empty");
		head = head.next;
		if (head == null)
			tail = null;
	}
	
	public void deleteTail() {
		if (empty()) Error.fatal("List is empty");
		if (head.next == null) {
			head = tail = null;
		} else {
			Node curr = head;
			while (curr.next.next != null)
				curr = curr.next;
			curr.next = null;
			tail = curr;
		}
	}
	
	public void reverse() {
		Node prev = null, curr = head;
		while (curr != null) {
			Node next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		tail = head;
		head = prev;
	}
	
	public void dump() {
		if (empty()) return;
		for (Node curr = head; curr != null; curr = curr.next)
			System.out.printf("%s->", curr.data);
		System.out.println("null");
	}
	
	public int size() {
		int nodeCount = 0;
		for (Node curr = head; curr != null; curr = curr.next)
			nodeCount += 1;
		return nodeCount;
	}
	
	public boolean empty() {
		return size() == 0;
	}
}
