package main;

public class MaxSum {
	public int[] getPrefixSum(int[] data) {
		int[] sum = new int[data.length];
		sum[0] = data[0];
		for (int i = 1; i < data.length; i++)
			sum[i] = sum[i - 1] + data[i];
		return sum;
	}
	
	public int maxSubarraySum(int[] data) {
		int maxSum = Integer.MIN_VALUE;
		for (int i = 0; i < data.length; i++) {
			for (int j = i; j < data.length; j++) {
				int localSum = 0;
				for (int k = i; k <= j; k++)
					localSum += data[k];
				maxSum = Math.max(maxSum, localSum);
			}
		}
		return maxSum;
	}
	
	public int maxSubarraySumDP(int[] data) {
		int[] sum = getPrefixSum(data);
		int maxSum = Integer.MIN_VALUE;
		for (int i = 0; i < data.length; i++) {
			for (int j = i; j < data.length; j++) {
				int currSum = sum[j] - (i == 0 ? 0 : sum[i - 1]);
				maxSum = Math.max(maxSum, currSum);
			}
		}
		return maxSum;
	}
	
	public int maxSubarraySumSW(int[] data) {
		int[] sum = getPrefixSum(data);
		int low = 0;
		int high = data.length - 1;
		int max = Integer.MIN_VALUE;
		while (low < high) {
			int wsum = sum[high] - (low == 0 ? 0 : sum[low - 1]);
			int lsum = wsum - data[low];
			int hsum = wsum - data[high];
			if (lsum > hsum) {
				low += 1;
				max = Math.max(max, lsum);
			} else {
				high -= 1;
				max = Math.max(max, hsum);
			}
		}
		return max;
	}
}
