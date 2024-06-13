package com.maple.maple_boss_now.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private final RedisTemplate<String, String> redisTemplate;

    public JwtProvider(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        String token = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        // Redis에 저장 (만료 시간을 함께 저장)
        redisTemplate.opsForValue().set(token, userId, expiration, TimeUnit.MILLISECONDS);

        return token;
    }

    public String getUserIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            // Redis에서 토큰 존재 여부 확인
            String userId = redisTemplate.opsForValue().get(token);
            if (userId == null) {
                return false; // Redis에 없으면 유효하지 않음
            }
            return true;
        } catch (Exception e) {
            log.error("Invalid JWT token", e);
            return false;
        }
    }

    public void deleteToken(String token) {
        // Redis에서 토큰 삭제
        redisTemplate.delete(token);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    //storeToken
    public void storeToken(String userId, String token) {
        // Redis에 저장 (만료 시간을 함께 저장)
        redisTemplate.opsForValue().set(token, userId, expiration, TimeUnit.MILLISECONDS);
    }
}
