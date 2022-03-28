package com.kang.kanglog.utils.util_function;

import com.kang.kanglog.domain.User;
import com.kang.kanglog.utils.common.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
public class JWTUtils {



    private static Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(JwtProperties.SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static Long getUserId(String token) {


        return extractAllClaims(token).get("userId", Long.class);
    }

    public static Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }



    public static String generateAccessToken(Long userId) {

        log.info("검증에 활용할 userId: " + userId);
        return doGenerateToken(userId, JwtProperties.ACCESS_TOKEN_VALIDATION_SECOND);
    }

    public static String generateRefreshToken(Long userId) {
        return doGenerateToken(userId, JwtProperties.REFRESH_TOKEN_VALIDATION_SECOND);
    }

    public static String doGenerateToken(Long userId, long expireTime) {

        log.info("토큰을 만드는데 인증이 에러가 나네..");
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(JwtProperties.SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }


    public static Boolean validateToken(String token, User user) {
        final Long userId = getUserId(token);

        return (userId == user.getId() && !isTokenExpired(token));
    }



}
