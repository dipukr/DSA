package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MissNumbers {
	public List<Integer> missingNumbers(int[] nums) {
		var missingNums = new ArrayList<Integer>();
		var exists = new boolean[nums.length + 1];
		for (int i = 0; i < nums.length; i++)
			exists[nums[i]] = true;
		for (int i = 1; i < exists.length; i++)
			if (!exists[i])
				missingNums.add(i);
		return missingNums;
	}
	
	public List<Integer> missingNumbersNM(int[] nums) {
		var missingNums = new ArrayList<Integer>();
		for (int i = 0; i < nums.length; i++) {
			int num = Math.abs(nums[i]);
			int markPos = num - 1;
			if (nums[markPos] > 0)
				nums[markPos] = -nums[markPos];
		}
		for (int i = 0; i < nums.length; i++)
			if (nums[i] > 0)
				missingNums.add(i + 1);
		return missingNums;
	}
	
	public int smallestMissingNumber(int[] nums) {
		var exists = new boolean[nums.length];
		Arrays.fill(exists, false);
		for (int i = 0; i < nums.length; i++)
			exists[nums[i]] = true;
		int smallest = Integer.MAX_VALUE;
		for (int i = 0; i < exists.length; i++)
			if (exists[i] == false)
				smallest = Math.min(smallest, i);
		return smallest;
	}
	
	public int smallestMissingNumberNM(int[] nums) {
		int smallest = Integer.MAX_VALUE;
		for (int i = 0; i < nums.length; i++) {
			int num = Math.abs(nums[i]);
			if (nums[num] > 0)
				nums[num] = -nums[num];
		}
		for (int i = 0; i < nums.length; i++)
			if (nums[i] > 0)
				smallest = Math.min(smallest, i);
		return smallest;
	}
}
