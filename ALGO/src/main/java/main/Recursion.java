package main;

import java.util.List;

public class Recursion {
	public long fibonacci(long n) {
		return (n == 1 || n == 2) ? n : fibonacci(n - 1) + fibonacci(n -2);
	}
	
	public long factorial(long n) {
		return (n == 0) ? 1 : n * factorial(n - 1);
	}

	public long sum(long n) {
		return (n == 0) ? 0 : n + sum(n - 1);
	}

	public long gcd(long a, long b) {
		return (b == 0) ? a : gcd(b, a % b);			
	}

	public long pow(long x, long n) {
		return (n == 0) ? 1 : x * pow(x, n - 1);
	}

	public long power(long x, long n) {
		if (n == 0) return 1;
		else if (n % 2 == 0) {
			long val = power(x, n / 2);
			return val * val;
		} else return x * power(x, n);
	}

	public long digitSum(long n) {
		return (n < 10) ? n : (n % 10) + digitSum(n / 10);
	}

	public void printDigit(long n) {
		if (n > 9)
			printDigit(n / 10);
		System.out.print(n % 10);
	}

	public void printDigit(int num, int base) {
		if (num >= base)
			printDigit(num / base, base);
		System.out.print("0123456789ABCDEF".charAt(num % base));
	}

	public void printDecOrder(long n) {
		if (n == 0) return;
		System.out.println(n);
		printDecOrder(n - 1);
	}

	public void printIncOrder(long n) {
		if (n == 0) return;
		printIncOrder(n - 1);
		System.out.println(n);
	}

	public void printReverse(String s) {
		if (s.length() == 0) return;
		String ros = s.substring(1);
		reverse(ros);
		System.out.print(s.charAt(0));
	}

	public String reverse(String s) {
		if (s.length() == 1)
			return s;
		return reverse(s.substring(1))
				+ s.charAt(0);	
	}

	public int findFirstOccurance(int[] arr, int key, int i) {
		if (i == arr.length) return -1;
		if (arr[i] == key) return i;
		return findFirstOccurance(arr, key, i + 1);
	}

	public int findLastOccurance(int[] arr, int key, int i) {
		if (i == arr.length) return -1;
		int answer = findLastOccurance(arr, key, i + 1);
		if (answer != -1) return answer;
		if (arr[i] == key) return i;
		return -1;
	}

	public String replacePiWith3_14(String s) {
		if (s.length() == 2) return s;
		if (s.charAt(0) == 'p' && s.charAt(1) == 'i')
			return "3.14" + replacePiWith3_14(s.substring(2));
		return s.charAt(0) + replacePiWith3_14(s.substring(1));
	}

	public String removeDuplicateAdjacentChar(String s) {
		if (s.length() == 1) return s;
		String ros = removeDuplicateAdjacentChar(s.substring(1));
		if (s.charAt(0) == ros.charAt(0)) return ros;
		return s.charAt(0) + ros;
	}

	public void hanoi(int n, char src, char dest, char helper) {
		hanoi(n - 1, src, dest, helper);
		hanoi(n - 2, src, dest, helper);
		hanoi(n - 3, src, dest, helper);
	}
	
	public boolean isPalindrome(String s, int l, int r) {
		if (l < r) return true;
		if (s.charAt(l) != s.charAt(r)) return false;
		return isPalindrome(s, l + 1, r - 1);
	}
	
	public void powerSet(String s, int i, String current, List<String> powerSets) {
		if (i == s.length()) {
			powerSets.add(current);
			return;
		}
		powerSet(s, i + 1, current + s.charAt(i), powerSets);
		powerSet(s, i + 1, current, powerSets);
	}
}