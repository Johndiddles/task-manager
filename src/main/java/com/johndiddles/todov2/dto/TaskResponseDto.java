package com.johndiddles.todov2.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskResponseDto {
    private String id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String dueDate;
    private List<String> tags;
}
