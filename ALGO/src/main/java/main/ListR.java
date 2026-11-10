package main;

public class ListR {

	public class Node {
		public int data;
		public Node next;
		public Node(int data) {
			this.data = data;
		}
	}
	
	public void draw(Node head) {
		if (head == null) {
			System.out.println("null");
			return;
		}
		System.out.printf("%d->", head.data);
		draw(head.next);
	}
	
	public void drawR(Node head) {
		if (head == null) {
			System.out.println("null");
			return;
		}
		drawR(head.next);
		System.out.printf("%d->", head.data);
	}
	
	public int size(Node head) {
		if (head == null) return 0;
		return size(head.next) + 1;
	}
	
	public boolean empty(Node head) {
		return size(head) == 0;
	}
	
	public void addAll(Node head, int ... data) {
		for (int elem: data)
			addTail(head, elem);
	}
	
	public Node addLast(Node head, int data) {
		if (head == null) return new Node(data);
		head.next = addLast(head.next, data);
		return head;
	}
	
	public Node deleteLast(Node head) {
		if (head.next == null) return null;
		head.next = deleteLast(head.next);
		return head;
	}
	
	public void appendLast(Node head, int data) {
		if (head.next == null) head.next = new Node(data);
		appendLast(head.next, data);
	}
	
	public void removeLast(Node head) {
		if (head.next.next == null) head.next = null;
		deleteLast(head.next);
	}
	
	public void addTail(Node head, int data) {
		if (head.next == null) {
			head.next = new Node(data);
			return;
		}
		addTail(head.next, data);
	}
	
	public void deleteTail(Node head) {
		if (head.next.next == null) {
			head.next = null;
			return;
		}
		deleteLast(head.next);
	}
	
	public boolean searchElement(Node head, int key) {
		if (head == null) return false;
		if (head.data == key) return true;
		return searchElement(head.next, key);
	}
	
	public boolean circular(Node head, Node node) {
		if (node == head) return true;
		if (node == null) return false;
		return circular(head, node.next);
	}
	
	public Node reverse(Node head) {
		if (head == null || head.next == null) return head;
		Node newHead = reverse(head.next);
		head.next.next = head;
		head.next = null;
		return newHead;
	}
	
	public Node reverseList(Node curr, Node prev) {
		if (curr == null) return prev;
		Node next = curr.next;
		curr.next = prev;
		return reverseList(next, curr);
	}
	
	public Node mergeSortedLists(Node first, Node second) {
		if (first == null) return second;
		if (second == null) return first;
		if (first.data < second.data) {
			first.next = mergeSortedLists(first.next, second);
			return first;
		} else {
			second.next = mergeSortedLists(first, second.next);
			return second;
		}
	}
}











