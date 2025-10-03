# ==========================
# Build Stage
# ==========================
FROM maven:3.8.4-openjdk-17 AS build

# Set working directory inside container
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Package the application without running tests
RUN mvn clean package -DskipTests

# ==========================
# Run Stage
# ==========================
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy JAR file from build stage
COPY --from=build /app/target/academiq-java-api-0.0.1-SNAPSHOT.jar app.jar

# Expose the Spring Boot server port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
