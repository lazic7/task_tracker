package com.example.demo.service.implementation;

import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.TaskService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> loadTasks(Long projectId, TaskStatus status) {
        return taskRepository.findTasks(projectId, status);
    }

    @Override
    public Task createTask(Task task) {
        if (!StringUtils.hasText(task.getTitle()) || task.getTitle().length() > 200) {
            throw new IllegalArgumentException("Title is required and must be <= 200 chars.");
        }
        if (task.getProjectId() == null) {
            throw new IllegalArgumentException("projectId is required.");
        }
        if (task.getPriority() == null || task.getPriority() < 1 || task.getPriority() > 5) {
            throw new IllegalArgumentException("priority must be between 1 and 5.");
        }
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.TODO);
        }

        try {
            return taskRepository.createTask(task);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Invalid data (FK/constraint violation).");
        }
    }
}
