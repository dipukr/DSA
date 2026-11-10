package main;

import java.util.HashMap;
import java.util.Map;

public class LRU {

	public class Node {
		public String key;
		public String val;
		public Node prev;
		public Node next;
		public Node(String key, String val) {
			this.key = key;
			this.val = val;
		}
	}
	
	private Map<String, Node> cache = new HashMap<>();
	private int capacity;
	private Node head;
	private Node tail;

	public LRU(int capacity) {
		this.capacity = capacity;
	}
	
	public String get(String key) {
		Node node = cache.get(key);
		if (node == null) return null;
		moveToHead(node);
		return node.val;
	}

	public void put(String key, String val) {
		Node node = cache.get(key);
		if (node != null) {
			node.val = val;
			moveToHead(node);
		} else {
			Node newNode = new Node(key, val);
			cache.put(key, newNode);
			addNodeAtHead(newNode);
			if (cache.size() > capacity) {
				// Evict least recently used (tail node)
				cache.remove(tail.key);
				removeNode(tail);
			}
		}
	}
	
	public void moveToHead(Node node) {
		removeNode(node);
		addNodeAtHead(node);
	}

	public void addNodeAtHead(Node node) {
		node.prev = null;
		node.next = head;
		if (head != null)
			head.prev = node;
		head = node;
		if (tail == null)
			tail = node;
	}

	public void removeNode(Node node) {
		if (node.prev != null)
			node.prev.next = node.next;
		else head = node.next;
		if (node.next != null)
			node.next.prev = node.prev;
		else tail = node.prev;
	}

	public void printData() {
		Node curr = head;
		System.out.print("Cache state (MRU -> LRU): ");
		while (curr != null) {
			System.out.printf("[%s=%s] ", curr.key, curr.val);
			curr = curr.next;
		}
		System.out.println();
	}

	public static void main(String[] args) {
		LRU cache = new LRU(3);

		cache.put("A", "1");
		cache.put("B", "2");
		cache.put("C", "3");
		cache.printData(); // A,B,C

		cache.get("A");
		cache.printData(); // A moved to head

		cache.put("D", "4"); // Evicts LRU (B)
		cache.printData();

		cache.get("C");
		cache.printData();
	}
}
