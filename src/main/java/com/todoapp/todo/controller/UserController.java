package com.todoapp.todo.controller;

import com.todoapp.todo.model.User;
import com.todoapp.todo.service.abstracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private IUserService userService;
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }
    @PostMapping("/login")
    public User login(@RequestBody User data){
        return userService.login(data.getUsername(),data.getPassword());
    }
    @GetMapping("/list")
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

}
