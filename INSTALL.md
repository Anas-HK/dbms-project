# Running the Ecommerce System

A containerized Java-based Ecommerce Database Management System.

## Prerequisites

- [Docker](https://www.docker.com/products/docker-desktop/)

## Quick Start

1. Create a directory and save this as `docker-compose.yml`:
```yaml
services:
  app:
    image: anashk/dbms-project:1.1
    container_name: ecommerce-app
    depends_on:
      db:
        condition: service_healthy
    environment:
      - DB_HOST=db
      - DB_NAME=cinema
      - DB_USER=root
      - DB_PASS=root
    stdin_open: true
    tty: true
    restart: unless-stopped

  db:
    image: anashk/dbms-project-db:1.1
    container_name: ecommerce-db
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: cinema
      MYSQL_ALLOW_EMPTY_PASSWORD: "yes"
    volumes:
      - db_data:/var/lib/mysql
    ports:
      - "3306:3306"
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-u", "root", "-proot"]
      interval: 5s
      timeout: 5s
      retries: 5
      start_period: 30s
    restart: unless-stopped

volumes:
  db_data:
    name: ecommerce-db-data
```

2. Start the containers:
```bash
docker-compose up -d
```

3. Attach to the application (this will open the program interface):
```bash
docker attach ecommerce-app
```

4. To detach from the program without stopping it: Press `Ctrl+P` followed by `Ctrl+Q`

## Basic Commands

Start containers:
```bash
docker-compose up -d
```

Stop containers:
```bash
docker-compose down
```

View logs:
```bash
# Application logs
docker-compose logs app

# Database logs
docker-compose logs db
```

Reset everything (including database):
```bash
docker-compose down -v
docker-compose up -d
``` 