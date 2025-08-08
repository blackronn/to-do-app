package com.todoapp.todo.controller;

import com.todoapp.todo.dto.TaskCreateRequestDto;
import com.todoapp.todo.dto.TaskResponseDto;
import com.todoapp.todo.service.abstracts.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;


@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private ITaskService taskService;


    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskCreateRequestDto requestDto) {
        TaskResponseDto createdTask = taskService.createTask(requestDto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDto>>  getTasksByUserId(@PathVariable Long userId) {
        List<TaskResponseDto> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> deleteTask(@PathVariable Long taskId){
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{taskId}/status")
    public ResponseEntity<TaskResponseDto> updateTaskStatus(@PathVariable Long taskId, @RequestBody Map<String, String> statusMap) {
        String newStatus = statusMap.get("status");
        TaskResponseDto updatedTask = taskService.updateTaskStatus(taskId, newStatus);
        return ResponseEntity.ok(updatedTask);
    }


}
