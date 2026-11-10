package main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PathFinder {

	private Map<Node, Integer> dist = new HashMap<>();
	private Map<Node, Node> prev = new HashMap<>();

	public PathFinder(Node start) {
		var queue = new LinkedList<Node>();
		queue.offer(start);
		dist.put(start, 0);
		while (!queue.isEmpty()) {
			Node curr = queue.poll();
			for (Node node: curr.adjacents)
				if (!dist.containsKey(node)) {
					dist.put(node, dist.get(curr) + 1);
					queue.offer(node);
					prev.put(node, curr);
				}
		}
	}

	public boolean hasPathTo(Node node) {
		return dist.containsKey(node);
	}

	public int distanceTo(Node node) {
		if (!hasPathTo(node)) return Integer.MAX_VALUE;
		return dist.get(node);
	}

	public List<Node> pathTo(Node node) {
		var path = new LinkedList<Node>();
		while (node != null && dist.containsKey(node)) {
			path.addFirst(node);
			node = prev.get(node);
		}
		return path;
	}
}