package com.interviewpilot.interviewpilotbackend.repository;

import com.interviewpilot.interviewpilotbackend.model.entity.User;
import java.util.Optional;

public interface UserRepository {

	boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);

	User save(String username, String email, String passwordHash);
}
