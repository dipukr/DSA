package main;

public class CircularList {

	public class Node {
		public Object data;
		public Node next;
		public Node(Object data) {
			this.data = data;
		}
	}

	private Node head = null;

	public void addHead(Object elem) {
		Node node = new Node(elem);
		if (head == null) {
			node.next = node;
			head = node;
			return;
		}
		Node curr = head;
		while (curr.next != head)
			curr = curr.next;
		curr.next = node;
		node.next = head;
		head = node;
	}

	public void addTail(int data) {
		if (head == null) {
			addHead(data);
			return;
		}
		Node node = new Node(data);
		Node curr = head;
		while (curr.next != head)
			curr = curr.next;
		curr.next = node;
		node.next = head;
	}

	public void draw() {
		if (head == null) return;
		Node curr = head;
		do {
			System.out.printf("%d->", curr.data);
			curr = curr.next;	
		} while (curr != head);
		System.out.println(head.data);
	}
	
	public boolean hasCycle() {
		return true;
	}

	public int size() {
		if (head == null) return 0;
		Node curr = head;
		int count = 0;
		do {
			count += 1;
			curr = curr.next;
		} while (curr != head);
		return count;
	}
	
	public boolean empty() {
		return size() == 0;
	}
	
	public static void main(String[] args) {
		var list = new CircularList();
		list.addHead(100);
		list.addHead(200);
		list.addTail(300);
		list.addTail(400);
		System.out.println(list.size());
		list.draw();
	}
}