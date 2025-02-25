package com.app.linkedin.user_service.services;

import com.app.linkedin.user_service.dtos.LoginRequestDto;
import com.app.linkedin.user_service.dtos.SignupRequestDto;
import com.app.linkedin.user_service.dtos.UserDto;

public interface AuthService {
    UserDto signup(SignupRequestDto signupRequestDto);

    String login(LoginRequestDto loginRequestDto);
}
