package main;

public class Fibonacci {
	public int fibonacci(int n) {
		if (n == 0) return 0;
		if (n == 1) return 1;
		return fibonacci(n - 1) + fibonacci(n - 2);
	}
	
	public int fibonacci(int n, int[] cache) {
		if (cache[n] != -1) return cache[n];
		cache[n] = fibonacci(n - 1, cache) + fibonacci(n - 2, cache);
		return cache[n];
	}
	
	@REM("BottomUP")
	public int fibonacciDP(int n) {
		int[] table = new int[n + 1];
		table[0] = 0;
		table[1] = 1;
		for (int i = 2; i <= n; i++)
			table[i] = table[i - 1] + table[i - 2];
		return table[n];
	}
}
