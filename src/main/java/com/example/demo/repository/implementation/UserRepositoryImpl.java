package com.example.demo.repository.implementation;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(
            JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAllUsers() {
        String sql = """
        SELECT id, username, email, created_at
        FROM users
        ORDER BY id
    """;

        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public Optional<User> findUserById(Long id) {
        String sql = """
        SELECT id, username, email, created_at
        FROM users
        WHERE id = ?
    """;

        List<User> results = jdbcTemplate.query(sql, new UserRowMapper(), id);
        return results.stream().findFirst();
    }

    @Override
    public User createUser(String username, String email) {
        String sql = """
        INSERT INTO users (username, email)
        VALUES (?, ?)
        RETURNING id, username, email, created_at
    """;

        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), username, email);
    }


}
