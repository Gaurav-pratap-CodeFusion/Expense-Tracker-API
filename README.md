# Expense Tracker API

A secure and role-based Expense Tracker Backend application built using Spring Boot, Spring Security, JWT Authentication, MySQL, and REST APIs.

This project allows users to manage expenses with an admin approval workflow system. Users can add expenses, admins can approve/reject them, and both users/admins can communicate through expense comments.

---

# Features

## Authentication & Authorization

* JWT Authentication
* Secure Login & Registration
* Password Encryption using BCrypt
* Role-Based Access Control
* Spring Security Integration

---

# Roles

## USER

Users can:

* Register/Login
* Add Expenses
* Update Expenses
* Delete Expenses
* View Own Expenses
* Comment on Own Expenses

---

## ADMIN

Admins can:

* View All Expenses
* View Pending Expenses
* Approve Expenses
* Reject Expenses
* Add Comments on Any Expense

---

# Expense Workflow

## 1. User Adds Expense

Expense status becomes:

PENDING

---

## 2. Admin Reviews Expense

Admin can:

* Approve Expense
* Reject Expense with Comment

---

## 3. User Updates Rejected Expense

When a rejected expense is updated:

Status automatically changes back to PENDING

for re-review.

---

# Technologies Used

## Backend

* Java
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* Hibernate
* MySQL
* Maven

---

## API Documentation

* Swagger / OpenAPI

---

## Frontend (Planned)

* Next.js
* Tailwind CSS
* Axios

---

# Project Structure

```text
src/main/java/com/gpcf/expense_tracker_api

├── config
├── controller
├── dto
├── entity
├── enums
├── exception
├── repository
├── security
│   ├── config
│   ├── jwt
│   └── service
├── service
│   └── impl
```

---

# Database Design

## Tables

* users
* roles
* user_roles
* expenses
* expense_comments

---

# Relationships

## User ↔ Roles

Many-to-Many

---

## User → Expenses

One-to-Many

---

## Expense → Comments

One-to-Many

---

# Security Features

* JWT Token Authentication
* Protected APIs
* Role-Based Authorization
* Ownership-Based Access Control
* Custom Exception Handling

---

# API Modules

## Authentication APIs

```text
POST /auth/register
POST /auth/login
```

---

## Expense APIs

```text
POST   /expenses
GET    /expenses/my-expenses
GET    /expenses/{id}
PUT    /expenses/{id}
DELETE /expenses/{id}
```

---

## Admin APIs

```text
GET /admin/expenses
GET /admin/expenses/pending

PUT /admin/expenses/{id}/approve
PUT /admin/expenses/{id}/reject
```

---

## Comment APIs

```text
POST /expenses/{expenseId}/comments
GET  /expenses/{expenseId}/comments
```

---

# Validation

Implemented using:

* @NotBlank
* @NotNull
* @Positive
* @Email
* @Size

---

# Exception Handling

Custom Global Exception Handling for:

* Validation Errors
* Access Denied
* Resource Not Found
* Duplicate Users
* Internal Server Errors

---

# Swagger Documentation

Swagger UI available at:

```text
http://localhost:8080/api-docs
```

---

# Setup Instructions

## 1. Clone Repository

```bash
git clone <repository-url>
```

---

## 2. Configure Database

Update:

application.properties

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker?createDatabaseIfNotExist=true

spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

---

## 3. Run Application

```bash
mvn spring-boot:run
```

---

# Future Improvements

* Next.js Frontend
* Dashboard Analytics
* Charts & Reports
* Email Notifications
* File Attachments
* Pagination & Filtering
* Docker Deployment
* CI/CD Pipeline

---

# Author

## Gaurav Pratap

Backend Developer | Java & Spring Boot Enthusiast

---

# Project Status

Backend Completed
Frontend In Progress
