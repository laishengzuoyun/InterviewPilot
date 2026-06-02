# InterviewPilot Environment Setup

This file documents the local defaults and environment variables used by the backend.

## Local Profile

The backend uses the `local` profile by default:

```yaml
spring:
  profiles:
    active: ${SPRING_PROFILES_ACTIVE:local}
```

For local development, copy:

```text
interviewpilot-backend/src/main/resources/application-local.example.yml
```

to:

```text
interviewpilot-backend/src/main/resources/application-local.yml
```

Then fill in your own local database password and AI key. `application-local.yml` is ignored by Git and must not be committed.

## Environment Variables

| Variable | Default | Purpose |
| --- | --- | --- |
| `SERVER_PORT` | `8080` | Backend HTTP port |
| `SPRING_PROFILES_ACTIVE` | `local` | Active Spring profile |
| `DB_URL` | `jdbc:mysql://localhost:3306/interviewpilot?...&allowPublicKeyRetrieval=true` | MySQL JDBC URL |
| `DB_USERNAME` | `root` | MySQL username |
| `DB_PASSWORD` | empty | MySQL password |
| `DB_DRIVER_CLASS_NAME` | `com.mysql.cj.jdbc.Driver` | JDBC driver class |
| `UPLOAD_BASE_DIR` | `uploads` | Local resume upload directory |
| `UPLOAD_MAX_FILE_SIZE` | `10MB` | Max single upload file size |
| `UPLOAD_MAX_REQUEST_SIZE` | `20MB` | Max multipart request size |
| `AI_PROVIDER` | `mimo` | AI provider name |
| `MIMO_API_KEY` | empty | Mimo API key |
| `MIMO_BASE_URL` | empty | Mimo API base URL |
| `MIMO_MODEL` | empty | Mimo model name |

## PowerShell Example

```powershell
$env:DB_URL='jdbc:mysql://localhost:3306/interviewpilot?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true'
$env:DB_USERNAME='root'
$env:DB_PASSWORD='your-local-db-password'
$env:UPLOAD_BASE_DIR='uploads'
$env:MIMO_API_KEY='your-mimo-api-key'
$env:MIMO_BASE_URL='your-mimo-base-url'
$env:MIMO_MODEL='your-mimo-model'

mvn -pl interviewpilot-backend spring-boot:run
```

Keep real secrets in local environment variables or `application-local.yml`, never in committed files.
