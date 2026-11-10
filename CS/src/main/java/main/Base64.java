package main;

public class Base64 {
	public static final int[] BASE64_INDEX = new int[128];
	public static final char[] BASE64_CHARS = 
			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

	public Base64() {
		for (int i = 0; i < BASE64_CHARS.length; i++)
			BASE64_INDEX[BASE64_CHARS[i]] = i;
	}

	public String encode(byte[] data) {
		var result = new StringBuilder();
		int i = 0;
		while (i < data.length) {
			int b1 = data[i++] & 0xff;
			int b2 = (i < data.length) ? data[i++] & 0xff : 0;
			int b3 = (i < data.length) ? data[i++] & 0xff : 0;
			
			int combined = (b1 << 16) | (b2 << 8) | b3;

			result.append(BASE64_CHARS[(combined >> 18) & 0x3f]);
			result.append(BASE64_CHARS[(combined >> 12) & 0x3f]);
			result.append(i - 1 < data.length ? BASE64_CHARS[(combined >> 6) & 0x3f] : '=');
			result.append(i < data.length ? BASE64_CHARS[combined & 0x3f] : '=');
		}
		return result.toString();
	}

	public byte[] decode(String base64) {
		base64 = base64.replace("=", "");
		byte[] buffer = new byte[base64.length() * 3 / 4];

		int index = 0;
		int i = 0;

		while (i < base64.length()) {
			int c1 = BASE64_INDEX[base64.charAt(i++)];
			int c2 = BASE64_INDEX[base64.charAt(i++)];
			int c3 = i < base64.length() ? BASE64_INDEX[base64.charAt(i++)] : 0;
			int c4 = i < base64.length() ? BASE64_INDEX[base64.charAt(i++)] : 0;

			int combined = (c1 << 18) | (c2 << 12) | (c3 << 6) | c4;

			buffer[index++] = (byte) ((combined >> 16) & 0xff);
			if (index < buffer.length)
				buffer[index++] = (byte) ((combined >> 8) & 0xff);
			if (index < buffer.length)
				buffer[index++] = (byte) (combined & 0xff);
		}
		return buffer;
	}
}
