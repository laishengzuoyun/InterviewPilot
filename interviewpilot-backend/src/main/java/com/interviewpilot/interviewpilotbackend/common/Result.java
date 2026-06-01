package com.interviewpilot.interviewpilotbackend.common;

public final class Result<T> {

	private final int code;
	private final String message;
	private final T data;

	private Result(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static Result<Void> success() {
		return success(null);
	}

	public static <T> Result<T> success(T data) {
		return new Result<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
	}

	public static Result<Void> fail(ErrorCode errorCode) {
		return fail(errorCode, errorCode.getMessage());
	}

	public static Result<Void> fail(ErrorCode errorCode, String message) {
		return fail(errorCode.getCode(), message);
	}

	public static Result<Void> fail(int code, String message) {
		return new Result<>(code, message, null);
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public T getData() {
		return data;
	}
}
