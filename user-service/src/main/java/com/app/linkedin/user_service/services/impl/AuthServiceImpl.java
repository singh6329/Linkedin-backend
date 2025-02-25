package com.app.linkedin.user_service.services.impl;

import com.app.linkedin.user_service.dtos.LoginRequestDto;
import com.app.linkedin.user_service.dtos.SignupRequestDto;
import com.app.linkedin.user_service.dtos.UserDto;
import com.app.linkedin.user_service.entities.Users;
import com.app.linkedin.user_service.events.UserCreatedEvent;
import com.app.linkedin.user_service.exceptions.BadRequestException;
import com.app.linkedin.user_service.exceptions.ResourceNotFoundException;
import com.app.linkedin.user_service.repositories.UserRepository;
import com.app.linkedin.user_service.services.AuthService;
import com.app.linkedin.user_service.services.JwtService;
import com.app.linkedin.user_service.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final KafkaTemplate<Long, UserCreatedEvent> kafkaTemplate;

    @Override
    public UserDto signup(SignupRequestDto signupRequestDto) {
        signupRequestDto.setEmail(signupRequestDto.getEmail().toLowerCase());
        boolean userExists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if(userExists)
            throw new BadRequestException("User with email "+signupRequestDto.getEmail()+" already exists!");

        Users user =  modelMapper.map(signupRequestDto, Users.class);
        user.setPassword(PasswordUtils.hashPassword(user.getPassword()));
        Users savedUser = userRepository.save(user);
        UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName()).build();
        kafkaTemplate.send("user-created-topic",userCreatedEvent);
        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public String login(LoginRequestDto loginRequestDto) {
        Users user = userRepository
                                .findByEmail(loginRequestDto.getEmail().toLowerCase())
                                .orElseThrow(()-> new ResourceNotFoundException("Email is not registered with us!"));
        boolean isPasswordMatches = PasswordUtils.verifyPassword(loginRequestDto.getPassword(),user.getPassword());
        if (!isPasswordMatches)
            throw new BadRequestException("Email or password doesn't matches!");
        return jwtService.generateAccessToken(user);
    }
}
