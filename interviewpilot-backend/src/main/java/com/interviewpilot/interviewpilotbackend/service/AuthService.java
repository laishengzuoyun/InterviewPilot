package com.interviewpilot.interviewpilotbackend.service;

import com.interviewpilot.interviewpilotbackend.model.dto.RegisterRequest;
import com.interviewpilot.interviewpilotbackend.model.vo.RegisterResponse;

public interface AuthService {

	RegisterResponse register(RegisterRequest request);
}
