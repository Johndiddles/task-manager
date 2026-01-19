package com.johndiddles.todov2.service;

import com.johndiddles.todov2.dto.CreateUserRequestDto;
import com.johndiddles.todov2.dto.UserResponseDto;
import com.johndiddles.todov2.exception.EmailAlreadyExistsException;
import com.johndiddles.todov2.mapper.UserMapper;
import com.johndiddles.todov2.model.User;
import com.johndiddles.todov2.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    public UserRepository userRepository;
    public PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
        if(userRepository.existsByEmail(createUserRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists!");
        }
        String hashedPassword = passwordEncoder.encode(createUserRequestDto.getPassword());
        createUserRequestDto.setPassword(hashedPassword);
        User user = userRepository.save(UserMapper.toUser(createUserRequestDto));
        System.out.println(user.getId() + " " + user.getEmail() + " " + user.getUsername());
        return UserMapper.toUserResponseDto(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
