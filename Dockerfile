FROM openjdk:17-jdk-slim


COPY target/*.jar app.jar

# application settings
COPY config/env/application-docker.yaml config/env/application-docker.yaml

# port for web service
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar", "-Dspring.config.location=./config/env/application-docker.yaml"]