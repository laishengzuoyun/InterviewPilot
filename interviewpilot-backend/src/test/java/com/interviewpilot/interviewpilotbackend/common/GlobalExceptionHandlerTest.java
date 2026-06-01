package com.interviewpilot.interviewpilotbackend.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.http.MockHttpInputMessage;

class GlobalExceptionHandlerTest {

	private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

	@Test
	void businessExceptionUsesConfiguredErrorCodeAndMessage() {
		BusinessException exception = new BusinessException(ErrorCode.NOT_FOUND, "resume not found");

		ResponseEntity<Result<Void>> response = handler.handleBusinessException(exception);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		Result<Void> body = response.getBody();
		assertNotNull(body);
		assertEquals(404, body.getCode());
		assertEquals("resume not found", body.getMessage());
	}

	@Test
	void unreadableRequestBodyReturnsBadRequest() {
		HttpMessageNotReadableException exception = new HttpMessageNotReadableException(
				"bad json",
				new MockHttpInputMessage(new byte[0]));

		ResponseEntity<Result<Void>> response = handler.handleHttpMessageNotReadableException(exception);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Result<Void> body = response.getBody();
		assertNotNull(body);
		assertEquals(400, body.getCode());
		assertEquals("Invalid request body", body.getMessage());
	}
}
