package com.johndiddles.todov2.controller;

import com.johndiddles.todov2.dto.LoginRequestDto;
import com.johndiddles.todov2.dto.LoginResponseDto;
import com.johndiddles.todov2.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto loginDto
    ){
        LoginResponseDto loginResponse = authService.authenticate(loginDto);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

}
