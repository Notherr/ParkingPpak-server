package com.luppy.parkingppak.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {

    public String createToken(Long id, String name) {

        String secret = "12345678901234567890123456789012";
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        String token = Jwts.builder()
                .claim("accountId", id)
                .claim("name", name)
                .signWith(key, SignatureAlgorithm.ES256)
                .compact();

        return token;
    }
}
