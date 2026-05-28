package com.johndiddles.todov2.repository;

import com.johndiddles.todov2.model.Task;
import com.johndiddles.todov2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCreatedByOrAssignee(User createdBy,  User assignee);
    Task findByIdAndCreatedByOrIdAndAssignee(UUID id, User createdBy, UUID id2, User assignee);
}
