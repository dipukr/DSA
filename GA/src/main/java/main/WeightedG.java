package main;

import java.util.HashMap;
import java.util.Map;

public class WeightedG {

	private Map<Integer, Map<Integer, Double>> map = new HashMap<>();

	public void addNode(int node) {
		if (map.containsKey(node)) Error.fatal("Node already exist.");;
		map.put(node, new HashMap<Integer, Double>());
	}

	public void addEdge(int src, int dest, double weight) {
		if (!map.containsKey(src) || !map.containsKey(dest))
			Error.fatal("Both nodes must be in the graph.");
		map.get(src).put(dest, weight);
	}

	public void removeEdge(int src, int dest) {
		if (!map.containsKey(src) || !map.containsKey(dest))
			Error.fatal("Both nodes must be in the graph.");
		map.get(src).remove(dest);
	}

	public boolean containsNode(int node) {
		return map.containsKey(node);
	}

	public Iterable<Integer> adjacents(int node) {
		Map<Integer, Double> edges = map.get(node);
		if (edges == null)
			Error.fatal("Node doesnot exist in the graph.");
		return edges.keySet();
	}

	public double cost(int src, int dest) {
		if (!map.containsKey(src) || !map.containsKey(dest))
			Error.fatal("Both nodes must be in the graph.");
		Double weight = map.get(src).get(dest);
		if (weight == null)
			Error.fatal("Edge doesnot exist in the graph.");
		return weight;
	}

	public int size() {
		return map.size();
	}
	
	public boolean empty() {
		return map.isEmpty();
	}
	
	@Override
	public String toString() {
		return map.toString();
	}
}