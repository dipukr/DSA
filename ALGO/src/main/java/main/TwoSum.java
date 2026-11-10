package main;

import java.util.Arrays;
import java.util.HashMap;

public class TwoSum {
	public int[] twoSum(int[] nums, int target) {
		for (int i = 0; i < nums.length; i++)
			for (int j = i + 1; j < nums.length; j++)
				if (nums[i] + nums[j] == target)
					return new int[] {i, j};
		return null;
	}
	
	public int[] twoSumOP(int[] nums, int target) {
		var complements = new HashMap<Integer, Integer>();
		for (int i = 0; i < nums.length; i++) {
			if (complements.containsKey(nums[i]))
				return new int[] {i, complements.get(nums[i])};
			complements.put(target - nums[i], i);
		}
		return null;
	}
	
	public int[] twoSumTP(int[] nums, int target) {
		Arrays.sort(nums);
		int lhs = 0;
		int rhs = nums.length - 1;
		while (lhs < rhs) {
			int sum = nums[lhs] + nums[rhs];
			if (sum == target)
				return new int[] {nums[lhs], nums[rhs]};
			if (sum < target) lhs += 1;
			if (sum > target) rhs -= 1;
		}
		return null;
	}
	
	public int[] twoSumBS(int[] nums, int target) {
		Arrays.sort(nums);
		for (int i = 0; i < nums.length; i++) {
			int other = target - nums[i];
			int found = Arrays.binarySearch(nums, other);
			if (found != -1)
				return new int[]{nums[i], other};
		}
		return null;
	}
}
