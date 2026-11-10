package maven;

import java.util.Date;
import java.security.Key;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {
	private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public String generateToken(String username) {
		return Jwts.builder()
			.setSubject(username)
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h
			.signWith(key)
			.compact();
	}

	public String verifyToken(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		} catch (JwtException e) {
			return null;
		}
	}
}
