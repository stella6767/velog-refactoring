package com.kang.kanglog.service;


import com.kang.kanglog.domain.User;
import com.kang.kanglog.utils.common.JwtProperties;
import com.kang.kanglog.web.dto.user.UserRespDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import static com.kang.kanglog.web.dto.user.UserRespDto.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtService {

    private final RedisService redisService;


    public void saveTokenInRedis(String key, String value){  //refreshToken을 reids에 저장
        //UUID uuid = UUID.randomUUID(); //고유키값 만들기
        redisService.setDataExpire(key, value, JwtProperties.REFRESH_TOKEN_VALIDATION_SECOND);
    }


    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(JwtProperties.SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserId(String token) {


        return extractAllClaims(token).get("userId", Long.class);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }



    public String generateAccessToken(Long userId) {

        log.info("검증에 활용할 userId: " + userId);
        return doGenerateToken(userId, JwtProperties.ACCESS_TOKEN_VALIDATION_SECOND);
    }

    public String generateRefreshToken(Long userId) {
        return doGenerateToken(userId, JwtProperties.REFRESH_TOKEN_VALIDATION_SECOND);
    }

    public String doGenerateToken(Long userId, long expireTime) {

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


    public Boolean validateToken(String token, User user) {
        final Long userId = getUserId(token);

        return (userId == user.getId() && !isTokenExpired(token));
    }



}
