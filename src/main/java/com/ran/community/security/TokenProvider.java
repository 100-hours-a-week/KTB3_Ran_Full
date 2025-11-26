package com.ran.community.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;

    private final long tokenValidity = 1000 * 60 * 60; // 1시간

    // 🔹 JWT 생성
    public String createToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + tokenValidity);

        return Jwts.builder()
                .setSubject(username)        // username 저장
                .setIssuedAt(now)            // 발급 시간
                .setExpiration(expiration)   // 만료 시간
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔹 JWT 서명 키 생성
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // 🔹 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 🔹 토큰에서 Authentication 객체 반환
    public Authentication getAuthentication(String token) {
        String username = getUsername(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    // 🔹 토큰에서 username 반환
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
