package com.interviewpilot.interviewpilotbackend.controller;

import com.interviewpilot.interviewpilotbackend.common.Result;
import com.interviewpilot.interviewpilotbackend.model.dto.RegisterRequest;
import com.interviewpilot.interviewpilotbackend.model.vo.RegisterResponse;
import com.interviewpilot.interviewpilotbackend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public Result<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
		return Result.success(authService.register(request));
	}
}
