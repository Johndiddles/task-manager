package com.johndiddles.todov2.service;

import com.johndiddles.todov2.dto.CreateUserRequestDto;
import com.johndiddles.todov2.dto.UserResponseDto;
import com.johndiddles.todov2.exception.EmailAlreadyExistsException;
import com.johndiddles.todov2.mapper.UserMapper;
import com.johndiddles.todov2.model.User;
import com.johndiddles.todov2.repository.UserRepository;
import com.johndiddles.todov2.util.Encoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
//@AllArgsConstructor
//@RequiredArgsConstructor
public class UserService {
    public UserRepository userRepository;
    public Encoder passwordEncoder;

    public UserService(UserRepository userRepository, Encoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {

        if(userRepository.existsByEmail(createUserRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists!");
        }
        String hashedPassword = passwordEncoder.passwordEncoder().encode(createUserRequestDto.getPassword());
        createUserRequestDto.setPassword(hashedPassword);
        User user = userRepository.save(UserMapper.toUser(createUserRequestDto));
        return UserMapper.toUserResponseDto(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
