package main;

public class Trie {

	public class Node {
		public boolean word;
		public Node[] childs = new Node[26];
	}

	private Node root = new Node();

	public void insert(String word) {
		Node curr = root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (curr.childs[c - 'a'] == null)
				curr.childs[c - 'a'] = new Node();
			curr = curr.childs[c - 'a'];
		}
		curr.word = true;
	}
	
	public boolean search(String word) {
		Node node = getNode(word);
		return node != null && node.word;
	}
	
	public boolean startsWith(String prefix) {
		return getNode(prefix) != null;
	}

	public Node getNode(String word) {
		Node curr = root;
		for (int i = 0; i < word.length(); i++) {
			char ch = word.charAt(i);
			if (curr.childs[ch - 'a'] == null)
				return null;
			curr = curr.childs[ch - 'a'];
		}
		return curr;
	}
}
