package main;

import java.util.ArrayList;
import java.util.List;

public class Subset {
	public void subsets(List<String> subsets, String subset, String s, int i) {
		if (i == s.length()) {
			subsets.add(subset);
			return;
		}
		subsets(subsets, subset + s.charAt(i), s, i + 1);
		subsets(subsets, subset, s, i + 1);
	}
	
	public List<String> subsets(String s) {
		List<String> subsets = new ArrayList<>();
		subsets(subsets, "", s, 0);
		return subsets;
	}
}
