package main;

public class AvlTree {
	
	public class Node {
		public double data;
		public int height;
		public int factor;
		public Node left;
		public Node right;
		public Node(double data) {
			this.data = data;
		}
	}
	
	private Node root;
	private int nodeCount;

	public boolean find(double data) {
		return find(root, data);
	}

	public boolean find(Node node, double data) {
		if (node == null) return false;
		if (data < node.data) return find(node.left, data);
		else if (data > node.data) return find(node.right, data);
		else return true;
	}

	public boolean insert(double data) {
		if (find(root, data)) return false;
		root = insert(root, data);
		nodeCount++;
		return true;
	}

	public Node insert(Node node, double data) {
		if (node == null) return new Node(data);
		if (data < node.data) node.left = insert(node.left, data);
      	else node.right = insert(node.right, data);
		update(node);
		return balance(node);
	}

	public void update(Node node) {
		int lhsHeight = (node.left == null) ? -1 : node.left.height;
		int rhsHeight = (node.right == null) ? -1 : node.right.height;
		node.height = Math.max(lhsHeight, rhsHeight) + 1;
		node.factor = rhsHeight - lhsHeight;
	}

	public Node balance(Node node) {
		if (node.factor == -2) {
			if (node.left.factor <= 0)
        		return leftLeftCase(node);
        	return leftRightCase(node);
		} else if (node.factor == +2) {
			if (node.right.factor >= 0)
				return rightRightCase(node);
			return rightLeftCase(node);	
		}
		return node;
	}

	public Node leftLeftCase(Node node) {
		return rightRotation(node);
	}

	public Node leftRightCase(Node node) {
		node.left = leftRotation(node.left);
		return leftLeftCase(node);
	}

	public Node rightRightCase(Node node) {
		return leftRotation(node);
	}

	public Node rightLeftCase(Node node) {
		node.right = rightRotation(node.right);
		return rightRightCase(node);
	}

	public Node leftRotation(Node node) {
		Node parent = node.right;
		node.right = parent.left;
		parent.left = node;
		update(node);
		update(parent);
		return parent;
	}

	public Node rightRotation(Node node) {
		Node parent = node.left;
		node.left = parent.right;
		parent.right = node;
		update(node);
		update(parent);
		return parent;
	}

	public boolean delete(double data) {
		if (find(root, data)) {
			root = delete(root, data);
			nodeCount--;
			return true;
		}
		return false;
	}

	public Node delete(Node node, double data) {
		if (data < node.data)
			node.left = delete(node.left, data);
		else if (data > node.data)
			node.right = delete(node.right, data);
		else {
			if (node.left == null)
        		return node.right;
			else if (node.right == null)
				return node.left;
			else {
				if (node.left.height > node.right.height) {
					double successorVal = findMax(node.left);
					node.data = successorVal;
					node.left = delete(node.left, successorVal);
				} else {
					double successorVal = findMin(node.right);
					node.data = successorVal;
					node.right = delete(node.right, successorVal);
				}
			}
		}
		update(node);
		return balance(node);
	}

	public double findMin(Node node) {
		while (node.left != null)
			node = node.left;
		return node.data;
	}

	public double findMax(Node node) {
		while (node.right != null)
			node = node.right;
		return node.data;
	}
  	
  	public boolean valid(Node root) {
    	if (root == null) return true;
		boolean valid = true;
		if (root.left != null) valid = valid && root.left.data < root.data;
		if (root.right != null) valid = valid && root.right.data > root.data;
		return valid && valid(root.left) && valid(root.right);
	}
  	
  	public int size() {
		return nodeCount;
	}
	
	public int height() {
		return empty() ? 0 : root.height;
	}
	
	public boolean empty() {
		return size() == 0;
	}
}