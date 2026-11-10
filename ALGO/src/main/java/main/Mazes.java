package main;

public class Mazes {
	public int[][] clone(int[][] src) {
		int[][] out = new int[src.length][];
		for (int i = 0; i < src.length; i++)
			out[i] = src[i].clone();
		return out;
	}
	public int pathCount(int row, int col) {
		if (row == 0 || col == 0) return 1;
		int count = 0;
		if (row > 0) count += pathCount(row - 1, col);
		if (col > 0) count += pathCount(row, col - 1);
		return count;
	}
	
	public int solve(int rows, int cols) {
		int[][] ans = new int[rows][cols];
		ans[0][0] = 1;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (i > 0) ans[i][j] += ans[i - 1][j];
				if (j > 0) ans[i][j] += ans[i][j - 1];
			}
		}
		return ans[rows - 1][cols - 1];
	}
}
