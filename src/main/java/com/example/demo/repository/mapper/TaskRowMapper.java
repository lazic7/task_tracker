package com.example.demo.repository.mapper;

import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class TaskRowMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task t = new Task();
        t.setId(rs.getLong("id"));
        t.setTitle(rs.getString("title"));
        t.setDescription(rs.getString("description"));

        String statusStr = rs.getString("status");
        t.setStatus(statusStr == null ? null : TaskStatus.valueOf(statusStr));

        int priority = rs.getInt("priority");
        t.setPriority(rs.wasNull() ? null : priority);

        t.setDueDate(rs.getObject("due_date", LocalDate.class));

        long projectId = rs.getLong("project_id");
        t.setProjectId(rs.wasNull() ? null : projectId);

        Long assigneeId = rs.getObject("assignee_id", Long.class);
        t.setAssigneeId(assigneeId);

        t.setCreatedAt(rs.getObject("created_at", OffsetDateTime.class));
        t.setUpdatedAt(rs.getObject("updated_at", OffsetDateTime.class));
        return t;
    }
}
