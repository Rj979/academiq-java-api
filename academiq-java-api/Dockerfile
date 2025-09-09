# Use Eclipse Temurin JDK 21 as base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven build files
COPY target/academiq-java-api-0.0.1-SNAPSHOT.jar app.jar

# Expose the port (Render uses PORT env variable)
EXPOSE 8080

# Run the jar with Render's PORT environment variable
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
