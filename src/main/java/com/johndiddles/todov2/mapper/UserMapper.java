package com.johndiddles.todov2.mapper;

import com.johndiddles.todov2.dto.CreateUserRequestDto;
import com.johndiddles.todov2.dto.UserResponseDto;
import com.johndiddles.todov2.model.User;

public class UserMapper {

    public static User toUser(CreateUserRequestDto userRequestDto) {
        User user = new User();

        user.setUsername(userRequestDto.getUsername());
        user.setPassword(userRequestDto.getPassword());
        user.setEmail(userRequestDto.getEmail());
        return user;
    }

    public static UserResponseDto toUserResponseDto(User user) {
        UserResponseDto responseDto = new UserResponseDto();

        responseDto.setId(user.getId().toString());
        responseDto.setUsername(user.getUsername());
        responseDto.setEmail(user.getEmail());
        return responseDto;
    }
}
