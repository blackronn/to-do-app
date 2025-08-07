package com.todoapp.todo.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TaskResponseDto {
    private Long id;
    private String description;
    private String title;
    private String status;
    private LocalDateTime dueDate;
    private UUID userId;
    private LocalDateTime createdDate;

}

