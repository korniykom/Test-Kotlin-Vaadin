# TestTask — Full-Stack Kotlin Vaadin Application

A web application built with Kotlin, Vaadin 25, Spring Boot 4, and PostgreSQL.

## Tech Stack

- **Language:** Kotlin
- **UI Framework:** Vaadin 25 with Karibu-DSL
- **Backend:** Spring Boot 4
- **Database:** PostgreSQL 16
- **Containerization:** Docker & Docker Compose

## Running the Project

The entire application can be started with a single command:

```bash
docker-compose up
```

This will:
1. Start a PostgreSQL database
2. Apply schema migrations automatically
3. Seed the database with 500 test users
4. Start the application on port 8080

Open your browser and navigate to: **http://localhost:8080**

## Default Credentials

| Role  | username     | Password |
|-------|--------------|----------|
| Admin | admin | password |
| User  | user  | password |

## Features

### All Users
- View paginated list of users (name, email, created at, updated at)
- Search/filter by name and email

### Admin Only
- Create new users
- Edit existing users
- Delete users
