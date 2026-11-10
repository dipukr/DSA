package main;

import java.util.LinkedList;

public class WAG {

	private double[][] matrix;
	private int N;
	
	public WAG(int N) {
		this.N = N;
		this.matrix = new double[N][];
	}
	
	public void DFS(int startNode, boolean[] visited, int[] prev) {
		if (visited[startNode]) return;
		visited[startNode] = true;
		for (int i = 0; i < N; i++) {
			if (matrix[startNode][i] == 0.0 && !visited[i]) {		
			}
		}	
	}
	
	public void BFS(int startNode) {
		var queue = new LinkedList<Integer>();
		queue.offer(startNode);
		while (!queue.isEmpty()) {
			int data = queue.poll();
		}
	}
}
