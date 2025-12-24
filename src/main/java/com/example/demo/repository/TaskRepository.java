package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;

import java.util.List;

public interface TaskRepository {
    List<Task> findTasks(Long projectId, TaskStatus status);
    Task createTask(Task task);
}
