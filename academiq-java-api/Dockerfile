FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/academiq-java-api-0.0.1-SNAPSHOT.jar app.jar

# Expose the dynamic Render port
EXPOSE ${PORT}

ENTRYPOINT ["java","-jar","app.jar"]
