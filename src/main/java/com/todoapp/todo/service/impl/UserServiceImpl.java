package com.todoapp.todo.service.impl;

import com.todoapp.todo.model.User;
import com.todoapp.todo.repository.UserRepository;
import com.todoapp.todo.service.abstracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User register(User user) {
        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .orElse(null);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

}
