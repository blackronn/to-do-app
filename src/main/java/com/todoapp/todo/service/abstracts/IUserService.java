package com.todoapp.todo.service.abstracts;

import com.todoapp.todo.model.User;

import java.util.List;

public interface IUserService {
    public User register(User user);
    public User login(String username, String password);

    public List<User> getAllUser();

}
