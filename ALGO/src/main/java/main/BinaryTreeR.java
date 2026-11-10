package main;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

public class BinaryTreeR {

	public class Node {
		public int data;
		public Node left;
		public Node right;
		public Node(int data) {
			this.data = data;
		}
	}
	
	public void visitNode(Node node) {
		System.out.printf("%d\t", node.data);
	}
	
	public void preorder(Node root) {
		if (root == null) return;
		visitNode(root);
		preorder(root.left);
		preorder(root.right);
	}
	
	public void inorder(Node root) {
		if (root == null) return;
		inorder(root.left);
		visitNode(root);
		inorder(root.right);
	}
	
	public void postorder(Node root) {
		if (root == null) return;
		postorder(root.left);
		postorder(root.right);
		visitNode(root);
	}
	
	public void DFS(Node root) {
		if (root == null) return;
		DFS(root.left);
		DFS(root.right);
		Console.draw("%s\t", root.data);
	}
	
	public void BFS(Node root) {
		var queue = new LinkedList<Node>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			visitNode(node);
			if (node.left != null)
				queue.offer(node.left);
			if (node.right != null)
				queue.offer(node.right);
		}
	}
	
	public void depthFirstSearch(Node root) {
		var stack = new Stack<Node>();
		stack.push(root);
		while (!stack.isEmpty()) {
			Node node = stack.pop();
			Console.draw("%s\t", node.data);
			if (node.left != null)
				stack.push(node.left);
			if (node.right != null)
				stack.push(node.right);
		}
	}
	
	public int size(Node node) {
		if (node == null) return 0;
		int leftSize = size(node.left);
		int rightSize = size(node.right);
		return leftSize + rightSize + 1;
	}
	
	public int height(Node node) {
		if (node == null) return 0;
		int leftHeight = height(node.left);
		int rightHeight = height(node.right);
		return Math.max(leftHeight, rightHeight) + 1;
	}
	
	public int treeSum(Node root) {
		if (root == null) return 0;
		int leftSum = treeSum(root.left);
		int rightSum = treeSum(root.right);
		return root.data + leftSum + rightSum;
	}
	
	public int treeMax(Node root) {
		if (root == null)
			return Integer.MIN_VALUE;
		int leftMax = treeSum(root.left);
		int rightMax = treeSum(root.right);
		return Math.max(root.data, Math.max(leftMax, rightMax));
	}
	
	public boolean search(Node root, int key) {
		if (root == null) return false;
		boolean inLeft = search(root.left, key);
		boolean inRight = search(root.right, key);
		return root.data == key || inLeft || inRight;
	}
	
	public boolean contains(Node root, int key) {
		if (root == null) return false;
		if (root.data == key) return true;
		return contains(root.left, key) || contains(root.right, key);
	}
	
	@REM("BottomUP")
	public Node reverse(Node root) {
		if (root == null) return null;
		Node leftTree = reverse(root.left);
		Node rightTree = reverse(root.right);
		root.left = rightTree;
		root.right = leftTree;
		return root;
	}
	
	@REM("BottomUP")
	public Node invert(Node root) {
		if (root == null) return null;
		Node save = invert(root.left);
		root.left = invert(root.right);
		root.right = save;
		return root;
	}
	
	@REM("TopDown")
	public void invertTree(Node node) {
		if (node == null) return;
		Node saved = node.left;
		node.left = node.right;
		node.right = saved;
		invertTree(node.left);
		invertTree(node.right);
	}
	
	@REM("O(N^2)")
	public int diameter(Node root) {
		if (root == null) return 0;
		int diam1 = diameter(root.left);
		int diam2 = diameter(root.right);
		int diam3 = height(root.left) + height(root.right) + 1;
		return Math.max(diam1, Math.max(diam2, diam3));
	}
	
	public void findByLevel(Node root, int level, List<Integer> data) {
		if (root == null) return;
		if (level == 1) data.add(root.data);
		else if (level > 1) {
			findByLevel(root.left, level - 1, data);
			findByLevel(root.right, level - 1, data);
		}
	}
	
	public void levelOrderTraversal(Node root, int level) {
		if (root == null) return;
		if (level == 1) visitNode(root);
		else if (level > 1) {
			levelOrderTraversal(root.left, level - 1);
			levelOrderTraversal(root.right, level - 1);
		}
	}
	
	public void levelOrderTraversal(Node root) {
		int height = height(root);
		for (int levelNo = 1; levelNo <= height; levelNo++) {
			Console.draw("Level %d: ", levelNo);
			levelOrderTraversal(root, levelNo);
			System.out.println();
		}
	}
	
	public List<Integer> leftView(Node root) {
		List<Integer> answer = new ArrayList<>();
		Queue<Node> queue = new LinkedList<>();
		if (root == null) return answer;
		queue.offer(root);
		while (!queue.isEmpty()) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				Node node = queue.poll();
				if (node.left != null) queue.offer(node.left);
				if (node.right != null) queue.offer(node.right);
				if (i == 0) answer.add(node.data);
			}
		}
		return answer;
	}
	
	public List<Integer> rightView(Node root) {
		List<Integer> answer = new ArrayList<>();
		Queue<Node> queue = new LinkedList<>();
		if (root == null) return answer;
		queue.offer(root);
		while (!queue.isEmpty()) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				Node node = queue.poll();
				if (node.left != null) queue.offer(node.left);
				if (node.right != null) queue.offer(node.right);
				if (i == size - 1) answer.add(node.data);
			}
		}
		return answer;
	}
	
	public void levelWiseBFS(Node root) {
		var queue = new LinkedList<Node>();
		queue.offer(root);
		queue.offer(null);
		int level = 1;
		System.out.printf("Level %d: ", level);
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			if (node != null) {
				System.out.printf("%d\t", node.data);
				if (node.left != null) queue.offer(node.left);
				if (node.right != null) queue.offer(node.right);
			} else if (!queue.isEmpty()) {
				level++;
				queue.offer(null);
				System.out.printf("\nLevel %d: ", level);
			}
		}
	}
	
	public Map<Integer, Integer> levelWiseSum(Node root) {
		var levelWiseSum = new TreeMap<Integer, Integer>();
		var queue = new LinkedList<Node>();
		queue.offer(root);
		queue.offer(null);
		int levelSum = 0;
		int levelNo = 1;
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			if (node != null) {
				levelSum += node.data;
				if (node.left != null) queue.offer(node.left);
				if (node.right != null) queue.offer(node.right);
			} else if (!queue.isEmpty()) {
				levelWiseSum.put(levelNo, levelSum);
				levelSum = 0;
				levelNo++;
				queue.offer(null);
			}
		}
		levelWiseSum.put(levelNo, levelSum);
		return levelWiseSum;
	}
	
	public int leftLeavesSum(Node root) {
		return leftLeavesSum(root, false);
	}
	
	public int leftLeavesSum(Node root, boolean leftChild) {
		if (root == null) return 0;
		if (leftChild && root.left == null && root.right == null)
			return root.data;
		int leftSum = leftLeavesSum(root.left, true);
		int rightSum = leftLeavesSum(root.right, false);
		return leftSum + rightSum;
	}
	
	public boolean hasPathDFS(Node node, int val, LinkedList<Integer> path) {
		if (node == null) return false;
		path.addLast(node.data);
		if (node.data == val || hasPathDFS(node.left, val, path) || hasPathDFS(node.right, val, path))
			return true;
		path.removeLast();
		return false;
	}
	
	public boolean hasPath(Node root, int val, Deque<Integer> path) {
		if (root == null) return false;
		if (root.data == val) {
			path.addLast(root.data);
			return true;
		}
		if (hasPath(root.left, val, path)) {
			path.addLast(root.data);
			return true;
		}
		if (hasPath(root.right, val, path)) {
			path.addLast(root.data);
			return true;
		}
		return false;
	}
	
	public boolean findPath(Node root, int val, Deque<Integer> path) {
		if (root == null) return false;
		if (root.data == val || findPath(root.left, val, path) || findPath(root.right, val, path)) {
			path.addLast(root.data);
			return true;
		}
		return false;
	}
	
	public boolean getPath(Node root, int val, Deque<Integer> path) {
		if (root == null) return false;
		path.addLast(root.data);
		if (root.data == val || getPath(root.left, val, path) || getPath(root.right, val, path))
			return true;
		path.removeLast();
		return false;
	}
	
	public boolean binarySearchTree(Node node) {
		if (node == null) return true;
		boolean valid = true;
		if (node.left != null) valid = valid && node.left.data < node.data;
		if (node.right != null) valid = valid && node.right.data < node.data;
		return valid && binarySearchTree(node.left) && binarySearchTree(node.right);
	}
}
