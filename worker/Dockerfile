# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-25-alpine AS build_stage
WORKDIR /app

# 1. Copy only the pom.xml (this layer only changes if your dependencies change)
COPY pom.xml .

# 2. Download dependencies (this will be cached unless pom.xml changes)
# go-offline tells Maven to download everything needed for the build
RUN mvn dependency:go-offline -B

# 3. Now copy the actual source code
COPY src ./src

# 4. Package the application (uses the already-downloaded dependencies)
# We use -o (offline) to ensure it doesn't try to connect to the internet
RUN mvn clean package -DskipTests -o

# Stage 2: Run the application
FROM eclipse-temurin:25-alpine
WORKDIR /app
# Copy only the built JAR from the first stage
COPY --from=build_stage /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]