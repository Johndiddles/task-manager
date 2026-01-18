package com.johndiddles.todov2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserResponseDto {
    private String id;
    private String email;
    private String username;
}
