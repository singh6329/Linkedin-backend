package com.app.linkedin.user_service.services.impl;

import com.app.linkedin.user_service.entities.Users;
import com.app.linkedin.user_service.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService  {

    String jwtSecurity = "erhfhfdhdhjadjkajkdsjvdbcbjvsjakvhddvsh";

    SecretKey getSecurityKey()
    {
        return Keys.hmacShaKeyFor(jwtSecurity.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateAccessToken(Users user) {
               return Jwts
                .builder()
                .signWith(getSecurityKey())
                .subject(String.valueOf(user.getId()))
                .claim("Email",user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+60*1000*3))
                .compact();
    }

    @Override
    public String generateRefreshToken(Users user) {
        return Jwts
                .builder()
                .signWith(getSecurityKey())
                .subject(String.valueOf(user.getId()))
                .claim("Email",user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+ 60L *1000*60*24*30*6))
                .compact();
    }

    @Override
    public Long verifyToken(String jwtToken) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecurityKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
        return Long.valueOf(claims.getSubject());

    }
}
