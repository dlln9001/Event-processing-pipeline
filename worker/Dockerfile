# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-25-alpine AS build_stage
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:25-alpine
WORKDIR /app
# Copy only the built JAR from the first stage
COPY --from=build_stage /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]