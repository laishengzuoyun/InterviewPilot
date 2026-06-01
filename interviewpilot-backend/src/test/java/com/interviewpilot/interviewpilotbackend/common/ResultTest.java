package com.interviewpilot.interviewpilotbackend.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class ResultTest {

	@Test
	void successUsesUnifiedResponseShape() {
		Map<String, String> data = Map.of("project", "InterviewPilot");

		Result<Map<String, String>> result = Result.success(data);

		assertEquals(200, result.getCode());
		assertEquals("success", result.getMessage());
		assertEquals(data, result.getData());
	}

	@Test
	void failureUsesErrorCodeAndNullData() {
		Result<Void> result = Result.fail(ErrorCode.BAD_REQUEST);

		assertEquals(400, result.getCode());
		assertEquals("Invalid request parameter", result.getMessage());
		assertNull(result.getData());
	}
}
