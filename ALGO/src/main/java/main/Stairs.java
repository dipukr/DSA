package main;

public class Stairs {
	public int climbStairs(int n) {
		int[] tbl = new int[n + 1];
		tbl[0] = 1;
		tbl[1] = 1;
		for (int i = 2; i <= n; i++)
			tbl[i] = tbl[i - 1] + tbl[i - 2];
		return tbl[n];
	}
	
	public int climbStairs(int n, int[] cache) {
		if (n < 2) return 1;
		if (cache[n] != 0) return cache[n];
		return cache[n] = climbStairs(n - 1, cache) + climbStairs(n - 2, cache);
	}
}
