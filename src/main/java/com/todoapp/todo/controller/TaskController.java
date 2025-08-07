package com.todoapp.todo.controller;

import com.todoapp.todo.dto.TaskResponseDto;
import com.todoapp.todo.service.TaskService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:3000") //vue.js için
public class TaskController {
    @Autowired
    private TaskService TaskService;

    //jwtden uid çıkarıcak buraya
    private UUID getUserIdFromJwt(Jwt jwt) {
        return UUID.fromString(jwt.getSubject());
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks(@AuthenticationPrincipal Jwt principal
    ) {
       UUID userId = getUserIdFromJwt(principal);
       List<TaskResponseDto> tasks = taskService.getTasksForUser(userId);
       return ResponseEntity.ok(tasks);

    }




}
