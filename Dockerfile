# Use the official OpenJDK 20 JDK image for the build stage
FROM openjdk:20-jdk-slim as build

# Set the working directory in the container
WORKDIR /app

# Copy the local code to the container
COPY . /app

# Give execution permission to the mvnw script
RUN chmod +x ./mvnw

# Run the Maven build
RUN ./mvnw clean package -DskipTests

# Use a smaller JDK image for the final stage
FROM openjdk:20-slim

# Set the working directory for the app in the final image
WORKDIR /app

# Copy the JAR file from the build stage to the final image
COPY --from=build /app/target/myapp.jar /app/myapp.jar

# Expose the port the app will run on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/app/myapp.jar"]
