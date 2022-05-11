package com.taskmanager.application.data.service;

import com.taskmanager.application.data.entity.Task;

import java.util.List;
import java.util.Optional;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface TaskRepository extends MongoRepository<Task, String> {

    @Override
    Optional<Task> findById(String id);

    List<Task> findByUserId(String userId);

}