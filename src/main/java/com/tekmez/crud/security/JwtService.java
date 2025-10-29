package com.tekmez.crud.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secretKey;

    @Value("${security.jwt.expiration-ms}")
    private long jwtExpirationMs;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String username, Map<String,Object>extraClaims){
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .claims(extraClaims) // extra info (fr.exp. Role)
                .subject(username) // token owner
                .issuedAt(new Date(now)) // create date
                .expiration(new Date(now + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, String username) {
        final String extracted = extractUsername(token);
        return (extracted.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver){
        final Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(claims);
    }
}


