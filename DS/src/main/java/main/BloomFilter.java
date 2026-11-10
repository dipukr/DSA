package main;

import java.util.BitSet;
import java.util.zip.CRC32;
import java.util.zip.Adler32;

public class BloomFilter {
	private BitSet bitSet;
	private int numHash;

	public BloomFilter(int size, int numHash) {
		this.bitSet = new BitSet(size);
		this.numHash = numHash;
	}

	public void add(String value) {
		for (int hash: getHashes(value))
			bitSet.set(Math.abs(hash % bitSet.size()));
	}

	public boolean mightContain(String value) {
		for (int hash: getHashes(value))
			if (!bitSet.get(Math.abs(hash % bitSet.size())))
				return false;
		return true;
	}

	public int[] getHashes(String value) {
		int[] hashes = new int[numHash];
		for (int i = 0; i < numHash; i++) {
			String combined = i + value;
			int hash = combined.hashCode();
			hashes[i] = hash;
		}
		return hashes;
	}

	public static long crc32(String input) {
		CRC32 crc = new CRC32();
		crc.update(input.getBytes());
		return crc.getValue();
	}

	public static long adler32(String input) {
		var adler = new Adler32();
		adler.update(input.getBytes());
		return adler.getValue();
	}

	public static void main(String[] args) {
		BloomFilter filter = new BloomFilter(1024, 3);

		filter.add("apple");
		filter.add("mango");

		Console.draw("apple: ", filter.mightContain("apple")); // true
		Console.draw("mango: ", filter.mightContain("mango")); // true
		Console.draw("lemon: ", filter.mightContain("lemon")); // false (probably)
	}
}
