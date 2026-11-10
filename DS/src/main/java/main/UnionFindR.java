package main;

public class UnionFindR {

	private int[] parent;
	private int[] rank;

	public UnionFindR(int n) {
		parent = new int[n];
		rank = new int[n];
		for (int i = 0; i < n; i++)
			parent[i] = i;
	}

	public int find(int u) {
		if (parent[u] != u)
			parent[u] = find(parent[u]);
		return parent[u];
	}
	
	public int root(int u) {
		while (u != parent[u])
			u = parent[u];
		return u;
	}

	public void union(int u, int v) {
		int uroot = find(u);
		int vroot = find(v);
		if (uroot == vroot) return;
		if (rank[uroot] < rank[vroot])
			parent[uroot] = vroot;
		if (rank[uroot] > rank[vroot])
			parent[vroot] = uroot;
		if (rank[uroot] == rank[vroot]) {
			parent[vroot] = uroot;
			rank[uroot] += 1;
		}
	}
	
	public boolean connected(int u, int v) {
		return find(u) == find(v);
	}
}
