package main;

public class Window {
	public boolean isVowel(char ch) {
		return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
	}

	public int maxVowelCountInSubstrWithSizeK(String s, int K) {
		int vowelCount = 0;
		for (int i = 0; i < K; i++)
			if (isVowel(s.charAt(i)))
				vowelCount += 1;
		int maxCount = vowelCount;
		for (int i = 0; i < s.length() - K; i++) {
			if (isVowel(s.charAt(i))) vowelCount -= 1;
			if (isVowel(s.charAt(i + K))) vowelCount += 1;
			maxCount = Math.max(maxCount, vowelCount);
		}
		return maxCount;
	}
	
	public int minTotalPrice(int[] nums, int K) {
		int winSum = 0;
		for (int i = 0; i < K; i++)
			winSum += nums[i];
		int minTotal = winSum;
		for (int i = 0; i < nums.length - K; i++) {
			winSum -= nums[i];
			winSum += nums[i + K];
			minTotal = Math.min(minTotal, winSum);
		}
		return minTotal;
	}
}
