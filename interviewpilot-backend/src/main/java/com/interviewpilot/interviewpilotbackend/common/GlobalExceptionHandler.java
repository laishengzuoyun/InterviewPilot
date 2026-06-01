package com.interviewpilot.interviewpilotbackend.common;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Result<Void>> handleBusinessException(BusinessException exception) {
		ErrorCode errorCode = exception.getErrorCode();
		String message = messageOrDefault(exception.getMessage(), errorCode.getMessage());
		return buildResponse(errorCode, message);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Result<Void>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception) {
		String message = exception.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.filter(StringUtils::hasText)
			.findFirst()
			.orElse(ErrorCode.BAD_REQUEST.getMessage());
		return buildResponse(ErrorCode.BAD_REQUEST, message);
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<Result<Void>> handleBindException(BindException exception) {
		String message = exception.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.filter(StringUtils::hasText)
			.findFirst()
			.orElse(ErrorCode.BAD_REQUEST.getMessage());
		return buildResponse(ErrorCode.BAD_REQUEST, message);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Result<Void>> handleConstraintViolationException(
			ConstraintViolationException exception) {
		String message = exception.getConstraintViolations()
			.stream()
			.map(ConstraintViolation::getMessage)
			.filter(StringUtils::hasText)
			.findFirst()
			.orElse(ErrorCode.BAD_REQUEST.getMessage());
		return buildResponse(ErrorCode.BAD_REQUEST, message);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<Result<Void>> handleMissingServletRequestParameterException(
			MissingServletRequestParameterException exception) {
		String message = "Missing request parameter: " + exception.getParameterName();
		return buildResponse(ErrorCode.BAD_REQUEST, message);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Result<Void>> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException exception) {
		String message = "Invalid request parameter: " + exception.getName();
		return buildResponse(ErrorCode.BAD_REQUEST, message);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Result<Void>> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException exception) {
		return buildResponse(ErrorCode.BAD_REQUEST, "Invalid request body");
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<Result<Void>> handleNoResourceFoundException(NoResourceFoundException exception) {
		return buildResponse(ErrorCode.NOT_FOUND, ErrorCode.NOT_FOUND.getMessage());
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Result<Void>> handleAuthenticationException(AuthenticationException exception) {
		return buildResponse(ErrorCode.UNAUTHORIZED, ErrorCode.UNAUTHORIZED.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Result<Void>> handleAccessDeniedException(AccessDeniedException exception) {
		return buildResponse(ErrorCode.FORBIDDEN, ErrorCode.FORBIDDEN.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Result<Void>> handleException(Exception exception) {
		return buildResponse(ErrorCode.INTERNAL_ERROR, ErrorCode.INTERNAL_ERROR.getMessage());
	}

	private ResponseEntity<Result<Void>> buildResponse(ErrorCode errorCode, String message) {
		return ResponseEntity
			.status(errorCode.getHttpStatus())
			.body(Result.fail(errorCode, message));
	}

	private String messageOrDefault(String message, String defaultMessage) {
		return StringUtils.hasText(message) ? message : defaultMessage;
	}
}
