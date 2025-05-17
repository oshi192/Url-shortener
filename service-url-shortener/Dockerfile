# service-url-shortener/Dockerfile

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy the built JAR from your Gradle build
COPY build/libs/service-url-shortener-0.0.1-SNAPSHOT.jar app.jar

# Run the JAR
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
