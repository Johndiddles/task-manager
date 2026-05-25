package com.johndiddles.todov2.controller;

import com.johndiddles.todov2.dto.CreateTaskRequestDto;
import com.johndiddles.todov2.dto.TaskResponseDto;
import com.johndiddles.todov2.dto.validators.CreateTaskValidationGroup;
import com.johndiddles.todov2.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @PostMapping("/")
    @Operation(summary = "Create Task")
    public ResponseEntity<TaskResponseDto> createTask(
            @Validated({Default.class, CreateTaskValidationGroup.class})
            @RequestBody CreateTaskRequestDto createTaskRequestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        TaskResponseDto task = taskService.createTask(createTaskRequestDto, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping("/")
    @Operation(summary = "Get all tasks")
    public ResponseEntity<List<TaskResponseDto>> getTasks(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<TaskResponseDto> tasks = taskService.getAllTasks(userDetails);
        return ResponseEntity.ok(tasks);
    }
}
