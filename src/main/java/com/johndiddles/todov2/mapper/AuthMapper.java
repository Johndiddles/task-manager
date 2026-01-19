package com.johndiddles.todov2.mapper;

import com.johndiddles.todov2.dto.LoginRequestDto;
import com.johndiddles.todov2.dto.LoginResponseDto;

public class AuthMapper {
    public static LoginResponseDto toLoginResponseDto(String token){
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setToken(token);
        loginResponseDto.setStatus("success");

        return loginResponseDto;
    }
}
