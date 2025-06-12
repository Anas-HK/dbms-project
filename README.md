# Ecommerce Database Management System

This repository contains a Java-based Ecommerce Database Management System with a MySQL backend, containerized with Docker for easy deployment.

## Prerequisites

- [Docker](https://www.docker.com/products/docker-desktop/) (version 20.10.0 or higher)
- [Docker Compose](https://docs.docker.com/compose/install/) (version 2.0.0 or higher, included with Docker Desktop)
- Git (optional, for cloning the repository)

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
   - Build the Java application container
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

The MySQL data is persisted in a Docker volume named `db_data`. This ensures that your data remains intact even after stopping and restarting the containers.

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

## License

[Add your license information here] 