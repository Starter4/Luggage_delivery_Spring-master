package com.example.mainservice.security;

import com.example.mainservice.entity.JwtUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.expiration.time}")
    private String expirationTime;

    public String createToken(Authentication authentication){
        JwtUser user = (JwtUser) authentication.getPrincipal();
        Date now  = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + Long.parseLong(expirationTime));
        return null;
    }
}
