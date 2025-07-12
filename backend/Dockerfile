# Use a multi-stage build for efficiency
FROM gradle:8.0-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# Production image
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
