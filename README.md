# 🎓 AcademiQ - College Management System API

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-10.x-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Authentication-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)

A robust and secure REST API backend for a modern college data management system, built with Java and Spring Boot.

---

## 📖 Table of Contents

-   [About The Project](#-about-the-project)
-   [Key Features](#-key-features)
-   [Tech Stack](#-tech-stack)
-   [System Architecture](#-system-architecture)
-   [Getting Started](#-getting-started)
    -   [Prerequisites](#prerequisites)
    -   [Installation & Setup](#installation--setup)
-   [Running with Docker](#-running-with-docker)
-   [API Endpoints](#-api-endpoints)
-   [Contributing](#-contributing)
-   [License](#-license)
-   [Author](#-author)

## 🌟 About The Project

**AcademiQ** provides a full-stack ready backend to streamline academic operations for educational institutions. It is designed to manage students, teachers, courses, and grades through a secure, scalable, and well-documented RESTful API, making it easy to integrate with any frontend application.

## ✅ Key Features

-   **Secure Authentication**: Role-based access control (RBAC) using JWT for `ADMIN`, `STAFF`, and `STUDENT` roles.
-   **User Management**: Complete CRUD operations for students, staff, and administrators.
-   **Course & Enrollment**: Easily manage departments, courses, subjects, and student enrollments.
-   **Academic Management**: Handle marks, grades, and paper submissions efficiently.
-   **Timetabling**: Organize and view department and course timetables.
-   **RESTful by Design**: Clean, intuitive, and consistent API endpoints.
-   **Containerized**: Docker support for seamless deployment and scalability.

## 🛠️ Tech Stack

| Category         | Technology                               |
| ---------------- | ---------------------------------------- |
| **Backend** | Java 17+, Spring Boot 3.x, Spring Web    |
| **Data** | Spring Data JPA (Hibernate), MariaDB     |
| **Security** | Spring Security 6, JWT (JSON Web Tokens) |
| **Build Tool** | Apache Maven                             |
| **Deployment** | Docker                                   |
| **Frontend** | _(Ready for any client like React, Vue, or Angular)_ |

## 🏗️ System Architecture

The application follows a classic multi-layered architecture to ensure a clean separation of concerns.

```

\+----------------+      +------------------------+      +-------------------+      +----------------------+      +----------------+
|   Client App   | \<--\> |   Controller Layer     | \<--\> |   Service Layer   | \<--\> |   Repository Layer   | \<--\> |    Database    |
| (React/Vue/etc)|      | (REST API Endpoints)   |      | (Business Logic)  |      | (JPA / Hibernate)    |      |    (MariaDB)   |
\+----------------+      +------------------------+      +-------------------+      +----------------------+      +----------------+
|                                                                                                        |
\+-------------------------------------\> Security (JWT Filter) \<------------------------------------------+

````

1.  **Controller Layer**: Exposes REST endpoints (`/api/**`). It handles HTTP requests, validates input, and delegates to the service layer.
2.  **Service Layer**: Contains the core business logic. It orchestrates data from different repositories and performs operations.
3.  **Repository Layer**: Abstracts data access using Spring Data JPA. It handles all database interactions.
4.  **Security**: A JWT-based filter intercepts incoming requests to authenticate and authorize users based on their roles.

## 🚀 Getting Started

Follow these instructions to get a local copy up and running for development and testing.

### Prerequisites

-   **Java JDK 17+**: [Download Link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
-   **Apache Maven 3.8+**: [Download Link](https://maven.apache.org/download.cgi)
-   **MariaDB Server**: [Download Link](https://mariadb.org/download/)
-   **Docker** (Optional, for containerized setup): [Download Link](https://www.docker.com/products/docker-desktop/)

### Installation & Setup

1.  **Clone the repository:**
    ```sh
    git clone [https://github.com/Rj979/academiq-java-api.git](https://github.com/Rj979/academiq-java-api.git)
    cd academiq-java-api
    ```

2.  **Configure the database:**
    -   Log in to your MariaDB instance and create a new database.
        ```sql
        CREATE DATABASE academiq_db;
        ```
    -   Update the database credentials in `src/main/resources/application.properties`:
        ```properties
        spring.datasource.url=jdbc:mariadb://localhost:3306/academiq_db
        spring.datasource.username=YOUR_DB_USERNAME
        spring.datasource.password=YOUR_DB_PASSWORD
        ```

3.  **Build the application:**
    ```sh
    ./mvnw clean package -DskipTests
    ```

4.  **Run the application:**
    ```sh
    ./mvnw spring-boot:run
    ```
    The API will be available at `http://localhost:8080`.

## 🐳 Running with Docker

1.  **Create a `.env` file** in the root directory and add your environment variables:
    ```env
    DB_URL=jdbc:mariadb://YOUR_DOCKER_HOST_IP:3306/academiq_db
    DB_USER=YOUR_DB_USERNAME
    DB_PASS=YOUR_DB_PASSWORD
    JWT_SECRET=YourSuperSecretKeyForJWTs
    ```
    *Note: `localhost` will not work from inside the container. Use your machine's local network IP.*

2.  **Build the Docker image:**
    ```sh
    docker build -t academiq-api .
    ```

3.  **Run the Docker container:**
    ```sh
    docker run -p 8080:8080 --env-file .env academiq-api
    ```

## 🌐 API Endpoints

Here are some examples of the available API endpoints. All protected routes require a `Bearer <token>` in the `Authorization` header.

| Method | Endpoint                    | Description                          | Access      |
| ------ | --------------------------- | ------------------------------------ | ----------- |
| `POST` | `/api/auth/login`           | Authenticate a user and get a JWT.   | Public      |
| `POST` | `/api/auth/register`        | Register a new student or staff user.  | Admin       |
| `GET`  | `/api/students`             | Get a list of all students.          | Admin       |
| `GET`  | `/api/students/{id}`        | Get details for a specific student.  | Admin/Staff |
| `POST` | `/api/courses`              | Create a new course.                 | Admin       |
| `GET`  | `/api/courses`              | Get a list of all available courses. | Authenticated |
| `POST` | `/api/enrollments`          | Enroll a student in a course.        | Admin/Staff |
| `GET`  | `/api/me`                   | Get the profile of the logged-in user. | Authenticated |

## 👨‍💻 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

## 📜 License

Distributed under the MIT License. See `LICENSE` for more information.

---

## ✒️ Author

**Rj979** - [GitHub](https://github.com/Rj979)

````
