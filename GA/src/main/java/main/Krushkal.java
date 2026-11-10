package main;

import java.util.Arrays;

public class Krushkal {
	
	public class Edge implements Comparable<Edge> {
		public int start;
		public int end;
		public double cost;
		
		public Edge(int start, int end, double cost) {
			this.start = start;
			this.end = end;
			this.cost = cost;
		}
		
		@Override
		public int compareTo(Edge that) {
			if (this.cost < that.cost) return -1;
			if (this.cost > that.cost) return +1;
			return 0;
		}
	}

	private Edge[] edges;
	private UnionFind uf;

	public Krushkal(Edge[] edges, int n) {
		this.edges = edges;
		this.uf = new UnionFind(n);
		Arrays.sort(edges);
	}

	public int solve() {
		int sum = 0;
		for (Edge edge: edges) {
			if (uf.connected(edge.start, edge.end)) continue;
			uf.union(edge.start, edge.end);
			sum += edge.cost;
		}
		return sum;
	}
}