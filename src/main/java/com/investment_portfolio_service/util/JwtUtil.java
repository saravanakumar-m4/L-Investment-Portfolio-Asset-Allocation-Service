package com.investment_portfolio_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

	static long x = 60 * 60*1000; //1Hour
	
	private static final long EXPIRATION_TIME = x;
	
	// Token validity: 24 hours
	//private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;
	
	// In real projects, move this to application.yml
	private static final String SECRET_KEY = "mySuperSecureJwtSecretKeyForInvestmentProject12345";

	private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

	/**
	 * Generate JWT token using username (email)
	 */
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	/**
	 * Validate token expiration and signature
	 */
	public boolean validateToken(String token) {
		try {
			extractAllClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Extract username (subject) from token
	 */
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

}
