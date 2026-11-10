package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Array {
	public List<int[]> subarrays(int[] data) {
		var subArrays = new ArrayList<int[]>();
		for (int start = 0; start < data.length; start++) {
			for (int end = start; end < data.length; end++) {
				int[] subArray = new int[end - start + 1];
				for (int k = start; k <= end; k++)
					subArray[k - start] = data[k];
				subArrays.add(subArray);
			}
		}
		return subArrays;
	}

	public void draw(int[] data, int low, int high) {
		String s = Arrays.stream(data)
			.mapToObj(Integer::toString)
			.collect(Collectors.joining(",", "[", "]"));
		System.out.println(s);
	}

	public void draw(int[] data) {
		draw(data, 0, data.length - 1);
	}

	public int sumArray(int[] data, int low, int high) {
		int sum = 0;
		for (int i = low; i <= high; i++)
			sum += data[i];
		return sum;
	}

	public int sumArray(int[] data) {
		return sumArray(data, 0, data.length - 1);
	}

	public void drawSubarrays(int[] data) {
		for (int i = 0; i < data.length; i++)
			for (int j = i; j < data.length; j++)
				draw(data, i, j);
	}

	public int sumSubarrays(int[] data) {
		int sum = 0;
		for (int i = 0; i < data.length; i++)
			for (int j = i; j < data.length; j++)
				sum += sumArray(data, i, j);
		return sum;
	}

	public int max(int[] data) {
		int max = data[0];
		for (int i = 1; i < data.length; i++)
			max = Math.max(max, data[i]);
		return max;
	}

	public int min(int[] data) {
		int min = data[0];
		for (int i = 1; i < data.length; i++)
			min = Math.min(min, data[i]);
		return min;
	}

	public int[] reverse(int[] data) {
		int[] result = new int[data.length];
		for (int i = 0; i < data.length; i++)
			result[i] = data[data.length - i - 1];
		return result;
	}

	public void reverseTP(int[] data) {
		int first = 0;
		int second = data.length - 1;
		while (first <= second) {
			int saved = data[first];
			data[first] = data[second];
			data[second] = saved;
			first++;
			second--;
		}
	}

	public int maxSumPair(int[] data) {
		int maxSum = Integer.MIN_VALUE;
		for (int i = 0; i < data.length; i++)
			for (int j = i + 1; j < data.length; j++)
				maxSum = Math.max(maxSum, data[i] + data[j]);
		return maxSum;
	}

	public void shuffle(int[] data) {
		var rand = new Random();
		for (int i = 0; i < data.length; i++) {
			int j = rand.nextInt(data.length);
			int saved = data[i];
			data[i] = data[j];
			data[j] = saved;
		}
	}

	public boolean hasDuplicates(int[] data) {
		for (int i = 0; i < data.length; i++)
			for (int j = i + 1; j < data.length; j++)
				if (data[i] == data[j])
					return true;
		return false;
	}

	public boolean containDuplicates(int[] data) {
		var set = new HashSet<Integer>(data.length);
		for (int elem : data) {
			if (set.contains(elem)) return true;
			else set.add(elem);
		}
		return false;
	}

	public int smallestSubarrayWithSumS(int[] data, int S) {
		int ans = Integer.MAX_VALUE;
		int lhs = 0, sum = 0;
		for (int rhs = 0; rhs < data.length; rhs++) {
			sum += data[rhs];
			while (sum >= S) {
				ans = Math.min(ans, rhs - lhs + 1);
				sum -= data[lhs];
				lhs++;
			}
		}
		return ans == Integer.MAX_VALUE ? 0 : ans;
	}

}
