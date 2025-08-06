package com.todoapp.todo.repository;

import com.todoapp.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
//CRUD işlemleri için
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // extends etmem sayesiinde findAll findById gibi methodlar otomatikleşti

    List<Task> findByUserId(UUID userId);



}
