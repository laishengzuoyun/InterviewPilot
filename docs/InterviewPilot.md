# InterviewPilot

> AI Resume Analysis & Mock Interview Platform

------

# 1. Project Overview

InterviewPilot is an AI-powered career coaching platform built with Spring Boot and Vue3.

Users can upload resumes, create target job positions, receive AI-generated matching reports, conduct mock interviews, and obtain personalized feedback.

The project aims to help job seekers identify skill gaps, improve interview performance, and better prepare for their target positions.

------

# 2. Project Objectives

Build a complete full-stack web application that supports:

- User Authentication
- Resume Management
- Job Management
- AI Job Matching Analysis
- AI Mock Interviews
- History Management

------

# 3. Technology Stack

## Backend

- Java 21
- Spring Boot 4.0.6
- Spring Security
- JWT
- MyBatis Plus
- MySQL
- PDFBox
- Apache POI

## Frontend

- Vue 3
- Vite
- Element Plus
- Axios
- Pinia
- Vue Router

## AI Services

- OpenAI API
- DeepSeek API

## Deployment

- Docker
- Nginx

------

# 4. System Architecture

```text
User
  ↓
Vue3 Frontend
  ↓
Spring Boot REST API
  ↓
MySQL Database
  ↓
AI Model API
```

------

# 5. Functional Modules

## 5.1 User Module

### Features

- User Registration
- User Login
- JWT Authentication
- Update Profile
- Change Password

### Entity

### User

| Field        | Type     |
| ------------ | -------- |
| id           | Long     |
| username     | String   |
| email        | String   |
| passwordHash | String   |
| createdAt    | DateTime |

------

## 5.2 Resume Module

### Features

- Upload PDF Resume
- Upload DOCX Resume
- Parse Resume Content
- View Resume
- Delete Resume

### Entity

### Resume

| Field       | Type     |
| ----------- | -------- |
| id          | Long     |
| userId      | Long     |
| fileName    | String   |
| filePath    | String   |
| contentText | Text     |
| createdAt   | DateTime |

------

## 5.3 Job Module

### Features

- Create Job
- Edit Job
- Delete Job
- View Job Details
- Browse Job List

### Entity

### Job

| Field        | Type     |
| ------------ | -------- |
| id           | Long     |
| userId       | Long     |
| title        | String   |
| company      | String   |
| description  | Text     |
| requirements | Text     |
| createdAt    | DateTime |

------

## 5.4 AI Matching Module

### Input

- Resume Content
- Job Description

### Output

- Matching Score
- Strengths Analysis
- Missing Skills
- Resume Improvement Suggestions
- Interview Preparation Suggestions

### Entity

### MatchReport

| Field       | Type     |
| ----------- | -------- |
| id          | Long     |
| userId      | Long     |
| resumeId    | Long     |
| jobId       | Long     |
| score       | Integer  |
| strengths   | Text     |
| weaknesses  | Text     |
| suggestions | Text     |
| rawAiResult | Text     |
| createdAt   | DateTime |

------

## 5.5 AI Mock Interview Module

### Workflow

```text
Select Resume
      ↓
Select Job
      ↓
Generate Interview Questions
      ↓
User Answers Questions
      ↓
AI Evaluates Answers
      ↓
Generate Final Report
```

### AI Feedback

- Question Score
- Strengths
- Weaknesses
- Improvement Suggestions
- Overall Evaluation

### Entity

### InterviewSession

| Field           | Type     |
| --------------- | -------- |
| id              | Long     |
| userId          | Long     |
| resumeId        | Long     |
| jobId           | Long     |
| overallFeedback | Text     |
| createdAt       | DateTime |

### InterviewQuestion

| Field     | Type     |
| --------- | -------- |
| id        | Long     |
| sessionId | Long     |
| question  | Text     |
| answer    | Text     |
| feedback  | Text     |
| score     | Integer  |
| createdAt | DateTime |

------

## 5.6 History Module

### Features

- View Match Reports
- View Interview Records
- Delete History
- Search History

------

# 6. Page Design

## Login

```text
/login
```

Features:

- Login
- Navigate to Register

------

## Register

```text
/register
```

Features:

- User Registration

------

## Dashboard

```text
/dashboard
```

Display:

- Resume Count
- Job Count
- Latest Match Report
- Latest Interview Result

------

## Resume Management

```text
/resumes
```

Features:

- Upload Resume
- View Resume
- Delete Resume

------

## Job Management

```text
/jobs
```

Features:

- Create Job
- Edit Job
- Delete Job

------

## AI Matching

```text
/match
```

Features:

- Select Resume
- Select Job
- Generate Analysis

------

## Mock Interview

```text
/interview
```

Features:

- Start Interview
- Submit Answers
- View Feedback

------

## History

```text
/history
```

Features:

- View Match History
- View Interview History

------

# 7. REST API Design

## Authentication

### Register

```http
POST /api/auth/register
```

### Login

```http
POST /api/auth/login
```

### Profile

```http
GET /api/user/profile
PUT /api/user/profile
```

------

## Resume APIs

### Upload Resume

```http
POST /api/resumes/upload
```

### Get Resumes

```http
GET /api/resumes
```

### Get Resume Detail

```http
GET /api/resumes/{id}
```

### Delete Resume

```http
DELETE /api/resumes/{id}
```

------

## Job APIs

### Create Job

```http
POST /api/jobs
```

### Get Jobs

```http
GET /api/jobs
```

### Get Job Detail

```http
GET /api/jobs/{id}
```

### Update Job

```http
PUT /api/jobs/{id}
```

### Delete Job

```http
DELETE /api/jobs/{id}
```

------

## Matching APIs

### Generate Match Report

```http
POST /api/match/analyze
```

### Get Match History

```http
GET /api/match/history
```

### Get Match Detail

```http
GET /api/match/{id}
```

------

## Interview APIs

### Start Interview

```http
POST /api/interviews/start
```

### Submit Answer

```http
POST /api/interviews/{id}/answer
```

### Finish Interview

```http
POST /api/interviews/{id}/finish
```

### Get Interview History

```http
GET /api/interviews/history
```

### Get Interview Detail

```http
GET /api/interviews/{id}
```

------

# 8. Development Roadmap

## Week 1

### Goals

- Spring Boot Setup
- Vue Setup
- MySQL Configuration
- Login & Registration
- JWT Authentication
- Dashboard Page

### Deliverable

User can successfully log into the system.

------

## Week 2

### Goals

- Resume Upload
- PDF Parsing
- DOCX Parsing
- Job CRUD

### Deliverable

Users can upload resumes and manage jobs.

------

## Week 3

### Goals

- AI Job Matching
- AI Interview Question Generation
- AI Feedback Analysis

### Deliverable

Core AI features are available.

------

## Week 4

### Goals

- History Records
- UI Optimization
- Docker Deployment
- README
- Demo Video

### Deliverable

MVP Version 1.0 Completed.

------

# 9. MVP Acceptance Criteria

The user can:

1. Register and Login
2. Upload a Resume
3. Create a Job Position
4. Generate an AI Matching Report
5. Complete a Mock Interview
6. Receive AI Feedback
7. View Historical Records

If all above features work successfully, the MVP 1.0 is considered complete.

------

# 10. Resume Description

Developed an AI-powered career coaching platform using Spring Boot and Vue 3. Implemented resume upload and parsing, AI-based job matching analysis, mock interview generation, personalized feedback reports, JWT authentication, and full-stack deployment.
