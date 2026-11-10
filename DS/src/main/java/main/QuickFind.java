package main;

public class QuickFind {

	private int[] id;

	public QuickFind(int n) {
		id = new int[n];
		for (int i = 0; i < n; i++)
			id[i] = i;
	}

	public int find(int u) {
		return id[u];
	}

	public void union(int u, int v) {
		int pid = id[u];
		for (int i = 0; i < id.length; i++)
			if (id[i] == pid)
				id[i] = id[v];
	}
	
	public boolean connected(int u, int v) {
		return find(u) == find(v);
	}
}