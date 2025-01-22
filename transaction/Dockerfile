FROM openjdk:17-jdk-slim
LABEL authors="Keivan Shirkoubian"

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/transaction-1.0-SNAPSHOT.jar transaction.jar

# Expose the port the app runs on
EXPOSE 8083

# Define the command to run the app
CMD ["java", "-jar", "transaction.jar"]