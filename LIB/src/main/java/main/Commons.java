package main;

import java.security.MessageDigest;

public class Commons {
	public static MessageDigest sha256() {
		try {
			return MessageDigest.getInstance("SHA-256");
		} catch (Exception e) {
			return null;
		}
	}
}
