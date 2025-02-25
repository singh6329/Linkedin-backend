package com.app.linkedin.api_gateway.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    SecretKey getSecretKey()
    {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String getUserIdFromToken(String jwtToken)
    {
        Claims claims = Jwts
                        .parser()
                        .verifyWith(getSecretKey())
                        .build()
                        .parseSignedClaims(jwtToken)
                        .getPayload();

       return claims.getSubject();
    }
}
