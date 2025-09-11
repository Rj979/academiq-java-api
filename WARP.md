# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

Project scope
- Backend: Spring Boot (Java 17/21), JPA/Hibernate, JWT auth
- Database: MariaDB/MySQL in production; H2 optional for dev (commented in config)
- Containerization: Dockerfile provided
- Repo layout: Java app in academiq-java-api/; auxiliary Python scripts in repo root

Common commands (run from academiq-java-api/ unless noted)
- Build (skip tests):
  pwsh: ./mvnw -q -DskipTests clean package
  unix: ./mvnw -q -DskipTests clean package
- Build (with tests):
  ./mvnw -q clean verify
- Run locally (Spring Boot):
  ./mvnw -q spring-boot:run
- Run a single test (method/class):
  mvn -q -Dtest=MyTest#methodName -DfailIfNoTests=false test
  mvn -q -Dtest=MyTest test
- Run tests with filtering by package:
  mvn -q -Dtest=com.academiq.* test
- Run in Docker (from academiq-java-api/):
  docker build -t academiq-api .
  docker run -p 8080:8080 --env-file ../.env academiq-api
- Formatting/linting:
  This project does not include a Java formatter plugin in pom.xml. If desired, use Spotless or fmt plugins; otherwise rely on IDE formatting.

Environment configuration
- Primary configuration is in academiq-java-api/src/main/resources/application.properties
- For local dev, prefer using environment variables over committing secrets. Set at runtime, e.g.:
  pwsh: $env:SPRING_DATASOURCE_URL="jdbc:mariadb://<host>:<port>/<db>"
        $env:SPRING_DATASOURCE_USERNAME="{{DB_USER}}"
        $env:SPRING_DATASOURCE_PASSWORD="{{DB_PASSWORD}}"
        $env:JWT_SECRET="{{JWT_SECRET}}"
        ./mvnw spring-boot:run
- Dockerfile exposes PORT and expects a prebuilt JAR at target/academiq-java-api-0.0.1-SNAPSHOT.jar

High-level architecture
- Spring Boot application entrypoint: com.academiq.AcademiqApplication
  - @EnableJpaRepositories("com.academiq.repository") and @EntityScan("com.academiq.model")
- Layers:
  - Controller layer (com.academiq.controller): REST endpoints (e.g., AuthController under /api/auth)
  - Service layer (likely present though not all classes reviewed): business logic
  - Repository layer (com.academiq.repository): Spring Data JPA repositories (e.g., AppUserRepository)
  - Model layer (com.academiq.model): JPA entities (e.g., AppUser with roles via @ElementCollection)
  - Security (com.academiq.config + com.academiq.security):
    - SecurityConfig defines SecurityFilterChain: CSRF disabled; permits /api/v1/auth/**; authenticates others
    - JwtTokenProvider issues tokens used in AuthController
- Persistence:
  - JPA/Hibernate with MariaDB/MySQL driver at runtime; H2 dependency available
  - application.properties controls datasource, hibernate.ddl-auto=update, dialect MariaDBDialect
- Authentication flow:
  - AuthController exposes POST /api/auth/login/{userType}
  - Uses AuthenticationManager to authenticate, JwtTokenProvider to generate token
  - Returns token and limited user profile (id, username, email, names, roles)

What to know from README.md
- Tech stack: Java (README badges say 21), Spring Boot 3, JPA, MariaDB/MySQL, JWT, Docker
- Quickstart:
  - ./mvnw spring-boot:run
  - ./mvnw clean package -DskipTests
  - Docker build/run example
- Suggested .env variables for DB and JWT; use an env file and avoid committing secrets

Testing
- Maven Surefire/FailSafe via spring-boot-starter-test and spring-security-test
- Default test location: academiq-java-api/src/test/java/
- Example commands:
  - Run all: mvn -q test
  - Single test class: mvn -q -Dtest=AcademiqApplicationTests test
  - Single method: mvn -q -Dtest=AcademiqApplicationTests#contextLoads test

Endpoints and conventions
- Auth:
  - POST /api/auth/login/{userType}
- SecurityConfig currently permits /api/v1/auth/**; ensure endpoint prefixes align (either update SecurityConfig or controller mappings when adding new endpoints)
- CORS configured via spring.web.cors.* properties; adjust for frontend origins

Local dev tips specific to this repo
- If running via Docker, ensure the JAR exists (build first). The image uses eclipse-temurin:21-jdk-alpine
- If using H2 for quick tests, uncomment H2 console in application.properties and point to jdbc:h2:mem:testdb; for MariaDB, set SPRING_DATASOURCE_* env vars
- Python helper scripts in repo root (database_checker.py, test_backend_frontend.py) can aid in connectivity checks, but are not part of the Java build

Repository-specific cautions
- Do not commit real secrets. Replace values in application.properties with env vars for production/dev. Prefer an external .env consumed by docker run --env-file
- Align URL prefixes between controllers and SecurityConfig to avoid unintended 401/403 responses

