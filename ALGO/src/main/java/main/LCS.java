package main;

public class LCS {
	public String lcs(String x, String y) {
		int m = x.length();
		int n = y.length();
		int[][] opt = new int[m + 1][n + 1];
		for (int i = m - 1; i >= 0; i--)
			for (int j = n - 1; j >= 0; j--)
				if (x.charAt(i) == y.charAt(j))
					opt[i][j] = opt[i+1][j+1] + 1;
				else
					opt[i][j] = Math.max(opt[i + 1][j], opt[i][j + 1]);

		// Recover LCS itself.
		String lcs = "";
		int i = 0, j = 0;
		while (i < m && j < n) {
			if (x.charAt(i) == y.charAt(j)) {
				lcs += x.charAt(i);
				i++;
				j++;
			} 
			else if (opt[i + 1][j] >= opt[i][j + 1]) i++;
			else j++;
		}
		return lcs;
	}
	
	public int lcs(String x, String y, int i, int j) {
		if (i == x.length() || j == y.length()) return 0;
		if (x.charAt(i) == y.charAt(j)) return 1 + lcs(x, y, i + 1, j + 1);
		return Math.max(lcs(x, y, i, j + 1), lcs(x, y, i + 1, j));
	}
	
	public int lcs(int[] A, int[] B, int i, int j) {
		if (i == A.length || j == B.length) return 0;
		if (A[i] == B[j]) return 1 + lcs(A, B, i + 1, j + 1);
		return Math.max(lcs(A, B, i, j + 1), lcs(A, B, i + 1, j));
	}
}