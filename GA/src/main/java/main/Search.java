package main;

import java.util.LinkedList;

public class Search {

	public void DFS(Node node) {
		visit(node);
		for (Node adjacent: node.adjacents)
			if (!adjacent.marked)
				DFS(adjacent);
	}

	public void BFS(Node startNode) {
		var queue = new LinkedList<Node>();
		queue.offer(startNode);
		visit(startNode);
		while (!queue.isEmpty()) {
			Node currNode = queue.poll();
			for (Node adjacent: currNode.adjacents) {
				if (!adjacent.marked) {
					visit(adjacent);
					queue.offer(currNode);
				}
			}	
		}
	}

	public void visit(Node node) {
		node.marked = true;
		System.out.printf("%s ", node.data);
	}
}