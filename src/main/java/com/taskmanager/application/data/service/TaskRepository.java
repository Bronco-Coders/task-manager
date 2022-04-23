package com.taskmanager.application.data.service;

import com.taskmanager.application.data.entity.Task;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, UUID> {

}