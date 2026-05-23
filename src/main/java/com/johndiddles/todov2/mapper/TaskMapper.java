package com.johndiddles.todov2.mapper;

import com.johndiddles.todov2.dto.CreateTaskRequestDto;
import com.johndiddles.todov2.dto.TaskResponseDto;
import com.johndiddles.todov2.model.Task;
import com.johndiddles.todov2.model.User;

public class TaskMapper {
    public static Task toTask(CreateTaskRequestDto requestDto, User user) {
        Task task = new Task();

        task.setTitle(requestDto.getTitle());
        task.setDescription(requestDto.getDescription());
        task.setDueDate(requestDto.getDueDate());
        task.setPriority(requestDto.getPriority());
        task.setCreatedBy(user);
        task.setAssignee(user);
        task.setStatus(requestDto.getStatus());
        task.setTags(requestDto.getTags());
//        task.setSharedWith(requestDto.getTask_shares());

        return task;
    }

    public static TaskResponseDto toTaskResponseDto(Task task) {
        TaskResponseDto responseDto = new TaskResponseDto();
        responseDto.setId(task.getId().toString());
        responseDto.setTitle(task.getTitle());
        responseDto.setDescription(task.getDescription());
        responseDto.setStatus(task.getStatus().toString());
        responseDto.setDueDate(task.getDueDate().toString());
        responseDto.setPriority(task.getPriority().toString());

        return responseDto;
    }
}
