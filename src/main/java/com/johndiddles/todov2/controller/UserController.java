package com.johndiddles.todov2.controller;

import com.johndiddles.todov2.dto.CreateUserRequestDto;
import com.johndiddles.todov2.dto.UserResponseDto;
import com.johndiddles.todov2.dto.validators.CreateUserValidationGroup;
import com.johndiddles.todov2.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
@Tag(name = "Users", description = "APIs for managing user profile")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<String>  index() {
        return ResponseEntity.ok("Hello World");
    }

    @PostMapping("/")
    @Operation(summary = "Create User")
    public ResponseEntity<UserResponseDto> createUser(
            @Validated({Default.class, CreateUserValidationGroup.class})
            @RequestBody CreateUserRequestDto userRequestDto
    ) {
        UserResponseDto user = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
