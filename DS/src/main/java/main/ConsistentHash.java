package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class ConsistentHash {
	private TreeMap<Long, Node> ring = new TreeMap<>();

	public ConsistentHash(Node... nodes) {
		for (Node node: nodes) {
			long hash = Tools.hash(node.getId());
			ring.put(hash, node);
		}
	}

	public void addNode(Node node) {
		long hash = Tools.hash(node.getId());
		Entry<Long, Node> predecessorEntry = ring.lowerEntry(hash);
		Entry<Long, Node> successorEntry = ring.ceilingEntry(hash);
		Node predecessor = (predecessorEntry == null) ? ring.get(ring.lastKey()) : predecessorEntry.getValue();
		Node successor = (successorEntry == null) ? ring.get(ring.firstKey()) : successorEntry.getValue();
		ring.put(hash, node);
		moveKeys(successor, node, predecessor.getHash(), hash);
	}

	public void moveKeys(Node src, Node dest, long start, long end) {
		List<String> keys = new ArrayList<>();
		for (String key: src.getData().keySet()) {
			long keyHash = Tools.hash(key);
			if (start < end ? keyHash > start && keyHash <= end : keyHash > start || keyHash <= end) {
				dest.put(key, src.get(key));
				keys.add(key);
			}
		}
		src.removeKeys(keys);
	}

	public void removeNode(Node node) {
		long hash = node.getHash();
		Entry<Long, Node> nextEntry = ring.higherEntry(hash);
		if (nextEntry == null) nextEntry = ring.firstEntry();
		Node successor = nextEntry.getValue();
		successor.getData().putAll(node.getData());
		ring.remove(hash);
	}

	public Node getNode(String key) {
		if (ring.isEmpty()) return null;
		long hash = Tools.hash(key);
		Entry<Long, Node> entry = ring.ceilingEntry(hash);
		if (entry == null) entry = ring.firstEntry();
		return entry.getValue();
	}
}
