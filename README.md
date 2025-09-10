# 🎓 AcademiQ Backend

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-green)
![MariaDB](https://img.shields.io/badge/MariaDB-10.11-blue)
![Docker](https://img.shields.io/badge/Docker-latest-lightgrey)
![License](https://img.shields.io/badge/License-MIT-yellow)

AcademiQ is a modern **college management system** backend built with **Spring Boot, JPA, and MariaDB**. It provides secure APIs for managing students, staff, courses, and enrollments, powering the AcademiQ ecosystem.

---

## ⚙️ How the System Works

1. **Authentication & Authorization**
   - Users (students, staff, admins) authenticate via **JWT tokens**.
   - JWT tokens are required for accessing protected API endpoints.
   - Roles determine access: `ADMIN`, `STAFF`, `STUDENT`.

2. **Student & Staff Management**
   - Admins can add, update, or remove students and staff.
   - User profiles are stored in **MariaDB** via **JPA/Hibernate**.

3. **Course & Enrollment Handling**
   - Courses are created and assigned to staff.
   - Students can be enrolled in multiple courses.
   - Relationships are maintained in the database (Students ↔ Courses ↔ Staff).

4. **RESTful API Layer**
   - Controllers expose endpoints.
   - Services handle business logic.
   - Repositories manage database access via **JPA**.

5. **Database Structure**
   - Entities: Users, Students, Staff, Courses, Enrollments.
   - Relationships: One-to-many (Staff → Courses), Many-to-many (Students ↔ Courses).

6. **Security & Configuration**
   - **JWT authentication** secures the API.
   - **CORS** allows frontend connections.
   - Environment variables handle sensitive info.

7. **Deployment & Scaling**
   - Dockerized for easy deployment.
   - Supports cloud platforms or VPS.
   - Can scale horizontally with multiple containers.

---

## 🚀 Features

- ✅ JWT Authentication & Role-Based Authorization  
- ✅ Student & Staff CRUD Operations  
- ✅ Course & Enrollment Management  
- ✅ RESTful API for Frontend Integration  
- ✅ Database-Driven (MariaDB/MySQL)  
- ✅ Dockerized for Easy Deployment  

---

## 🏗️ Tech Stack

- **Java 21**  
- **Spring Boot 3**  
- **Hibernate ORM**  
- **MariaDB / MySQL**  
- **JWT Authentication**  
- **Docker + Docker Compose**  

---

## 📂 Project Structure
```
academiq-java-api/
├── src/                # Controllers, Services, Entities
│   ├── main/java
│   ├── main/resources  # Configs
│   └── test            # Unit & Integration tests
├── target/             # Compiled JAR after build
├── pom.xml             # Maven dependencies
├── Dockerfile          # Docker build config
└── render.yaml         # Deployment config
```

---

## ⚙️ Setup & Installation

### 1️⃣ Clone the repository
```bash
git clone https://github.com/Rj979/AcademiQ.git
cd AcademiQ/academiq-java-api
```

### 2️⃣ Configure environment variables
Create a `.env` file in the root:
```ini
DB_HOST=localhost
DB_PORT=3306
DB_NAME=academiq
DB_USER=root
DB_PASSWORD=password

JWT_SECRET=your-secret-key
JWT_EXPIRATION_MS=86400000
CORS_ALLOWED_ORIGINS=http://localhost:3000
```

### 3️⃣ Run with Maven
```bash
./mvnw spring-boot:run
```

### 4️⃣ Build JAR
```bash
./mvnw clean package -DskipTests
```

---

## 🐳 Run with Docker

### Build Docker image
```bash
docker build -t academiq-api .
```

### Run container
```bash
docker run -p 8080:8080 --env-file .env academiq-api
```

---

## 🌐 API Endpoints (Sample)

- `POST /api/auth/login` → Authenticate user & get JWT  
- `POST /api/auth/register` → Register student/staff  
- `GET /api/students` → List all students (admin only)  
- `POST /api/courses` → Add a new course  

*(Full API docs coming soon)*

---

## 🚀 Deployment

Deploy on:
- [Render](https://render.com)  
- [Railway](https://railway.app)  
- Docker / Kubernetes  
- Any VPS with Java + Docker support  

---

## 👨‍💻 Contributing

1. Fork the repo  
2. Create a feature branch (`git checkout -b feature-x`)  
3. Commit changes (`git commit -m "Added feature x"`)  
4. Push and create a PR 🎉  

---

## 📜 License
MIT License © 2025 [Rj979](https://github.com/Rj979)
