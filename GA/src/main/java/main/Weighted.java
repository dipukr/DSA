package main;

import java.util.ArrayList;
import java.util.List;

public class Weighted {
	
	private List<List<Edge>> adj;

	public Weighted(int N) {
		this.adj = new ArrayList<>();
		for (int i = 0; i < adj.size(); i++)
			adj.add(new ArrayList<>());
	}

	public void addEdge(int start, int end, int cost) {
		adj.get(start).add(new Edge(end, cost));
	}
	
	public List<Edge> adjacents(int u) {
		return adj.get(u);
	}
	
	public int size() {
		return adj.size();
	}
}
