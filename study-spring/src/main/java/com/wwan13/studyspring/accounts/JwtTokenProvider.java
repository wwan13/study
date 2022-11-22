package com.wwan13.studyspring.accounts;

import io.jsonwebtoken.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Date;

public class JwtTokenProvider {

    private static final String JWT_SECRET = "secret_key";

    // 토큰 유효시간
    private static final int JWT_EXPIRATION_MS = 604800000;

    // jwt 토큰 생성
    public static String generateToken(Authentication authentication) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        String token = Jwts.builder()
                .setSubject(authentication.getPrincipal().toString())
                .setIssuedAt(now) // 생성 시간 (현재)
                .setExpiration(expiryDate) // 만료시간
                .signWith(SignatureAlgorithm.ES512, JWT_SECRET) // 사용할 암호화 알고리즘, 시크릿키
                .compact();
        return token;
    }

    // jwt 토큰을 통해 유저 아이디 추출
    public static String getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        String userId = claims.getSubject();

        return userId;
    }

    // jwt 토큰 유효성 검사
    public static boolean validationToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
