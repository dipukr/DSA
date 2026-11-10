package main;

import java.util.Arrays;

public class MinCoins {
	public int minCoins(int[] coins, int target) {
		if (target == 0) return 0;
		int minVal = Integer.MAX_VALUE;
		for (int coin: coins) {
			if (target - coin < 0) continue;
			int subMinVal = minCoins(coins, target - coin) + 1;
			minVal = Math.min(minVal, subMinVal);
		}
		return minVal;
	}

	public int minCoins(int[] coins, int target, int[] cache) {
		if (target == 0) return 0;
		if (cache[target] != -1) return cache[target];
		int minVal = Integer.MAX_VALUE;
		for (int coin: coins) {
			if (target - coin < 0) continue;
			int subMinVal = minCoins(coins, target - coin, cache) + 1;
			minVal = Math.min(minVal, subMinVal);
		}
		return cache[target] = minVal;
	}
	
	public int minCoinsTB(int[] coins, int target) {
		int rows = coins.length + 1;
		int cols = target + 1;
		int[][] table = new int[rows][cols];
		Arrays.fill(table, Integer.MAX_VALUE);
		int answer = Integer.MAX_VALUE;
		for (int i = 1; i < rows; i++) {
			table[i][0] = 0;
			for (int j = 1; j < cols; j++) {
				
			}
		}
		return answer;
	}
}
