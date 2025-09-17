package com.lms.cases.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final Key secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        // Convert secret string to HMAC key
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Validate JWT token signature and structure.
     *
     * @param token JWT string
     * @return true if valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Check if the JWT token is expired.
     *
     * @param token JWT string
     * @return true if expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    /**
     * Extract username from JWT.
     *
     * @param token JWT string
     * @return username (subject)
     */
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Extract authorities (roles) from JWT.
     *
     * @param token JWT string
     * @return list of authorities
     */
    public List<String> extractAuthorities(String token) {
        return getClaims(token).get("authorities", List.class);
    }

    /**
     * Extract userId from JWT. Requires user-service to include "userId" claim in JWT.
     *
     * @param token JWT string
     * @return userId as Long
     */
    public Long extractUserId(String token) {
        Object userIdClaim = getClaims(token).get("userId");
        if (userIdClaim instanceof Integer) {
            return ((Integer) userIdClaim).longValue();
        } else if (userIdClaim instanceof Long) {
            return (Long) userIdClaim;
        } else if (userIdClaim instanceof String) {
            return Long.parseLong((String) userIdClaim);
        } else {
            throw new IllegalArgumentException("Invalid userId claim in token");
        }
    }

    /**
     * Get claims from JWT token.
     *
     * @param token JWT string
     * @return Claims object
     */
    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }
}
