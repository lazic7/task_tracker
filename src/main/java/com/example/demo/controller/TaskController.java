package com.example.demo.controller;

import com.example.demo.dto.request.CreateTaskRequestDTO;
import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;
import com.example.demo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET /tasks?projectId=1&status=TODO
    @GetMapping
    public ResponseEntity<List<Task>> getTasks(
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) String status
    ) {
        TaskStatus parsedStatus = null;
        if (status != null && !status.isBlank()) {
            try {
                parsedStatus = TaskStatus.valueOf(status);
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }

        return ResponseEntity.ok(taskService.loadTasks(projectId, parsedStatus));
    }

    // POST /tasks
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskRequestDTO req) {
        Task task = new Task();
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setPriority(req.getPriority());
        task.setProjectId(req.getProjectId());
        task.setAssigneeId(req.getAssigneeId());

        if (req.getStatus() != null && !req.getStatus().isBlank()) {
            task.setStatus(TaskStatus.valueOf(req.getStatus())); // invalid -> IllegalArgumentException -> 400 iz handlera
        }

        if (req.getDueDate() != null && !req.getDueDate().isBlank()) {
            task.setDueDate(LocalDate.parse(req.getDueDate())); // invalid -> exception -> 400 (handler)
        }

        Task created = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
