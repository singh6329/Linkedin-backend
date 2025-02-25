package com.app.linkedin.user_service.services;

import com.app.linkedin.user_service.entities.Users;

public interface JwtService {
    String generateAccessToken(Users user);
    String generateRefreshToken(Users user);
    Long verifyToken(String jwtToken);
}
