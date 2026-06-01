package com.interviewpilot.interviewpilotbackend.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;

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

	@Test
	void authenticationExceptionReturnsUnauthorizedWithoutRawMessage() {
		BadCredentialsException exception = new BadCredentialsException("raw bad credential detail");

		ResponseEntity<Result<Void>> response = handler.handleAuthenticationException(exception);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		Result<Void> body = response.getBody();
		assertNotNull(body);
		assertEquals(401, body.getCode());
		assertEquals("Unauthorized", body.getMessage());
	}

	@Test
	void accessDeniedExceptionReturnsForbiddenWithoutRawMessage() {
		AccessDeniedException exception = new AccessDeniedException("raw access denied detail");

		ResponseEntity<Result<Void>> response = handler.handleAccessDeniedException(exception);

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
		Result<Void> body = response.getBody();
		assertNotNull(body);
		assertEquals(403, body.getCode());
		assertEquals("Forbidden", body.getMessage());
	}

	@Test
	void genericExceptionReturnsInternalErrorWithoutStackTrace() {
		RuntimeException exception = new RuntimeException("database password leaked in raw message");

		ResponseEntity<Result<Void>> response = handler.handleException(exception);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		Result<Void> body = response.getBody();
		assertNotNull(body);
		assertEquals(500, body.getCode());
		assertEquals("Internal server error", body.getMessage());
	}
}
