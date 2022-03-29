package com.luppy.parkingppak.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    //30분.
    private final Long tokenValidTime = 30 * 60 * 1000L;
    private final String secret = "12345678901234567890123456789012";


    public String createToken(Long id, String name) {

        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        Claims claims = Jwts.claims().setSubject(name);
        claims.put("accountId", id);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenValidTime))
                .signWith(key, SignatureAlgorithm.ES256)
                .compact();

        return token;
    }

    // 토큰에서 회원 정보 추출
    public String getAccountName(String jwt) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody().getSubject();
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
