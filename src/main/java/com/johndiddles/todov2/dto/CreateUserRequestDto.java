package com.johndiddles.todov2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserRequestDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank(message = "Username is required")
    @Size(min = 2, message = "Username cannot be less than 2 characters")
    @Size(max = 30, message = "Username cannot be more than 30 characters")
    private String username;

    @NotBlank(message = "Message is required")
    @Size(min = 8, message = "Password cannot be less 8 characters")
    private String password;
}
