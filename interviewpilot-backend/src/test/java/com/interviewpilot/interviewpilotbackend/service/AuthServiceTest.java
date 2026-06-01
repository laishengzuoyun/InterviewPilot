package com.interviewpilot.interviewpilotbackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.interviewpilot.interviewpilotbackend.common.BusinessException;
import com.interviewpilot.interviewpilotbackend.model.dto.RegisterRequest;
import com.interviewpilot.interviewpilotbackend.model.entity.User;
import com.interviewpilot.interviewpilotbackend.model.vo.RegisterResponse;
import com.interviewpilot.interviewpilotbackend.repository.UserRepository;
import com.interviewpilot.interviewpilotbackend.service.impl.AuthServiceImpl;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthServiceTest {

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Test
	void registerStoresBcryptPasswordHashAndReturnsProfile() {
		FakeUserRepository userRepository = new FakeUserRepository();
		AuthService authService = new AuthServiceImpl(userRepository, passwordEncoder);

		RegisterResponse response = authService.register(
				new RegisterRequest("pilot", "pilot@example.com", "Password123"));

		assertEquals(1L, response.id());
		assertEquals("pilot", response.username());
		assertEquals("pilot@example.com", response.email());
		assertTrue(passwordEncoder.matches("Password123", userRepository.savedPasswordHash));
		assertTrue(userRepository.savedPasswordHash.startsWith("$2"));
	}

	@Test
	void registerRejectsDuplicateEmail() {
		FakeUserRepository userRepository = new FakeUserRepository();
		userRepository.emailExists = true;
		AuthService authService = new AuthServiceImpl(userRepository, passwordEncoder);

		BusinessException exception = assertThrows(BusinessException.class, () -> authService.register(
				new RegisterRequest("pilot", "pilot@example.com", "Password123")));

		assertEquals("Email already exists", exception.getMessage());
	}

	@Test
	void registerConvertsDatabaseDuplicateKeyToBusinessException() {
		FakeUserRepository userRepository = new FakeUserRepository();
		userRepository.throwDuplicateKeyOnSave = true;
		AuthService authService = new AuthServiceImpl(userRepository, passwordEncoder);

		BusinessException exception = assertThrows(BusinessException.class, () -> authService.register(
				new RegisterRequest("pilot", "pilot@example.com", "Password123")));

		assertEquals("Email already exists", exception.getMessage());
	}

	private static class FakeUserRepository implements UserRepository {

		private boolean emailExists;
		private boolean throwDuplicateKeyOnSave;
		private String savedPasswordHash;

		@Override
		public boolean existsByEmail(String email) {
			return emailExists;
		}

		@Override
		public Optional<User> findByEmail(String email) {
			return Optional.empty();
		}

		@Override
		public User save(String username, String email, String passwordHash) {
			if (throwDuplicateKeyOnSave) {
				throw new DuplicateKeyException("duplicate email");
			}
			this.savedPasswordHash = passwordHash;
			return new User(1L, username, email, passwordHash, LocalDateTime.now(), LocalDateTime.now());
		}
	}
}
