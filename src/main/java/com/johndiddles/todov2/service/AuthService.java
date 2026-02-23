package com.johndiddles.todov2.service;

import com.johndiddles.todov2.dto.LoginRequestDto;
import com.johndiddles.todov2.dto.LoginResponseDto;
import com.johndiddles.todov2.exception.InvalidCredentialsException;
import com.johndiddles.todov2.mapper.AuthMapper;
import com.johndiddles.todov2.model.User;
import com.johndiddles.todov2.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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

    public Boolean validateToken(String token) {
        try {
            jwtUtil.validateToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
