package com.interviewpilot.interviewpilotbackend.repository;

import com.interviewpilot.interviewpilotbackend.model.entity.User;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserRepository implements UserRepository {

	private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> new User(
			rs.getLong("id"),
			rs.getString("username"),
			rs.getString("email"),
			rs.getString("password_hash"),
			rs.getObject("created_at", LocalDateTime.class),
			rs.getObject("updated_at", LocalDateTime.class));

	private final JdbcTemplate jdbcTemplate;

	public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean existsByEmail(String email) {
		Integer count = jdbcTemplate.queryForObject(
				"SELECT COUNT(1) FROM `user` WHERE `email` = ?",
				Integer.class,
				email);
		return count != null && count > 0;
	}

	@Override
	public Optional<User> findByEmail(String email) {
		List<User> users = jdbcTemplate.query(
				"""
				SELECT `id`, `username`, `email`, `password_hash`, `created_at`, `updated_at`
				FROM `user`
				WHERE `email` = ?
				""",
				USER_ROW_MAPPER,
				email);
		return users.stream().findFirst();
	}

	@Override
	public User save(String username, String email, String passwordHash) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO `user` (`username`, `email`, `password_hash`) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, username);
			statement.setString(2, email);
			statement.setString(3, passwordHash);
			return statement;
		}, keyHolder);
		return findByEmail(email)
			.orElseGet(() -> new User(keyHolder.getKeyAs(Long.class), username, email, passwordHash, null, null));
	}
}
