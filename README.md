# Notebook Application

## Table of Contents

- [Introduction](#introduction)
- [Getting Started](#getting-started)
    - [How to Run](#how-to-run)
- [About The Service](#about-the-service)

## Introduction

The Notebook Application provides a platform to manage notes. [Login](#login) is required for calling APIs.

## Getting Started

This application is packaged as a jar which has Tomcat 8 embedded. No Tomcat installation is necessary. You need to have Java 17 installed.

### How to Run
#### Prerequisites
- Docker
- Docker Compose

#### Running with Docker Compose
1. Ensure Docker is installed and running.
2. Navigate to the root directory of the project.
3. Run the following command to start the application and PostgreSQL database:

```bash
docker-compose up
```

#### Running the Application Locally
1. Ensure PostgreSQL is installed and running.
2. Update the application.properties file with your PostgreSQL configuration.
3. Run the following command to start the application:
```bash
./gradlew bootRun 
```



## About the Service

### Endpoints

#### Register

- Endpoint: POST /api/v1/register
- Description: Register user to the system
- Request Body:

```json
{
  "username": "admin",
  "password": "password",
  "email": "admin@mail.com",
  "name":  "admin",
  "surname": "admin",
  "roleId": 0
}
```


#### Login

- Endpoint: POST /api/v1/authenticate
- Description: Login to the system
- Request Body:

```json
{
  "username": "admin",
  "password": "password"
}
```

#### List All Notes

- Endpoint: GET /api/v1/notes/list
- Description: Retrieves details all the notes. Requires admin role to access.

#### Create a Note

- Endpoint: POST /api/v1/notes
- Description: Creates a new note.
- Request Body:

```json
{
  "title":"To-do List",
  "content":"Go to gym"
}

```
#### Delete a Note

- Endpoint: DELETE /api/v1/notes/{id}
- Description: Deletes a note of the authenticated user.
- Request Body:

#### Update Note

- Endpoint: PUT /api/v1/note/{id}
- Description: Updates note details of the authenticated user.
- Request Body:

```json
{
  "title":"To-do List",
  "content":"Go to market"
}
```
#### List User-notes

- Endpoint: POST /api/v1/notes/user-notes
- Description: Lists notes of the authenticated user.

