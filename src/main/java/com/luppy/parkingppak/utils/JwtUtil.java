package com.luppy.parkingppak.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    //30분.
    private Long tokenValidTime = 30 * 60 * 1000L;

    public String createToken(Long id, String name) {

        String secret = "12345678901234567890123456789012";
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        String token = Jwts.builder()
                .claim("accountId", id)
                .claim("name", name)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenValidTime))
                .signWith(key, SignatureAlgorithm.ES256)
                .compact();

        return token;
    }
}
