# Use the official aws 21 image from Docker Hub
FROM public.ecr.aws/amazoncorretto/amazoncorretto:21.0.6-al2023-headless
# Set working directory inside the container
WORKDIR /app
# Copy the compiled Java application JAR file into the container
COPY ./target/CountryCurrencyAPI.jar /app
# Expose the port the Spring Boot application will run on
EXPOSE 8080
# Command to run the application
CMD ["java", "-jar", "CountryCurrencyAPI.jar"]