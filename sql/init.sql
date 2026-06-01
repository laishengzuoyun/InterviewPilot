CREATE DATABASE IF NOT EXISTS `interviewpilot`
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE `interviewpilot`;

CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `password_hash` VARCHAR(255) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_username` (`username`),
  UNIQUE KEY `uk_user_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `resume` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `file_name` VARCHAR(255) NOT NULL,
  `file_path` VARCHAR(500) NOT NULL,
  `content_text` LONGTEXT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_resume_user_id` (`user_id`),
  KEY `idx_resume_created_at` (`created_at`),
  CONSTRAINT `fk_resume_user`
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `job` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `company` VARCHAR(100) NULL,
  `description` TEXT NULL,
  `requirements` TEXT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_job_user_id` (`user_id`),
  KEY `idx_job_created_at` (`created_at`),
  CONSTRAINT `fk_job_user`
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `match_report` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `resume_id` BIGINT NOT NULL,
  `job_id` BIGINT NOT NULL,
  `score` INT NULL,
  `strengths` TEXT NULL,
  `weaknesses` TEXT NULL,
  `suggestions` TEXT NULL,
  `interview_preparation` TEXT NULL,
  `raw_ai_result` LONGTEXT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_match_report_user_id` (`user_id`),
  KEY `idx_match_report_resume_id` (`resume_id`),
  KEY `idx_match_report_job_id` (`job_id`),
  KEY `idx_match_report_resume_job` (`resume_id`, `job_id`),
  KEY `idx_match_report_created_at` (`created_at`),
  CONSTRAINT `chk_match_report_score`
    CHECK (`score` IS NULL OR (`score` >= 0 AND `score` <= 100)),
  CONSTRAINT `fk_match_report_user`
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT `fk_match_report_resume`
    FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT `fk_match_report_job`
    FOREIGN KEY (`job_id`) REFERENCES `job` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `interview_session` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `resume_id` BIGINT NOT NULL,
  `job_id` BIGINT NOT NULL,
  `overall_feedback` TEXT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_interview_session_user_id` (`user_id`),
  KEY `idx_interview_session_resume_id` (`resume_id`),
  KEY `idx_interview_session_job_id` (`job_id`),
  KEY `idx_interview_session_created_at` (`created_at`),
  CONSTRAINT `fk_interview_session_user`
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT `fk_interview_session_resume`
    FOREIGN KEY (`resume_id`) REFERENCES `resume` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT `fk_interview_session_job`
    FOREIGN KEY (`job_id`) REFERENCES `job` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `interview_question` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `session_id` BIGINT NOT NULL,
  `question` TEXT NOT NULL,
  `answer` TEXT NULL,
  `feedback` TEXT NULL,
  `improved_answer` TEXT NULL,
  `score` INT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_interview_question_session_id` (`session_id`),
  KEY `idx_interview_question_created_at` (`created_at`),
  CONSTRAINT `chk_interview_question_score`
    CHECK (`score` IS NULL OR (`score` >= 0 AND `score` <= 100)),
  CONSTRAINT `fk_interview_question_session`
    FOREIGN KEY (`session_id`) REFERENCES `interview_session` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
