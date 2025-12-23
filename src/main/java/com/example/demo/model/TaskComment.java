package com.example.demo.model;

import java.time.OffsetDateTime;

public class TaskComment {
    private Long id;
    private Long taskId;
    private Long authorId;
    private String body;
    private OffsetDateTime createdAt;

    public TaskComment() {
    }

    public TaskComment(Long id, Long taskId, Long authorId, String body, OffsetDateTime createdAt) {
        this.id = id;
        this.taskId = taskId;
        this.authorId = authorId;
        this.body = body;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
