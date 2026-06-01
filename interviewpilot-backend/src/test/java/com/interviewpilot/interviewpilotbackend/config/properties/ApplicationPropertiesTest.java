package com.interviewpilot.interviewpilotbackend.config.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.unit.DataSize;

@SpringBootTest(properties = {
		"spring.profiles.active=test",
		"interviewpilot.upload.base-dir=target/test-uploads",
		"interviewpilot.upload.max-file-size=12MB",
		"interviewpilot.upload.max-request-size=24MB",
		"ai.provider=mimo",
		"ai.mimo.api-key=test-api-key",
		"ai.mimo.base-url=https://api.example.test",
		"ai.mimo.model=test-model"
})
class ApplicationPropertiesTest {

	@Autowired
	private UploadProperties uploadProperties;

	@Autowired
	private AiProperties aiProperties;

	@Test
	void uploadPropertiesBindFromEnvironment() {
		assertEquals("target/test-uploads", uploadProperties.getBaseDir());
		assertEquals(DataSize.ofMegabytes(12), uploadProperties.getMaxFileSize());
		assertEquals(DataSize.ofMegabytes(24), uploadProperties.getMaxRequestSize());
	}

	@Test
	void aiPropertiesBindFromEnvironment() {
		assertEquals("mimo", aiProperties.getProvider());
		assertEquals("test-api-key", aiProperties.getMimo().getApiKey());
		assertEquals("https://api.example.test", aiProperties.getMimo().getBaseUrl());
		assertEquals("test-model", aiProperties.getMimo().getModel());
	}
}
