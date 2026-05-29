package com.johndiddles.todov2.service;

import com.johndiddles.todov2.dto.CreateTaskRequestDto;
import com.johndiddles.todov2.dto.TaskResponseDto;
import com.johndiddles.todov2.dto.UpdateTaskRequestDto;
import com.johndiddles.todov2.exception.ResourceNotFoundException;
import com.johndiddles.todov2.mapper.TaskMapper;
import com.johndiddles.todov2.model.Task;
import com.johndiddles.todov2.model.User;
import com.johndiddles.todov2.repository.TaskRepository;
import com.johndiddles.todov2.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.UUID;

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
        Task task;
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        if(createTaskRequestDto.getAssignee_id() != null) {
            User assignee = userRepository.findById(createTaskRequestDto.getAssignee_id()).orElseThrow();
            task = taskRepository.save(TaskMapper.toTask(createTaskRequestDto, user, assignee));
        } else {
            task = taskRepository.save(TaskMapper.toTask(createTaskRequestDto, user, user));
        }

        return TaskMapper.toTaskResponseDto(task);
    }

    public List<TaskResponseDto> getAllTasks(UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<Task> tasks = taskRepository.findByCreatedByOrAssignee(user, user);
        return tasks.stream().map(TaskMapper::toTaskResponseDto).toList();
    }

    public TaskResponseDto getTask(UserDetails userDetails, UUID taskId) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Task task = taskRepository.findByIdAndCreatedByOrIdAndAssignee(taskId, user, taskId, user);

        return TaskMapper.toTaskResponseDto(task);
    }

    @Transactional
    public TaskResponseDto updateTask(
            UUID taskId,
            UpdateTaskRequestDto updateTaskRequestDto
    ){
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("No task found with ID: " + taskId));

        task.setTitle(updateTaskRequestDto.getTitle());
        task.setDescription(updateTaskRequestDto.getDescription());
        task.setPriority(updateTaskRequestDto.getPriority());
        task.setStatus(updateTaskRequestDto.getStatus());
        task.setDueDate(updateTaskRequestDto.getDueDate());
        task.setTags(updateTaskRequestDto.getTags());

        UUID assigneeId = task.getAssignee().getId();
        UUID newAssigneeId = updateTaskRequestDto.getAssignee_id();

        if (assigneeId != null && newAssigneeId != null && !newAssigneeId.equals(assigneeId)) {
            User newAssignee = userRepository.findById(updateTaskRequestDto.getAssignee_id()).orElseThrow();
            task.setAssignee(newAssignee);
        }

        Task savedTask = taskRepository.save(task);

        return TaskMapper.toTaskResponseDto(savedTask);
    }

}