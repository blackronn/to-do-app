package com.todoapp.todo.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskCreateRequestDto {
    private String title;
    private String description;
    private Long userId;

}
