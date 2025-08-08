package com.todoapp.todo.service.abstracts;

import com.todoapp.todo.dto.TaskCreateRequestDto;
import com.todoapp.todo.dto.TaskResponseDto;

import java.util.List;

public interface ITaskService {
    TaskResponseDto createTask(TaskCreateRequestDto taskCreateRequestDto);

    List<TaskResponseDto> getTasksByUserId(Long userId);

    void deleteTask(Long taskId);

    TaskResponseDto updateTaskStatus(Long taskId, String status);

}