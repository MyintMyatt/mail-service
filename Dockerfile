# Stage 1: Build application

FROM gradle:9.2.1-jdk21 AS builder

WORKDIR /app
COPY .env /app/.env
COPY --chown=gradle:gradle . /app



# Use system Gradle (already installed) to build

RUN gradle clean build -x test --no-daemon



# Stage 2: Runtime image

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar



EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
