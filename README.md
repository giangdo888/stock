# Logistics Management API

[![GitHub](https://img.shields.io/badge/GitHub-Repository-181717?logo=github)](https://github.com)

A Spring Boot application for managing warehouse inventory, products, and shipment flows with PostgreSQL persistence.

## Features

- Warehouse CRUD management
- Product inventory tracking and stock validation
- Shipment creation and status transitions
- PostgreSQL-backed persistence for local development
- REST API endpoints with validation and centralized error handling

## Tech Stack

- Java 21
- Spring Boot 3.5.6
- Spring Data JPA
- PostgreSQL 16
- Docker Compose
- Maven

## Prerequisites

- [Java 21 JDK](https://adoptium.net/)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker Desktop](https://www.docker.com/products/docker-desktop)

## Getting Started

### 1. Clone the repository

```bash
git clone <repository-url>
cd stock-management
```

### 2. Start the PostgreSQL database with Docker

From the project root:

```bash
docker compose up -d
```

This starts PostgreSQL on:

- Host: `localhost`
- Port: `15432`
- Database: `logistics`
- Username: `postgres`
- Password: `postgres`

### 3. Verify the database is running

```bash
docker compose ps
```

Optional direct check:

```bash
psql -h localhost -p 15432 -U postgres -d logistics
```

### 4. Run database migrations

The project uses Flyway to manage the database schema and seed data in a versioned, repeatable way.

Migration files live in:

```text
src/main/resources/db/migration/
```

Run them locally with:

```bash
./mvnw flyway:migrate
```

The application will also apply the same migration history automatically when it starts, using the datasource configured in:

```properties
spring.datasource.url=jdbc:postgresql://localhost:15432/logistics
```

This keeps local and cloud PostgreSQL environments aligned in a controlled way.

### 5. Run the application

```bash
./mvnw spring-boot:run
```

### 6. Test the API

The app exposes REST endpoints for:

- Warehouses
- Products
- Shipments

Example base URL:

```text
http://localhost:8080
```

You can use Swagger or curl/Postman to test the endpoints.

## Project Structure

```text
stock-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
├── README.md
└── project-description.md
```

## Useful Commands

### Stop the database

```bash
docker compose down
```

### Rebuild from scratch

```bash
docker compose down -v
docker compose up -d
```

### Run tests

```bash
./mvnw test
```

## License

Private project - All rights reserved.

