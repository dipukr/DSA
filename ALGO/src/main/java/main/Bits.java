package main;

public class Bits {
	public void printBits(int n) {
		if (n < 2) {
			System.out.print(n);
			return;
		}
		int bit = n % 2;
		printBits(n / 2);
		System.out.print(bit);
	}

	public void printRev(int n) {
		while (n != 0) {
			System.out.print(n % 2);
			n = n / 2;
		}
	}

	public int getBit(int n, int pos) {
		int mask = (1 << pos);
		return (n & mask) == 0 ? 0 : 1;
	}

	public int setBit(int n, int pos) {
		int mask = (1 << pos);
		return n | mask;
	}

	public int clearBit(int n, int pos) {
		int mask = ~(1 << pos);
		return n & mask;
	}
	
	public int flipBit(int n, int pos) {
		int mask = (1 << pos);
		return n ^ mask;
	}

	public int updateBit(int n, int pos, int val) {
		int mask = ~(1 << pos);
		n = n & mask;
		return n | (val << pos);
	}

	public boolean even(int n) {
		return (n & 1) == 0;
	}
	
	public boolean odd(int n) {
		return (n & 1) == 1;
	}
	
	public boolean isPower2(int n) {
		return n > 0 && (n & (n - 1)) == 0;
	}
	
	public static void main(String[] args) {
		Bits a = new Bits();
		int n = 1729;
		a.printBits(n);
		System.out.println();
		System.out.println(a.getBit(n, 6));
		System.out.println(a.even(n));
		System.out.println(a.odd(n));
	}
}