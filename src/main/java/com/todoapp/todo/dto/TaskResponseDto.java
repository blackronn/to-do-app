package com.todoapp.todo.dto;


import lombok.Data;

@Data
public class TaskResponseDto {
    private Long id;
    private String description;
    private String title;
    private String status;
    private Long userId;


}

