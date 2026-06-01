package com.interviewpilot.interviewpilotbackend.model.entity;

import java.time.LocalDateTime;

public record User(
		Long id,
		String username,
		String email,
		String passwordHash,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {
}
