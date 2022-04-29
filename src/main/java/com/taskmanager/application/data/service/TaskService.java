package com.taskmanager.application.data.service;

import com.taskmanager.application.data.entity.Task;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository repository;

    @Autowired
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Optional<Task> get(UUID id) {
        return repository.findById(id);
    }

    public Task update(Task entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
    public void deleteTask(Task entity){
        repository.delete(entity);
    }

    public Page<Task> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
