package main;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class Dijkstra {
	
	private Queue<Edge> queue = new PriorityQueue<>();
	private Weighted wg;
	private double dist[];
	private boolean[] visited;

	public Dijkstra(Weighted wg, int start) {
		this.wg = wg;
		this.dist = new double[wg.size()];
		this.visited = new boolean[wg.size()];
		Arrays.fill(dist, Integer.MAX_VALUE);
		search(start);
	}

	public void search(int start) {
		queue.offer(new Edge(start, 0));
		dist[start] = 0;
		visited[start] = true;
		while (!queue.isEmpty()) {
			int u = queue.poll().node();
			for (Edge e: wg.adjacents(u)) {
				int v = e.node();
				double c = e.cost();
				if (!visited[v] && dist[u] + c < dist[v]) {
					dist[v] = dist[u] + c;
					queue.offer(e);
				}
			}
		}
	}

	public boolean hasPathTo(int v) {
		return dist[v] != Double.POSITIVE_INFINITY;
	}

	public double distanceTo(int v) {
		return dist[v];
	}
}