package main;

import java.util.ArrayList;
import java.util.List;

public class Subarray {
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
}
