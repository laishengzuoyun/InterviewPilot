package com.interviewpilot.interviewpilotbackend.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;

class SecurityExceptionHandlerTest {

	private final SecurityExceptionHandler handler = new SecurityExceptionHandler();

	@Test
	void authenticationFailureWritesUnifiedUnauthorizedJson() throws Exception {
		MockHttpServletResponse response = new MockHttpServletResponse();

		handler.commence(
				new MockHttpServletRequest("GET", "/api/resumes"),
				response,
				new BadCredentialsException("raw bad credential detail"));

		assertEquals(401, response.getStatus());
		assertTrue(response.getContentType().startsWith("application/json"));
		assertEquals("UTF-8", response.getCharacterEncoding());
		assertEquals("{\"code\":401,\"message\":\"Unauthorized\",\"data\":null}", response.getContentAsString());
	}

	@Test
	void accessDeniedFailureWritesUnifiedForbiddenJson() throws Exception {
		MockHttpServletResponse response = new MockHttpServletResponse();

		handler.handle(
				new MockHttpServletRequest("GET", "/api/resumes"),
				response,
				new AccessDeniedException("raw access denied detail"));

		assertEquals(403, response.getStatus());
		assertTrue(response.getContentType().startsWith("application/json"));
		assertEquals("UTF-8", response.getCharacterEncoding());
		assertEquals("{\"code\":403,\"message\":\"Forbidden\",\"data\":null}", response.getContentAsString());
	}
}
