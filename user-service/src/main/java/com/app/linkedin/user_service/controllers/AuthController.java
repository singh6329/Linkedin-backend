package com.app.linkedin.user_service.controllers;

import com.app.linkedin.user_service.dtos.LoginRequestDto;
import com.app.linkedin.user_service.dtos.SignupRequestDto;
import com.app.linkedin.user_service.dtos.UserDto;
import com.app.linkedin.user_service.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto signupRequestDto)
    {
        return new ResponseEntity<>(authService.signup(signupRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto)
    {
        return new ResponseEntity<>(authService.login(loginRequestDto),HttpStatus.OK);
    }

}
