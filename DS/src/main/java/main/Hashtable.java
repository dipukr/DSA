package main;

public class Hashtable {
	
	public class Node {
		public Object key;
		public Object val;
		public Node next;
		
		public Node(Object key, Object val, Node next) {
			this.key = key;
			this.val = val;
			this.next = next;
		}
	}

	private Node[] heads = null;
	private int listCount = 0;
	private int count = 0;
	
	public Hashtable() {
		this(8);
	}

	public Hashtable(int listCount) {
		this.count = 0;
		this.listCount = listCount;
		this.heads = new Node[listCount];
	}

	public void resize(int chainCount) {
		Hashtable m = new Hashtable(chainCount);
		for (int i = 0; i < chainCount; i++)
			for (Node node = heads[i]; node != null; node = node.next)
				m.put(node.key, node.val);
		this.heads = m.heads;
		this.count = m.count;
		this.listCount = m.listCount;
	}

	public void put(Object key, Object val) {
		if (count >= 10 * listCount)
			resize(listCount * 2);
		int h = hash(key);
		heads[h] = new Node(key, val, heads[h]);
		count++;
	}

	public boolean contains(Object key) {
		return get(key) != null;
	}

	public Object get(Object key) {
		int h = hash(key);
		for (Node node = heads[h]; node != null; node = node.next)
			if (key.equals(node.key))
				return node.val;
		return null;
	}

	public int hash(Object key) {
		return key.hashCode() % listCount;
	}
	
	public int size() {
		return count;
	}
	
	public boolean empty() {
		return size() == 0;
	}
}