package com.taskmanager.application.data.service;

import com.taskmanager.application.data.entity.User;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends MongoRepository<User, UUID> {

    User findByUsername(String username);
}