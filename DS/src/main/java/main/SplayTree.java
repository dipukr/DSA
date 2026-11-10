package main;

/**
 * Splay Tree implementation with insert and search operations.
 *
 * A splay tree is a self-adjusting binary search tree. After every insert or
 * search, the accessed node is moved to the root via rotations ("splaying"),
 * giving amortized O(log n) per operation and making recently accessed keys
 * fast to reach again.
 */
public class SplayTree {
	
	public class Node {
		public int key;
		public Node left;
		public Node right;
		Node(int key) {this.key = key;}
	}

	private Node root;
	
	public Node rotateRight(Node x) {
		Node y = x.left;
		x.left = y.right;
		y.right = x;
		return y;
	}

	public Node rotateLeft(Node x) {
		Node y = x.right;
		x.right = y.left;
		y.left = x;
		return y;
	}

	/**
	 * Brings the node with the given key to the root of the subtree. If the key is
	 * not present, the last accessed node (the closest key on the search path)
	 * becomes the root.
	 *
	 * Top-down recursive splay handling zig, zig-zig, and zig-zag cases.
	 */
	public Node splay(Node node, int key) {
		if (node == null || node.key == key)
			return node;

		if (key < node.key) {
			// Key lies in the left subtree
			if (node.left == null) {
				return node; // key not in tree
			}

			if (key < node.left.key) {
				// Zig-Zig (left-left): splay in left-left subtree, then rotate right twice
				node.left.left = splay(node.left.left, key);
				node = rotateRight(node);
			} else if (key > node.left.key) {
				// Zig-Zag (left-right): splay in left-right subtree, then rotate left
				node.left.right = splay(node.left.right, key);
				if (node.left.right != null) {
					node.left = rotateLeft(node.left);
				}
			}

			// Zig: final right rotation to bring the target to the root
			return (node.left == null) ? node : rotateRight(node);

		} else {
			// Key lies in the right subtree
			if (node.right == null) {
				return node; // key not in tree
			}

			if (key > node.right.key) {
				// Zig-Zig (right-right)
				node.right.right = splay(node.right.right, key);
				node = rotateLeft(node);
			} else if (key < node.right.key) {
				// Zig-Zag (right-left)
				node.right.left = splay(node.right.left, key);
				if (node.right.left != null) {
					node.right = rotateRight(node.right);
				}
			}

			// Zig: final left rotation
			return (node.right == null) ? node : rotateLeft(node);
		}
	}

	/**
	 * Inserts a key into the splay tree. The new key becomes the root. Duplicate
	 * keys are ignored (the existing node is splayed to the root).
	 */
	public void insert(int key) {
		if (root == null) {
			root = new Node(key);
			return;
		}

		root = splay(root, key);

		if (root.key == key) {
			return; // duplicate; already at root after splay
		}

		Node newNode = new Node(key);
		if (key < root.key) {
			// New node takes root's left subtree; old root goes right
			newNode.right = root;
			newNode.left = root.left;
			root.left = null;
		} else {
			// New node takes root's right subtree; old root goes left
			newNode.left = root;
			newNode.right = root.right;
			root.right = null;
		}
		root = newNode;
	}

	/**
	 * Searches for a key. Splays the tree so that the key (if found) or the last
	 * accessed node (if not) becomes the root.
	 *
	 * @return true if the key exists in the tree
	 */
	public boolean search(int key) {
		if (root == null) return false;
		root = splay(root, key);
		return root.key == key;
	}

	// ---------- Utility: in-order traversal for verification ----------

	public void inorder() {
		inorder(root);
		System.out.println();
	}

	public void inorder(Node node) {
		if (node == null) return;
		inorder(node.left);
		System.out.print(node.key + " ");
		inorder(node.right);
	}

	// ---------- Demo ----------

	public static void main(String[] args) {
		var tree = new SplayTree();
		int[] keys = {10, 20, 30, 40, 50, 25};
		for (int k: keys) tree.insert(k);

		System.out.print("In-order traversal: ");
		tree.inorder(); // 10 20 25 30 40 50

		System.out.println("Search 25: " + tree.search(25)); // true, 25 is now root
		System.out.println("Search 60: " + tree.search(60)); // false
		System.out.println("Root after searches: " + tree.root.key);
	}
}