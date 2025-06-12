# Use a base image with Java and JDK
FROM openjdk:21-slim

# Create app directory
WORKDIR /app

# Copy dependencies and source code
COPY lib/ /app/lib
COPY src/ /app/src

# Compile all Java source files
RUN mkdir out && \
    javac -cp "lib/*" -d out src/*.java

# Set classpath and run the main class
CMD ["java", "-cp", "out:lib/*", "main"]
