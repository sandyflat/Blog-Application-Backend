# Blog Application Backend

A **Spring Boot** backend application for a blogging platform with **JWT authentication**, **role-based access control**, and RESTful APIs for managing users, posts, categories, and comments.

## Overview

This application provides backend functionality for a blogging platform. Users can **register, login, create posts, comment, and manage categories**, while **role-based access control** ensures only authorized actions are allowed for Admins and Users.

## Tech Stack

- **Java 21**  
- **Spring Boot**  
- **Spring Security (JWT Authentication)**  
- **Spring Data JPA**  
- **MySQL Database**  
- **Lombok**  
- **Maven**  

## Features

- User registration and login with **JWT tokens**  
- Role-based access: **Admin** vs **User**  
- CRUD operations for:
  - Users
  - Posts
  - Categories
  - Comments   
- Pagination and sorting support for posts  
- Exception handling with custom API responses  

## Project Structure

- `controller` – REST controllers for APIs  
- `entity` – JPA entity classes  
- `dto` – Data Transfer Objects  
- `repository` – Spring Data JPA repositories  
- `service` – Service interfaces  
- `serviceimpl` – Service implementations  
- `security` – JWT, authentication, and role-based security  
- `exception` – Custom exceptions and handlers  
- `config` – Application configurations  

## Setup Instructions

1. **Clone the repository**

`git clone <repo-url>`  
`cd Blog-Application-Backend`

2. **Configure MySQL database** in `application.properties`

- `spring.datasource.url = jdbc:mysql://localhost:3306/db_name`  
- `spring.datasource.username = db_username`  
- `spring.datasource.password = db_password`  
- `spring.jpa.hibernate.ddl-auto = update`

3. **Build and run the application**

`mvn clean install`  
`mvn spring-boot:run`

4. The application runs at: `http://localhost:8080`  

## Authentication & Authorization

- **JWT-based Authentication**  
  - `/api/auth/login` – Login and receive **access** and **refresh tokens**  
  - `/api/auth/register-user` – Register new user (default role: USER)  
  - `/api/auth/refresh-token` – Refresh expired access token  

- **Role-based access**  
  - **ADMIN** – Manage users, categories, posts  
  - **USER** – Create posts, comment, and view content  

## API Endpoints

### Authentication

| Method | Endpoint                  | Description                   | Roles  |
|--------|---------------------------|-------------------------------|--------|
| POST   | `/api/auth/login`          | Login and get JWT tokens       | Public |
| POST   | `/api/auth/register-user`  | Register new user             | Public |
| POST   | `/api/auth/refresh-token`  | Refresh access token          | Public |

### Users

| Method | Endpoint          | Description           | Roles       |
|--------|-----------------|---------------------|------------|
| GET    | `/api/users`      | Get all users       | ADMIN      |
| GET    | `/api/users/{id}` | Get user by ID      | ADMIN, USER|
| PUT    | `/api/users/{id}` | Update user info    | ADMIN, USER|
| DELETE | `/api/users/{id}` | Delete user         | ADMIN      |

### Categories

| Method | Endpoint                | Description           | Roles       |
|--------|------------------------|---------------------|------------|
| POST   | `/api/categories`        | Create a new category| ADMIN      |
| PUT    | `/api/categories/{id}`   | Update category      | ADMIN      |
| GET    | `/api/categories`        | Get all categories   | ADMIN, USER|
| GET    | `/api/categories/{id}`   | Get category by ID   | ADMIN, USER|
| DELETE | `/api/categories/{id}`   | Delete category      | ADMIN      |

### Posts

| Method | Endpoint                                  | Description                         | Roles       |
|--------|------------------------------------------|-------------------------------------|------------|
| POST   | `/api/posts/user/{userId}/category/{categoryId}` | Create a post                     | ADMIN, USER|
| PUT    | `/api/posts/posts/{postId}`               | Update a post                        | ADMIN, USER|
| GET    | `/api/posts`                              | Get all posts (pagination & sorting)| ADMIN, USER|
| GET    | `/api/posts/{postId}`                     | Get post by ID                        | ADMIN, USER|
| GET    | `/api/posts/category/{categoryId}`        | Get posts by category                 | ADMIN, USER|
| GET    | `/api/posts/user/{userId}`                | Get posts by user                     | ADMIN, USER|
| GET    | `/api/posts/search/{keywords}`            | Search posts by title                 | ADMIN, USER|
| DELETE | `/api/posts/{postId}`                     | Delete a post                         | ADMIN, USER|

### Comments

| Method | Endpoint                    | Description          | Roles       |
|--------|----------------------------|-------------------|------------|
| POST   | `/api/comments/post/{postId}` | Create comment for post | ADMIN, USER|
| DELETE | `/api/comments/{commentId}`   | Delete comment          | ADMIN, USER|
