package main;

public class Main {
	public static void main(String[] args) {
		int a = 2_000_000_000;
		int b = 2_000_000_000;
		int c = a + ((b - a) >> 1);
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
	}
}