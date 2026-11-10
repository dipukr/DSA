package main;

public class LinkedList {
	
	public class Node {
		public Object data;
		public Node prev;
		public Node next;
		public Node(Object data) {this.data = data;}
		public Node(Object data, Node prev, Node next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}
	
	private Node head;
	private Node tail;
	
	public void addFirst(Object elem) {
		if (head == null) {
			Node node = new Node(elem);
			head = tail = node;
		} else {
			Node node = new Node(elem, null, head);
			head.prev = node;
			head = node;
		}
	}
	
	public void addLast(Object elem) {
		if (head == null) {
			Node node = new Node(elem);
			head = tail = node;
		} else {
			Node node = new Node(elem, tail, null);
			tail.next = node;
			tail = node;
		}
	}
	
	public void deleteFirst() {
		if (head == null) Error.fatal("List is empty");
		if (head == tail) {
			head = null;
			tail = null;
		} else {
			head = head.next;
			head.prev = null;
		}
	}
	
	public void deleteLast() {
		if (head == null) Error.fatal("List is empty");
		if (head == tail) {
			head = null;
			tail = null;
		} else {
			tail.prev.next = null;
			tail = tail.prev; 
		}
	}
	
	public void reverse() {
		if (head == tail) return;
		Node curr = head;
		while (curr != null) {
			Node next = curr.next;
			curr.next = curr.prev;
			curr.prev = curr.next;
			curr = next;
		}
		Node temp = head;
		head = tail;
		tail = temp;
	}
	
	public void draw() {
		if (empty()) return;
		for (Node curr = head; curr != null; curr = curr.next)
			System.out.printf("%s->", curr.data);
		System.out.println("null");
	}
	
	public int size() {
		int counter = 0;
		for (Node curr = head; curr != null; curr = curr.next)
			counter += 1;
		return counter;
	}
	
	public boolean empty() {
		return size() == 0;
	}
	
	public static void main(String[] args) {
		var list = new LinkedList();
		list.addFirst(100);
		list.addFirst(200);
		list.addFirst(300);
		list.addLast(400);
		list.addLast(500);
		list.draw();
		list.deleteLast();
		list.draw();
		list.deleteFirst();
		list.draw();
		list.reverse();
		list.draw();
	}
}
