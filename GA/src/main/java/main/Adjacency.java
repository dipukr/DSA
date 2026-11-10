package main;

import java.util.Arrays;
import java.util.LinkedList;

public class Adjacency {
	
	private boolean[][] matrix;
	private int[] prev;
	private int N;

	public Adjacency(int size) {
		this.N = size;
		this.prev = new int[N];
		this.matrix = new boolean[size][size];
	}

	public void addEdge(int from, int to) {
		matrix[from][to] = true;
		matrix[to][from] = true;
	}

	public void removeEdge(int from, int to) {
		matrix[from][to] = false;
		matrix[to][from] = false;
	}
	
	public void DFS(int node, boolean[] visited) {
		if (visited[node]) return;
		visited[node] = true;
		for (int i = 0; i < N; i++)
			if (matrix[node][i] && !visited[i]) {
				prev[i] = node;
				DFS(i, visited);
			}
	}

	public void BFS(int start) {
		boolean[] marked = new boolean[N];
		Arrays.fill(marked, false);
		var queue = new LinkedList<Integer>();
		marked[start] = true;
		queue.offer(start);
		while (!queue.isEmpty()) {
			int u = queue.poll();
			System.out.printf("%d\t", u);
			for (int i = 0; i < N; i++) {
				if (matrix[u][i] && !marked[i]) {
					queue.offer(i);
					prev[i] = u;
					marked[i] = true;
				}
			}
		}
	}
}