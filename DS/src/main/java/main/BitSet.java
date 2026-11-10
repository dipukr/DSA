package main;

public class BitSet {

	private long[] data;
	private int size;

	public BitSet(int size) {
		this.data = new long[(size / 64) + 1];
		this.size = size;
	}

	public void set(int index) {
		data[index / 64] |= (1L << (index % 64));
	}
	
	public boolean get(int index) {
		return (data[index / 64] & (1L << (index % 64))) == 1L;
	}

	public boolean contains(int index) {
		return (data[index / 64] & (1L << (index % 64))) != 0;
	}
	
	public int size() {
		return size;
	}
}