package com.johndiddles.todov2.controller;

import com.johndiddles.todov2.dto.CreateUserRequestDto;
import com.johndiddles.todov2.dto.LoginRequestDto;
import com.johndiddles.todov2.dto.LoginResponseDto;
import com.johndiddles.todov2.dto.UserResponseDto;
import com.johndiddles.todov2.dto.validators.CreateUserValidationGroup;
import com.johndiddles.todov2.service.AuthService;
import com.johndiddles.todov2.service.UserService;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    public AuthController(AuthService authService,  UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto loginDto
    ){
        LoginResponseDto loginResponse = authService.authenticate(loginDto);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDto> register(
            @Validated({Default.class, CreateUserValidationGroup.class})
            @RequestBody CreateUserRequestDto userRequestDto
    ) {
        String password = userRequestDto.getPassword();

        UserResponseDto user = userService.createUser(userRequestDto);

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail(user.getEmail());
        loginRequestDto.setPassword(password);
        LoginResponseDto loginResponseDto = authService.authenticate(loginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDto);
    }

    @GetMapping("/verify")
    public ResponseEntity<Void> verifyToken(
            @RequestHeader("Authorization") String token
    ) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return authService.validateToken(token.substring(7))
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
