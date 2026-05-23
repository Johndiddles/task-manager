package com.johndiddles.todov2.dto;

import com.johndiddles.todov2.model.TaskPriority;
import com.johndiddles.todov2.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class CreateTaskRequestDto {
    @NotBlank
    @Size(min = 4, message = "Title cannot be less than 4 characters")
    @Size(max = 120, message = "Title cannot be more than 120 characters")
    private String title;

    @NotBlank
    @Size(min = 10, message = "Description cannot be less than 10 characters")
    @Size(max = 500, message = "Description cannot be more than 500 characters")
    private String description;

    @NotNull
    private TaskPriority priority;
    @NotNull
    private Date dueDate;
    @NotNull(message = "Status has to be one of [TODO, IN_PROGRESS, COMPLETED, CANCELED, BLOCKED]")
    private TaskStatus status;

//    @NotNull
    private UUID assignee_id;
    private List<String> tags;
    private Set<UUID> task_shares;

}
