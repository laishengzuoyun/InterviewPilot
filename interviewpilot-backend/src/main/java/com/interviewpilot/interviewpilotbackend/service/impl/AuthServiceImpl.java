package com.interviewpilot.interviewpilotbackend.service.impl;

import com.interviewpilot.interviewpilotbackend.common.BusinessException;
import com.interviewpilot.interviewpilotbackend.common.ErrorCode;
import com.interviewpilot.interviewpilotbackend.model.dto.RegisterRequest;
import com.interviewpilot.interviewpilotbackend.model.entity.User;
import com.interviewpilot.interviewpilotbackend.model.vo.RegisterResponse;
import com.interviewpilot.interviewpilotbackend.repository.UserRepository;
import com.interviewpilot.interviewpilotbackend.service.AuthService;
import java.util.Locale;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public RegisterResponse register(RegisterRequest request) {
		String username = request.username().trim();
		String email = normalizeEmail(request.email());

		if (userRepository.existsByEmail(email)) {
			throw new BusinessException(ErrorCode.BAD_REQUEST, "Email already exists");
		}

		String passwordHash = passwordEncoder.encode(request.password());
		User user;
		try {
			user = userRepository.save(username, email, passwordHash);
		}
		catch (DuplicateKeyException exception) {
			throw new BusinessException(ErrorCode.BAD_REQUEST, "Email already exists");
		}
		return new RegisterResponse(user.id(), user.username(), user.email());
	}

	private String normalizeEmail(String email) {
		return email.trim().toLowerCase(Locale.ROOT);
	}
}
