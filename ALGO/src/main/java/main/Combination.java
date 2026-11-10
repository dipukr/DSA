package main;

import java.util.ArrayList;
import java.util.List;

public class Combination {
	public void combinations(List<List<Integer>> combinations, List<Integer> currComb, int N, int K, int start) {
		if (currComb.size() == K) {
			combinations.add(List.copyOf(currComb));
			return;
		}
		for (int num = start; num <= N; num += 1) {
			currComb.add(num);
			combinations(combinations, currComb, N, K, start + 1);
			currComb.remove(currComb.size() - 1);
		}
	}
	
	public List<List<Integer>> combinations(int N, int K) {
		List<List<Integer>> combinations = new ArrayList<>();
		List<Integer> currComb = new ArrayList<>();
		combinations(combinations, currComb, 0, N, K);
		return combinations;
	}
}
