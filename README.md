# Ecommerce Database Management System (v1.1)

This repository contains a fully containerized Java-based Ecommerce Database Management System. The entire application, including both the Java application and MySQL database, runs in Docker containers, making it completely independent and easy to deploy on any machine that has Docker installed.

## Prerequisites

- [Docker](https://www.docker.com/products/docker-desktop/) (version 20.10.0 or higher)
- [Docker Compose](https://docs.docker.com/compose/install/) (version 2.0.0 or higher, included with Docker Desktop)
- Git (optional, for cloning the repository)

No other dependencies required - everything runs inside Docker containers!

## Quick Start

1. Clone the repository (or download the source code):
   ```
   git clone https://github.com/yourusername/Ecommerce_System.git
   cd Ecommerce_System
   ```

2. Start the application using Docker Compose:
   ```
   docker-compose up
   ```

   This command will:
   - Build the Java application container (ecommerce-system:1.1)
   - Start the MySQL database container
   - Initialize the database with the schema from `ecommerce_db_setup.sql`
   - Start the application once the database is healthy

3. To run the application in the background:
   ```
   docker-compose up -d
   ```

4. To stop the application:
   ```
   docker-compose down
   ```

## Container Information

The application runs two containers:
1. **ecommerce-app** (ecommerce-system:1.1)
   - Java application container
   - Built from OpenJDK 21-slim base image
   - Automatically connects to the database container

2. **ecommerce-db** (mysql:8.0)
   - MySQL database container
   - Exposes port 3306
   - Data persisted in named volume: ecommerce-db-data

## Configuration

The Docker setup uses the following environment variables that can be modified in the `docker-compose.yml` file:

### Database Configuration
- `MYSQL_ROOT_PASSWORD`: The MySQL root password (default: `root`)
- `MYSQL_DATABASE`: The database name (default: `cinema`)

### Application Configuration
- `DB_HOST`: The hostname of the database server (default: `db`)
- `DB_NAME`: The database name to connect to (default: `cinema`)
- `DB_USER`: The database username (default: `root`)
- `DB_PASS`: The database password (default: `root`)

## Data Persistence

The MySQL data is persisted in a Docker volume named `ecommerce-db-data`. This ensures that your data remains intact even after stopping and restarting the containers.

## Development

### Rebuilding the Application

If you make changes to the Java code, rebuild the containers:

```
docker-compose down
docker-compose up --build
```

### Accessing the Database

You can connect to the MySQL database from your host machine using:
- Host: `localhost`
- Port: `3306`
- Username: `root`
- Password: `root`
- Database: `cinema`

### Database Initialization

The database is initialized with the schema defined in `ecommerce_db_setup.sql`, which is mounted as an initialization script in the MySQL container.

## Troubleshooting

### Connection Issues

If the application cannot connect to the database:

1. Ensure both containers are running:
   ```
   docker-compose ps
   ```

2. Check the logs for error messages:
   ```
   docker-compose logs app
   docker-compose logs db
   ```

3. Verify that the MySQL container is healthy:
   ```
   docker-compose exec db mysqladmin -u root -proot ping
   ```

### Resetting the Database

To completely reset the database and start fresh:

```
docker-compose down -v
docker-compose up
```

This will remove the database volume, causing the database to be reinitialized from the SQL script on next startup.

## Version History

### v1.1
- Added container health checks
- Improved database connection reliability
- Added container names and version tags
- Implemented automatic container restart
- Enhanced error messages and troubleshooting guidance

## License

[Add your license information here] 