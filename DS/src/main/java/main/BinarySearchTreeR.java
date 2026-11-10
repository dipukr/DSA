package main;

public class BinarySearchTreeR {
	
	public class Node {
		public int data;
		public Node left;
		public Node right;
		public Node(int data) {
			this.data = data;
		}
	}

	private Node root;
	
	public void insert(int data) {
		if (!contains(data)) {
			root = insert(root, data);
		}
	}
	
	public Node insert(Node root, int data) {
		if (root == null)
			return new Node(data);
		if (data < root.data) 
			root.left = insert(root.left, data);
		else if (data > root.data)
			root.right = insert(root.right, data);
		return root;
	}
	
	public boolean contains(int data) {
		return contains(root, data);
	}
	
	public boolean contains(Node node, int data) {
		if (node == null) return false;
		if (data < node.data) return contains(node.left, data);
		else if (data > node.data) return contains(node.right, data);
		else return true;
	}

	public int findMin() {
		if (empty()) Error.fatal("Tree is empty.");
		return findMin(root).data;
	}
	
	public Node findMin(Node root) {
		if (root.left == null) return root;
		return findMin(root.left);
	}

	public int findMax() {
		if (empty()) Error.fatal("Tree is empty.");
		return findMax(root).data;
	}
	
	public Node findMax(Node root) {
		if (root == null || root.right == null) return root;
		return findMax(root.right);
	}

	public void deleteMin() {

	}

	public void deleteMax() {
		
	}

	public void delete(int data) {
		if (contains(data)) {
			root = delete(root, data);
		}
	}

	public void merge(BinarySearchTreeR tree) {
		this.root = merge(this.root, tree.root);
	}

	public Node merge(Node t1, Node t2) {
		if (t1 == null) return t2;
		if (t2 == null) return t1;
		t1.data += t2.data;
		t1.left = merge(t1.left, t2.left);
		t1.right = merge(t1.right, t2.right);
		return t1;
	}
	
	public Node delete(Node root, int data) {
		if (data < root.data) root.left = delete(root.left, data);
		if (data > root.data) root.right = delete(root.right, data);
		else {
			if (root.left == null && root.right == null) {
				root = null;
			} else if (root.left == null) {
				root = root.right;
			} else if (root.right == null) {
				root = root.left;
			} else {
				Node temp = findMin(root);
				root = delete(root, data);
			}
		}
		return root;
	}
	
	public void inOrder() {
		inOrder(root);
	}
	
	public void inOrder(Node root) {
		if (root != null) {
			inOrder(root.left);
			System.out.printf("%d\t", root.data);
			inOrder(root.right);
		}
	}
	
	public void preOrder() {
		preOrder(root);
	}
	
	public void preOrder(Node root) {
		if (root != null) {
			System.out.printf("%d\t", root.data);
			preOrder(root.left);
			preOrder(root.right);
		}
	}
	
	public void postOrder() {
		postOrder(root);
	}

	public void postOrder(Node root) {
		if (root != null) {
			postOrder(root.left);
			postOrder(root.right);
			System.out.printf("%d\t", root.data);
		}
	}

	public int size() {
		return size(root);
	}

	public int size(Node root) {
		if (root == null) return 0;
		int leftSize = size(root.left);
		int rightSize = size(root.right);
		return leftSize + rightSize + 1;
	}
	
	public int height() {
		return height(root);
	}
	
	public int height(Node root) {
		if (root == null) return 0;
		int leftHeight = height(root.left);
		int rightHeight = height(root.right);
		return Math.max(leftHeight, rightHeight) + 1;
	}
	
	public boolean empty() {
		return size() == 0;
	}
}