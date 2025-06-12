# Use a base image with Java and JDK
FROM openjdk:21-slim

# Add metadata
LABEL version="1.1" \
      description="Ecommerce Database Management System" \
      maintainer="Your Organization"

# Install wget to download MySQL Connector if needed
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Create directories
RUN mkdir -p lib out src db

# Copy source code and database script
COPY src/ src/
COPY db/cinema.sql db/

# Ensure MySQL Connector exists or download it
COPY lib/mysql-connector-j-8.0.33.jar lib/mysql-connector-j-8.0.33.jar
RUN if [ ! -f lib/mysql-connector-j-8.0.33.jar ]; then \
    wget https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar -O lib/mysql-connector-j-8.0.33.jar; \
    fi

# Verify MySQL Connector exists
RUN ls -la lib/mysql-connector-j-8.0.33.jar || exit 1

# Compile Java source files
RUN javac -cp "lib/*" -d out src/*.java

# Set the default command
CMD ["java", "-cp", "out:lib/*", "main"]
