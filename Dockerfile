# Stage 1: Build the Spring Boot application
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies (for better caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/stripe-integration-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port and run the app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]