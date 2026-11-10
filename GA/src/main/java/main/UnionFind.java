package main;

public class UnionFind {

	private int[] parent;

	public UnionFind(int n) {
		parent = new int[n];
		for (int i = 0; i < n; i++)
			parent[i] = i;
	}

	public int find(int x) {
		if (parent[x] == x) return x;
		return find(parent[x]);
	}

	public boolean connected(int u, int v) {
		return find(u) == find(v);
	}

	public void union(int u, int v) {
		int r1 = find(u);
		int r2 = find(v);
		if (r1 == r2) return;
		parent[r2] = r1;
	}
}