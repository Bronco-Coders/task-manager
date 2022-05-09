package com.taskmanager.application.data.service;

import com.taskmanager.application.data.entity.Task;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface TaskRepository extends MongoRepository<Task, String> {

    @Override
    Optional<Task> findById(String id);

}