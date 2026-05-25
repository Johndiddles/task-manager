package com.johndiddles.todov2.controller;

import com.johndiddles.todov2.dto.CreateUserRequestDto;
import com.johndiddles.todov2.dto.UserResponseDto;
import com.johndiddles.todov2.dto.validators.CreateUserValidationGroup;
import com.johndiddles.todov2.mapper.UserMapper;
import com.johndiddles.todov2.model.User;
import com.johndiddles.todov2.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Users", description = "APIs for managing user profile")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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

    @GetMapping("/profile")
    @Operation(summary = "Get logged in user details")
    public ResponseEntity<UserResponseDto> getLoggedInUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Optional<User> user = userService.findByUsername(userDetails.getUsername());

            return user.map(value -> ResponseEntity
                    .status(HttpStatus.OK)
                    .body(UserMapper.toUserResponseDto(value)
                    )
            ).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/profile/{email}")
    @Operation(summary = "Get user details")
    public ResponseEntity<UserResponseDto> getUser(
            @PathVariable String email
    ) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            UserResponseDto userResponseDto = UserMapper.toUserResponseDto(user.get());
            return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
        }
        return ResponseEntity.notFound().build();
    }
}
