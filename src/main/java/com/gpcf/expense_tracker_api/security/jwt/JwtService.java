package com.gpcf.expense_tracker_api.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

//    Keys.hmacShaKeyFor() converts raw bytes into a valid cryptographic HMAC SecretKey object.
//    "Keys.hmacShaKeyFor() is used to create a secure HMAC SecretKey object from the secret string bytes, " +
//        "which is required by JWT signing algorithms like HS256."


//    user identity carry karta hai
//    server verify karta hai
//    har request me login dubara nahi karna padta

    public SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                jwtSecret.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateJwt(UserDetails user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getUsername());
        List<String> roleList = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        claims.put("roles", roleList);
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return (List<String>) claims.get("roles");

    }


    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean isTokenValid(UserDetails us, String token) {
        return extractUsername(token).equals(us.getUsername()) && !isTokenExpired(token);
    }
}
