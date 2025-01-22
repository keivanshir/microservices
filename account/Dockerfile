FROM openjdk:17-jdk-slim
LABEL authors="Keivan Shirkoubian"

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/account-1.0-SNAPSHOT.jar account.jar

# Expose the port the app runs on
EXPOSE 8082

# Define the command to run the app
CMD ["java", "-jar", "account.jar"]