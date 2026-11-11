package org.gamein.marketservergamein2022.infrastructure.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtUtils {
    private static final String SECRET_STRING = "gamein2026-very-very-ultra-top-secret-signing-key-for-jwt-tokens-must-be-at-least-64-bytes-long-secure!";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

    public static Claims getAllClaimsFromToken(String token) throws JwtException {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static Long getUserIdFromToken(String token) throws JwtException {
        String subject = getAllClaimsFromToken(token).getSubject();
        return Long.parseLong(subject);
    }

    public static Long getTeamIdFromToken(String token) throws JwtException {
        Claims claims = getAllClaimsFromToken(token);
        Object teamIdObj = claims.get("teamId");
        if (teamIdObj instanceof Number) {
            return ((Number) teamIdObj).longValue();
        }
        return null;
    }
}
