package main;

import java.util.List;
import java.util.ArrayList;

public class DGraph {
	
	private List<Node> nodes = new ArrayList<Node>();

	public void addNode(Node node) {
		nodes.add(node);
	}

	public void addEdge(Node start, Node end) {
		start.adjacents.add(end);
	}
}