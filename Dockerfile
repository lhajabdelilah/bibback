# Use an official Java runtime as a parent image
FROM openjdk:20-jdk-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the local Gradle project files into the container
COPY . .

# Install Gradle and build the project (skip tests if needed)
RUN apt-get update && apt-get install -y gradle && \
    gradle build -x test

# Use a smaller JDK image for the final stage
FROM openjdk:20-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/your-app-name.jar /app/your-app-name.jar

# Expose the port your Spring Boot app is running on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/your-app-name.jar"]
