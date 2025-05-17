# service-click-collector/Dockerfile

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY build/libs/service-click-collector-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
