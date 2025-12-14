package com.example.demo.service;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

@Service
public class JwtService {
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.access-exp-ms}")
    private long jwtAccessExpirationMs;

    @Value("${app.jwt.refresh-exp-ms}")
    private long jwtRefreshExpirationMs;

    // Sinh token access (ngắn hạn)
    public String generateAccessToken(String email) {
        return generateToken(email, jwtAccessExpirationMs);
    }

    // Sinh token refresh (dài hạn)
    public String generateRefreshToken(String email) {
        return generateToken(email, jwtRefreshExpirationMs);
    }

    // Hàm chung để tạo token
    private String generateToken(String email, long expirationMillis) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Xác minh token
    public String validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return claims.getBody().getSubject(); // email
        } catch (JwtException e) {
            return null;
        }
    }
}
