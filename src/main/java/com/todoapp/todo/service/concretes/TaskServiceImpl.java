package com.todoapp.todo.service.concretes;

import com.todoapp.todo.dto.TaskCreateRequestDto;
import com.todoapp.todo.dto.TaskResponseDto;
import com.todoapp.todo.model.User;
import com.todoapp.todo.model.Task;
import com.todoapp.todo.repository.TaskRepository;
import com.todoapp.todo.repository.UserRepository;
import com.todoapp.todo.service.abstracts.ITaskService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TaskResponseDto createTask(TaskCreateRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı, id: " + requestDto.getUserId()));

        Task task = new Task();
        task.setTitle(requestDto.getTitle());
        task.setDescription(requestDto.getDescription());
        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        return convertToDto(savedTask);

    }
    @Override
    public List<TaskResponseDto> getTasksByUserId(Long userId) {
        List<Task> tasks = taskRepository.findAllByUserId(userId);
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long taskId) {
        if(!taskRepository.existsById(taskId)) {
            throw new EntityNotFoundException("Görev bulunamadı, id: " + taskId);
        }
        taskRepository.deleteById(taskId);
    }

    @Override
    public TaskResponseDto updateTaskStatus(Long taskId, String status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Görev bulunamadı, id: " + taskId));

        task.setStatus(status);
        Task updatedTask = taskRepository.save(task);

        return convertToDto(updatedTask);
    }

    private TaskResponseDto convertToDto(Task task) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setUserId(task.getUser().getId());
        return dto;
    }
}
