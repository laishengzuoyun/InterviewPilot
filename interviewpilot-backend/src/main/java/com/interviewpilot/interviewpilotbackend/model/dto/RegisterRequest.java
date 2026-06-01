package com.interviewpilot.interviewpilotbackend.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
		@NotBlank(message = "Username is required")
		@Size(min = 3, max = 50, message = "Username length must be between 3 and 50")
		String username,

		@NotBlank(message = "Email is required")
		@Email(message = "Email format is invalid")
		@Size(max = 100, message = "Email length must not exceed 100")
		String email,

		@NotBlank(message = "Password is required")
		@Size(min = 8, max = 72, message = "Password length must be between 8 and 72")
		String password
) {
}
