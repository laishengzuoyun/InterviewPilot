package com.interviewpilot.interviewpilotbackend.common;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	SUCCESS(200, "success", HttpStatus.OK),
	BAD_REQUEST(400, "Invalid request parameter", HttpStatus.BAD_REQUEST),
	UNAUTHORIZED(401, "Unauthorized", HttpStatus.UNAUTHORIZED),
	FORBIDDEN(403, "Forbidden", HttpStatus.FORBIDDEN),
	NOT_FOUND(404, "Resource not found", HttpStatus.NOT_FOUND),
	BUSINESS_ERROR(4000, "Business error", HttpStatus.BAD_REQUEST),
	INTERNAL_ERROR(500, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

	private final int code;
	private final String message;
	private final HttpStatus httpStatus;

	ErrorCode(int code, String message, HttpStatus httpStatus) {
		this.code = code;
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
