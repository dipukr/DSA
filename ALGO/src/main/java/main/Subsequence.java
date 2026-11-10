package main;

import java.util.ArrayList;
import java.util.List;

public class Subsequence {
	public void subsequences(List<List<Integer>> subsequences, List<Integer> subseq, int[] data, int index) {
		if (index == data.length) {
			subsequences.add(new ArrayList<>(subseq));
			return;
		}
		subseq.add(data[index]); // include the current element
		subsequences(subsequences, subseq, data, index + 1);
		subseq.remove(subseq.size() - 1); // exclude the current element (backtrack)
		subsequences(subsequences, subseq, data, index + 1);
	}
	
	public List<List<Integer>> subsequences(int[] data) {
		List<List<Integer>> subsequences = new ArrayList<>();
		for (int mask = 0; mask < (1 << data.length); mask++) {
			List<Integer> subsequence = new ArrayList<>();
			for (int i = 0; i < data.length; i++)
				if ((mask & (1 << i)) != 0)
					subsequence.add(data[i]);
			subsequences.add(subsequence);
		}
		return subsequences;
	}
}
