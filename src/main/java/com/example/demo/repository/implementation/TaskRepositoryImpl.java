package com.example.demo.repository.implementation;

import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.mapper.TaskRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    public TaskRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Task> findTasks(Long projectId, TaskStatus status) {
        String baseSql = """
            SELECT id, title, description, status, priority, due_date, project_id, assignee_id, created_at, updated_at
            FROM tasks
        """;

        StringBuilder sql = new StringBuilder(baseSql);
        List<Object> params = new ArrayList<>();

        boolean hasWhere = false;

        if (projectId != null) {
            sql.append(" WHERE project_id = ?");
            params.add(projectId);
            hasWhere = true;
        }

        if (status != null) {
            sql.append(hasWhere ? " AND" : " WHERE");
            sql.append(" status = ?");
            params.add(status.name());
        }

        sql.append(" ORDER BY id");

        return jdbcTemplate.query(sql.toString(), new TaskRowMapper(), params.toArray());
    }

    @Override
    public Task createTask(Task task) {
        String sql = """
            INSERT INTO tasks (title, description, status, priority, due_date, project_id, assignee_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            RETURNING id, title, description, status, priority, due_date, project_id, assignee_id, created_at, updated_at
        """;

        return jdbcTemplate.queryForObject(
                sql,
                new TaskRowMapper(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus() == null ? null : task.getStatus().name(),
                task.getPriority(),
                task.getDueDate(),
                task.getProjectId(),
                task.getAssigneeId()
        );
    }
}
