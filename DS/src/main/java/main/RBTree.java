package main;

public class RBTree {

	private static enum Color {RED, BLACK};

	public class Node {
		public int data;
		public Node left;
		public Node right;
		public Node parent;
		public Color color;
		public Node(int data) {
			this.data = data;
		}
	}

	private final Node nilNode = new Node(0);
	private Node root = null;
	private int nodeCount = 0;

	public void flipColor(Node node) {
		node.color = node.color == Color.RED ? Color.BLACK : Color.RED;
	}
	
	public boolean search(int key) {
		Node node = root;
		while (node != null) {
			if (key < node.data) node = node.left;
			else if (key > node.data) node = node.right;
			else return true;
		}
		return false;
	}
	
	public void insert(int data) {
		Node node = new Node(data);
		node.left = nilNode;
		node.right = nilNode;
		
		Node parent = nilNode;
		Node current = root;
		
		while (current != nilNode) {
			parent = current;
			if (data < current.data) current = current.left;
			if (data > current.data) current = current.right;
		}
		node.parent = parent;
		
		if (parent == nilNode) root = node;
		if (data < parent.data) parent.left = node;
		if (data > parent.data) parent.right = node;
		
		node.color = Color.RED;		
		insertFixup(node);
	}
	
	public void insertFixup(Node node) {
		while (node.parent.color == Color.RED) {
			if (node.parent == node.parent.parent.left) {
				
			} else {
				
			}
		}
	}


	public void leftRotate(Node x) {
		Node y = x.right;
		x.right = y.left;
		if (y.left != nilNode) y.left.parent = x;
		y.parent = x.parent;
		if (x.parent == nilNode) root = y;
		else if (x == x.parent.left) x.parent.left = y;
		else x.parent.right = y;
		y.left = x;
		x.parent = y;
	}
	
	public void rightRotate(Node x) {
		Node y = x.left;
		x.left = y.right;
		if (y.right != nilNode) y.left.right = x;
		x.parent = y.parent;
		if (y.parent == nilNode) root = x;
		else if (y == y.parent.right) y.parent.right = x;
		else y.parent.left = x;
		x.right = y;
		x.parent = y;
	}

	public int size() {
		return nodeCount;
	}

	public boolean empty() {
		return size() == 0;
	}
}
