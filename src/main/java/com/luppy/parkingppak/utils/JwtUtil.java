package com.luppy.parkingppak.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    //하루
    private final Long tokenValidTime = 1000L * 60 * 60 * 24;
    private final String secret = "12345678901234567890123456789012";


    public String createToken(Long id, String name) {

        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        Claims claims = Jwts.claims().setSubject(String.valueOf(id));
        claims.put("accountName", name);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenValidTime))
                .signWith(key)
                .compact();

        return token;
    }


    public String getAccountId(String jwt) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody().getSubject();
    }


    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwt) {
        try {
            // Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt);
            // return !claims.getBody().getExpiration().before(new Date());
            Key key = Keys.hmacShaKeyFor(secret.getBytes());

            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            return true;
        } catch (ExpiredJwtException e){
            log.info("만료된 jwt 토큰입니다.");
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
