package com.interviewpilot.interviewpilotbackend.model.dto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

class RegisterRequestTest {

	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	void validRequestPassesValidation() {
		RegisterRequest request = new RegisterRequest("pilot", "pilot@example.com", "Password123");

		assertTrue(validator.validate(request).isEmpty());
	}

	@Test
	void invalidUsernameEmailAndPasswordFailValidation() {
		RegisterRequest request = new RegisterRequest("ab", "not-email", "short");

		assertFalse(validator.validate(request).isEmpty());
	}
}
