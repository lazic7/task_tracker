package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;

import java.util.List;

public interface TaskService {
    List<Task> loadTasks(Long projectId, TaskStatus status);
    Task createTask(Task task);
}
