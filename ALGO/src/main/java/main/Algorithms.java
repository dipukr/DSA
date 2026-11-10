package main;

import java.util.ArrayList;
import java.util.List;

public class Algorithms {
	public int max(int a, int b, int c) {
		return (a > b) ?
			   (a > c) ? a : c :
			   (b > c) ? b : c;
	}

	public int maxB(int a, int b, int c) {
		return Math.max(a, Math.max(b, c));
	}

	public int digitCount(int num) {
		int count = 0;
		while (num > 0) {
			count += 1;
			num = num / 10;
		}
		return count;
	}
	
	public int digitSum(int num) {
		int result = 0;
		while (num > 0) {
			result += num % 10;
			num = num / 10;
		}
		return result;
	}

	public int digital(int num) {
		while (num > 9)
			num = digitSum(num);
		return num;
	}
	
	public int GCD(int a, int b) {
		while (b > 0) {
			int rem = a % b;
			a = b;
			b = rem;
		}
		return a;
	}
	
	public int factorial(int num) {
		int fact = 1;
		for (int i = num; i > 0; i--)
			fact = fact * i;
		return fact;
	}

	public int fibonacci(int n) {
		int first = 0;
		int second = 1;
		int next = 0;
		for (int i = 3; i <= n; i++) {
			next = first + second;
			first = second;
			second = next;
		}
		return next;
	}

	public boolean isFibonacci(int n) {
		int i = 0;
		while (true) {
			int val = fibonacci(i);
			if (val == n) return true;
			if (val > n) return false;
			if (val < n) i++;
		}
	}

	public int pow(int x, int n) {
		int result = 1;
		for (int i = 1; i <= n; i++)
			result = result * x;
		return result;
	}

	public int reverse(int num) {
		int value = 0;
		while (num > 0) {
			int digit = num % 10;
			value = value * 10 + digit;
			num = num / 10;
		}
		return value;
	}

	public boolean isPrime(int n) {
		for (int i = 2; i < n; i++)
			if (n % 2 == 0)
				return false;
		return true;
	}

	public int nthPrime(int n) {
		return 0;
	}

	public List<Integer> findFactors(int n) {
		var factors = new ArrayList<Integer>();
		for (int i = 1; i <= n; i++)
			if (n % i == 0)
				factors.add(i);
		return factors;
	}

	public List<Integer> getFactors(int n) {
		var factors = new ArrayList<Integer>();
		for (int i = 1; i <= Math.sqrt(n); i++) {
			if (n % i == 0) {
				factors.add(i);
				factors.add(n / i);	
			}
		}
		return factors;
	}
	
	public int parseInt(char[] chars) {
		int result = 0;
		for (char ch: chars) {
			int d = ch - '0';
			result = result * 10 + d;
		}
		return result;
	}
	
	public int intValue(char[] chars) {
		int factor = pow(10, chars.length - 1);
		int result = 0;
		for (char ch: chars) {
			int d = ch - '0';
			result += d * factor;
			factor = factor / 10;
		}
		return result;
	}
	
	public void binary(int decimal) {
		while (decimal > 0) {
			int digit = decimal % 2;
			System.out.print(digit);
			decimal = decimal / 2;
		}
	}
	
	public void octal(int decimal) {
		while (decimal > 0) {
			int digit = decimal % 8;
			System.out.print(digit);
			decimal = decimal / 8;
		}
	}
}
