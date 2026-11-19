package maven;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PGen {
	public static void main(String[] args) {
		String hash = BCrypt.hashpw("P@55word", BCrypt.gensalt(10));
		System.out.println(BCrypt.checkpw("P@55word", hash));
	}
}
