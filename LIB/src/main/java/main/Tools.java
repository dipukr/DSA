package main;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Tools {
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	private static MessageDigest md = algorithm("SHA-256");
	private static final Random rand = new Random();
	
	public static MessageDigest algorithm(String algo) {
		try {
			return MessageDigest.getInstance(algo);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public static String sha256(String input) {
		try {
			var digest = MessageDigest.getInstance("SHA-256");
			byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
			byte[] hash = digest.digest(bytes);
			var hexval = new StringBuilder();
			for (byte b: hash)
				hexval.append(String.format("%02x", b));
			return hexval.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	
	public static long hash(String key) {
		md.reset();
		md.update(key.getBytes());
		byte[] digest = md.digest();
		long hash = ((long) (digest[0] & 0xFF) << 56) |
			((long) (digest[0] & 0xFF) << 48) |
			((long) (digest[0] & 0xFF) << 40) |
			((long) (digest[0] & 0xFF) << 32) |
			((long) (digest[0] & 0xFF) << 24) |
			((long) (digest[0] & 0xFF) << 16) |
			((long) (digest[0] & 0xFF) << 8) |
			((long) (digest[0] & 0xFF));
		return hash;
	}
	
	public static int randInt(int bound) {
		return rand.nextInt(bound);
	}
	
	public static int randInt(int origin, int bound) {
		return rand.nextInt(origin, bound);
	}
	
	public static String randStr(int len) {
		var sb = new StringBuilder();
		for (int i = 0; i < len; i++)
			sb.append(ALPHABET.charAt(randInt(ALPHABET.length())));
		return sb.toString();
	}
}
