package com.laioffer.delivery.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET = System.getProperty("JWT_SECRET");
    private static final long EXPIRATION_TIME = Long.parseLong(System.getProperty("JWT_EXPIRATION", "3600000"));
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * generate a JWT string based on the user's email
     *
     * @param email which is a unique user id
     * @return genereted JWT string
     */
    public static String generateToken(String email) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRATION_TIME))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * @param token
     * @return the subject if legal
     */
    public static String parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
