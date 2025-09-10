FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Set your JWT secret as an environment variable
ENV JWT_SECRET=7a9d8f3e2c1b6a5d4f0e1b2c3d4a5f6b7e8c9d0a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f8

COPY target/academiq-java-api-0.0.1-SNAPSHOT.jar app.jar

# Expose the dynamic Render port
EXPOSE ${PORT}

ENTRYPOINT ["java","-jar","app.jar"]
