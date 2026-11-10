package main;

public class Edge implements Comparable<Edge> {
	public int node;
	public double cost;
	
	public Edge(int node, double cost) {
		this.node = node;
		this.cost = cost;
	}
	
	public int node() {return node;}
	public double cost() {return cost;}
	
	@Override
	public int compareTo(Edge that) {
		if (this.cost < that.cost) return -1;
		if (this.cost > that.cost) return +1;
		return 0;
	}
}
