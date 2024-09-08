# Endpoints monitoring service

## Requirements:
MySql Database
Java 21

## Setup
Check the `src/main/resources/application*.properties` for configuration.

### create mysql db if not exists

```sql
CREATE DATABASE mysql;
CREATE USER root WITH PASSWORD 'applifting';
GRANT ALL PRIVILEGES ON DATABASE mysql TO root;
```

###create schema if not exists
```sql
\c mysql;
CREATE SCHEMA treasure_tortoise;
GRANT ALL PRIVILEGES ON SCHEMA endpoint_monitoring TO mysql;
```

## SWAGGER UI:
http://localhost:8080/swagger-ui/index.htmlgradele