package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

public class BinaryTree {

	public class Node {
		public int data;
		public Node left;
		public Node right;
		public Node(int data) {
			this.data = data;
		}
	}
	
	public void visit(Node node) {
		System.out.printf("%d\t", node.data);
	}
	
	public int size(Node root) {
		int nodeCount = 0;
		if (root == null) return nodeCount;
		var queue = new LinkedList<Node>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			nodeCount += 1;
			Node node = queue.poll();
			if (node.left != null) queue.offer(node.left);
			if (node.right != null) queue.offer(node.right);
		}
		return nodeCount;
	}
	
	public int height(Node root) {
		var queue = new LinkedList<Node>();
		queue.offer(root);
		queue.offer(null);
		int level = 0;
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			if (node != null) {
				if (node.left != null) queue.offer(node.left);
				if (node.right != null) queue.offer(node.right);
			} else if (!queue.isEmpty()) {
				level += 1;
				queue.offer(null);
			}
		}
		return level;
	}
	
	public List<Integer> preorder(Node root) {
		List<Integer> result = new ArrayList<>();
		var stack = new Stack<Node>();
		stack.push(root);
		while (!stack.isEmpty()) {
			Node node = stack.pop();
			result.add(node.data);
			if (node.left != null) stack.add(node.left);
			if (node.right != null) stack.add(node.right);
		}
		return result;
	}
	
	public List<Integer> inorder(Node root) {
		List<Integer> result = new ArrayList<>();
		var stack = new Stack<Node>();
		Node curr = root;
		while (curr != null && !stack.isEmpty()) {
			while (curr != null) {
				stack.push(curr);
				curr = curr.left;
			}
			curr = stack.pop();
			result.add(curr.data);
			curr = curr.right;
		}
		return result;
	}
	
	public List<Integer> postorder(Node root) {
		List<Integer> result = new ArrayList<>();
		if (root == null) return result;
		var stack1 = new Stack<Node>();
		var stack2 = new Stack<Node>();
		while (!stack1.isEmpty()) {
			Node node = stack1.pop();
			stack2.push(node);
			if (node.left != null) stack1.push(node.left);
			if (node.right != null) stack1.push(node.right);
		}
		while (!stack2.isEmpty()) {
			Node node = stack2.pop();
			result.add(node.data);
		}
		return result;
	}
	
	public List<Integer> postOrder(Node root) {
		List<Integer> result = new ArrayList<>();
		if (root == null) return result;
		var stack = new Stack<Node>();
		Node curr = root;
		Node prev = null;
		while (curr != null || !stack.isEmpty()) {
			if (curr != null) {
				stack.push(curr);
				curr = curr.left;
			} else {
				Node node = stack.peek();
				if (node.right != null && prev != node.right) {
					curr = node.right;
				} else {
					result.add(node.data);
					prev = stack.pop();
				}
			}
		}
		return result;
	}
	
	public List<Integer> leverOrder(Node root) {
		List<Integer> result = new ArrayList<>();
		var queue = new LinkedList<Node>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			result.add(node.data);
			if (node.left != null) queue.offer(node.left);
			if (node.right != null) queue.offer(node.right);
		}
		return result;
	}
	
	public void printByLevel(Node root, int level) {
		if (root == null) return;
		if (level == 1) visit(root);
		else if (level > 1) {
			printByLevel(root.left, level - 1);
			printByLevel(root.right, level - 1);
		}
	}
	
	public void findByLevel(Node root, int level, List<Integer> data) {
		if (root == null) return;
		if (level == 1) data.add(root.data);
		else if (level > 1) {
			findByLevel(root.left, level - 1, data);
			findByLevel(root.right, level - 1, data);
		}
	}
	
	public void levelOrderTraversal(Node root) {
		int height = height(root);
		for (int levelNo = 1; levelNo <= height; levelNo++) {
			Console.draw("Level %d: ", levelNo);
			printByLevel(root, levelNo);
			System.out.println();
		}
	}
	
	public List<Integer> leftView(Node root) {
		List<Integer> answer = new ArrayList<>();
		if (root == null) return answer;
		Queue<Node> queue = new LinkedList<>();
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
		if (root == null) return answer;
		Queue<Node> queue = new LinkedList<>();
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
	
	public int treeSum(Node root) {
		if (root == null) return 0;
		int leftSum = treeSum(root.left);
		int rightSum = treeSum(root.right);
		return root.data + leftSum + rightSum;
	}

	public void BFS(Node root) {
		var queue = new LinkedList<Node>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			visit(node);
			if (node.left != null) queue.offer(node.left);
			if (node.right != null) queue.offer(node.right);
		}
	}
	
	public void DFS(Node root) {
		var stack = new Stack<Node>();
		stack.push(root);
		while (!stack.isEmpty()) {
			Node node = stack.pop();
			visit(node);
			if (node.left != null) stack.push(node.left);
			if (node.right != null) stack.push(node.right);
		}
	}

	public void levelWiseBFS(Node root) {
		var queue = new LinkedList<Node>();
		queue.offer(root);
		queue.offer(null);
		int level = 1;
		Console.draw("Level %d: ", level);
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			if (node != null) {
				Console.draw("%d\t", node.data);
				if (node.left != null) queue.offer(node.left);
				if (node.right != null) queue.offer(node.right);
			} else if (!queue.isEmpty()) {
				level++;
				queue.offer(null);
				Console.draw("\nLevel %d: ", level);
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
	
	public void test() {
		Node root = new Node(40);
		root.left = new Node(20);
		root.right = new Node(70);
		root.left.left = new Node(10);
		root.left.right = new Node(30);
		root.right.left = new Node(60);
		root.right.right = new Node(90);
		List<Integer> data = new ArrayList<>();
		levelOrderTraversal(root);
		findByLevel(root, 3, data);
		System.out.println(levelWiseSum(root));
	}
}
