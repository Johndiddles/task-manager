package com.johndiddles.todov2.service;

import com.johndiddles.todov2.dto.CreateTaskRequestDto;
import com.johndiddles.todov2.dto.TaskResponseDto;
import com.johndiddles.todov2.mapper.TaskMapper;
import com.johndiddles.todov2.model.Task;
import com.johndiddles.todov2.model.User;
import com.johndiddles.todov2.repository.TaskRepository;
import com.johndiddles.todov2.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskResponseDto createTask(
            CreateTaskRequestDto createTaskRequestDto,
            UserDetails userDetails
    ){
        System.out.println("Got into the create task service");
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        Task task = taskRepository.save(TaskMapper.toTask(createTaskRequestDto, user));
        return TaskMapper.toTaskResponseDto(task);
    }
}
