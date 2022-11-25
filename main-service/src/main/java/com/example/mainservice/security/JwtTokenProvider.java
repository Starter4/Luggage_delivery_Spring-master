package com.example.mainservice.security;

import com.example.mainservice.entity.JwtUser;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    @Value("${jwt.expiration.time}")
    private String expirationTime;

    @Value("${jwt.secret}")
    private String secretKey;

    public String createToken(Authentication authentication){
        JwtUser user = (JwtUser) authentication.getPrincipal();
        Date now  = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + Long.parseLong(expirationTime));

        String userID = String.valueOf(user.getId());
        Map<String,Object> claimsMap = new HashMap<>();
            claimsMap.put("userID", userID);
        claimsMap.put("login", user.getLogin());
        claimsMap.put("username", user.getUsername());
        claimsMap.put("firstName", user.getFirstname());
        claimsMap.put("lastName", user.getLastname());

        return Jwts.builder()
                .setSubject(userID)
                .addClaims(claimsMap)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.ES512,secretKey)
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        }catch (SignatureException | MalformedJwtException | ExpiredJwtException
                | UnsupportedJwtException | IllegalArgumentException e){
            return false;
        }
    }

    public Long getUserIDFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(String.valueOf(claims.get("userID")));
    }
}
