package com.interviewpilot.interviewpilotbackend.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

@ConfigurationProperties(prefix = "interviewpilot.upload")
public class UploadProperties {

	private String baseDir = "uploads";
	private DataSize maxFileSize = DataSize.ofMegabytes(10);
	private DataSize maxRequestSize = DataSize.ofMegabytes(20);

	public String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public DataSize getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(DataSize maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public DataSize getMaxRequestSize() {
		return maxRequestSize;
	}

	public void setMaxRequestSize(DataSize maxRequestSize) {
		this.maxRequestSize = maxRequestSize;
	}
}
