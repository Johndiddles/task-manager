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

import java.util.List;

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
        Task task = new Task();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        if(createTaskRequestDto.getAssignee_id() != null) {
            User assignee = userRepository.findById(createTaskRequestDto.getAssignee_id()).orElseThrow();
            task = taskRepository.save(TaskMapper.toTask(createTaskRequestDto, user, assignee));
        } else {
            task = taskRepository.save(TaskMapper.toTask(createTaskRequestDto, user, user));
        }

        return TaskMapper.toTaskResponseDto(task);
    }

    public List<TaskResponseDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(TaskMapper::toTaskResponseDto).toList();
    }
}
