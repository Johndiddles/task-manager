package com.johndiddles.todov2.service;

import com.johndiddles.todov2.dto.LoginRequestDto;
import com.johndiddles.todov2.dto.LoginResponseDto;
import com.johndiddles.todov2.exception.InvalidCredentialsException;
import com.johndiddles.todov2.mapper.AuthMapper;
import com.johndiddles.todov2.model.User;
import com.johndiddles.todov2.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    public LoginResponseDto authenticate(LoginRequestDto loginRequestDto){
        Optional<User> usersByEmail = userService.findByEmail(loginRequestDto.getEmail());
        if(usersByEmail.isEmpty()){
            throw new InvalidCredentialsException("Invalid login credentials");
        }
        Optional<String> token =  usersByEmail
                .filter(user -> passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()))
                .map(user -> jwtUtil.generateToken(user.getEmail(), user.getId().toString()));
        if(token.isEmpty()){
            throw new InvalidCredentialsException("Invalid login credentials");
        }
        return AuthMapper.toLoginResponseDto(token.get());
    }
}
