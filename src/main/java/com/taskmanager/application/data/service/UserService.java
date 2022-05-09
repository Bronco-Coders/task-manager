package com.taskmanager.application.data.service;

import com.taskmanager.application.data.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> get(UUID id) {
        return repository.findById(id);
    }

    public User update(User entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<User> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public User findByUsername(String username) {
    	User user = new User();
    	user.setUsername(username);
    	Example<User> example = Example.of(user);
		 List<User> userList = repository.findAll(example);
		 
		 if(userList != null && userList.size() >0) {
			 return userList.get(0);
		 }
		 return null;
    }

    public int count() {
        return (int) repository.count();
    }

}
