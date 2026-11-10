package main;

import java.security.MessageDigest;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash {
	private TreeMap<Long, String> ring = new TreeMap<>();
	private MessageDigest md;
	
	public ConsistentHash() {
		this.md = Commons.sha256();
	}
	
	public void addServer(String server) {
		long hash = hash(server);
		ring.put(hash, server);
	}
	
	public void removeServer(String server) {
		long hash = hash(server);
		ring.remove(hash, server);
	}
	
	public String getServer(String key) {
		if (ring.isEmpty()) return null;
		long hash = hash(key);
		if (!ring.containsKey(hash)) {
			SortedMap<Long, String> tailMap = ring.tailMap(hash);
			hash = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
		}
		return ring.get(hash);
	}
	
	public long hash(String key) {
		md.reset();
		md.update(key.getBytes());
		byte[] digest = md.digest();
		long hash = ((long) (digest[0] & 0xFF) << 56) |
			((long) (digest[1] & 0xFF) << 48) |
			((long) (digest[2] & 0xFF) << 40) |
			((long) (digest[3] & 0xFF) << 32) |
			((long) (digest[4] & 0xFF) << 24) |
			((long) (digest[5] & 0xFF) << 16) |
			((long) (digest[6] & 0xFF) <<  8) |
			((long) (digest[7] & 0xFF));
		return hash;
	}
}
