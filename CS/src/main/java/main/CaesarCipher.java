package main;

public class CaesarCipher {
	public String encrypt(String text, int key) {
		int shift = Math.floorMod(key, 26);
		var result = new StringBuilder();

		for (char ch: text.toCharArray()) {
			if (ch >= 'A' && ch <= 'Z') {
				char c = (char) ((ch - 'A' + shift) % 26 + 'A');
				result.append(c);
			} else if (ch >= 'a' && ch <= 'z') {
				char c = (char) ((ch - 'a' + shift) % 26 + 'a');
				result.append(c);
			} else {
				result.append(ch);
			}
		}
		return result.toString();
	}

	public String decrypt(String text, int key) {
		return encrypt(text, -key);
	}
}
