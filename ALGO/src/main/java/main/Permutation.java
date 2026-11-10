package main;

import java.util.ArrayList;
import java.util.List;

public class Permutation {
	public List<List<Integer>> perm(int N) {
		List<List<Integer>> permutations = new ArrayList<>();
		List<Integer> currPerm = new ArrayList<>();
		perm(N, currPerm, permutations);
		return permutations;
	}

	public void perm(int N, List<Integer> currPerm, List<List<Integer>> permutations) {
		if (currPerm.size() == N) {
			permutations.add(new ArrayList<>(currPerm));
			return;
		}
		for (int num = 1; num <= N; num++) {
			currPerm.add(num);
			perm(N, currPerm, permutations);
			currPerm.remove(currPerm.size() - 1);
		}
	}
	
	public List<List<Integer>> permutations(int[] data) {
		List<List<Integer>> permutations = new ArrayList<>();
		List<Integer> currPerm = new ArrayList<>();
		permutations(data, currPerm, permutations);
		return permutations;
	}
	
	public void permutations(int[] elems, List<Integer> currPerm, List<List<Integer>> permutations) {
		if (currPerm.size() == elems.length) {
			permutations.add(new ArrayList<>(currPerm));
			return;
		}
		for (int elem: elems) {
			if (currPerm.contains(elem)) continue;
			currPerm.add(elem);
			permutations(elems, currPerm, permutations);
			currPerm.remove(currPerm.size() - 1);
		}
	}

	public void permutationsDUP(int[] elems, List<Integer> currPerm, List<List<Integer>> permutations) {
		if (currPerm.size() == elems.length) {
			permutations.add(new ArrayList<>(currPerm));
			return;
		}
		for (int elem: elems) {
			currPerm.add(elem);
			permutationsDUP(elems, currPerm, permutations);
			currPerm.remove(currPerm.size() - 1);
		}
	}
}
