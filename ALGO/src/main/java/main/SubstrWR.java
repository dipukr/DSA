package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class SubstrWR {
	public int longestSubstr(String s) {
		int maxlen = 0;
		for (int start = 0; start < s.length(); start++) {
			for (int end = start; end < s.length(); end++) {
				var set = new TreeSet<Character>();
				for (int a = start; a <= end; a++)
					set.add(s.charAt(a));
				if (set.size() == end - start + 1)
					maxlen = Math.max(maxlen, set.size());
			}
		}
		return maxlen;
	}

	public int longestSubstrSW(String s) {
		int maxLen = 0;
		int start = 0, end = 0;
		var set = new HashSet<Character>();
		while (end < s.length()) {
			char ch = s.charAt(end);
			if (set.contains(ch)) {
				set.remove(s.charAt(start));
				start++;
			} else {
				set.add(ch);
				maxLen = Math.max(maxLen, end - start + 1);
				end++;
			}
		}
		return maxLen;
	}
	
	public int longestSubstrSW2(String s) {
		int max = 0;
		int start = 0, end = 0;
		var set = new HashSet<Character>();
		while (end < s.length()) {
			char ch = s.charAt(end);
			while (set.contains(ch)) {
				set.remove(ch);
				start++;
			}
			set.add(ch);
			max = Math.max(max, end - start + 1);
			end++;
		}
		return max;
	}
	
	public int longestSubstrSW3(String s) {
		int max = 0;
		int start = 0;
		var mem = new HashMap<Character, Integer>();
		for (int end = 0; end < s.length(); end++) {
			char ch = s.charAt(end);
			if (mem.containsKey(ch) && mem.get(ch) >= start)
				start = mem.get(ch) + 1;
			mem.put(ch, end);
			max = Math.max(max, end - start + 1);
		}
		return max;
	}

	public int longestSubstrSW4(String s) {
		int[] last = new int[256]; // tracks last positions of characters
		Arrays.fill(last, -1);
		int left = 0, max = 0;
		for (int right = 0; right < s.length(); right++) {
			char c = s.charAt(right);
			// If character was seen inside the window
			if (last[c] >= left)
				left = last[c] + 1; // move left pointer
			last[c] = right; // update last seen index
			max = Math.max(max, right - left + 1);
		}
		return max;
	}

	public static void main(String[] args) {
		var obj = new SubstrWR();
		System.out.println(obj.longestSubstr("abcabcdbb"));
		System.out.println(obj.longestSubstrSW("abcabcdbb"));
	}
}
