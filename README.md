# 🎓 AcademiQ Backend

AcademiQ is a modern **college management system** backend built with **Spring Boot, JPA, and MariaDB**.  
This service powers the AcademiQ ecosystem by providing secure APIs for students, staff, and administrators.  

---

## 🚀 Features
- ✅ **User Authentication & Authorization** (JWT-based)  
- ✅ **Student & Staff Management**  
- ✅ **Course & Enrollment Handling**  
- ✅ **Database-Driven** (MariaDB/MySQL with JPA/Hibernate)  
- ✅ **RESTful APIs** for frontend integration  
- ✅ **Dockerized** for easy deployment  

---

## 🏗️ Tech Stack
- **Java 21**  
- **Spring Boot 3** (Web, JPA, Security)  
- **Hibernate ORM**  
- **MariaDB / MySQL**  
- **JWT Authentication**  
- **Docker + Docker Compose**  

---

## 📂 Project Structure
```
academiq-java-api/
├── src/                # Source code
│   ├── main/java       # Java code (controllers, services, entities)
│   ├── main/resources  # Configs (application.properties / yaml)
│   └── test            # Unit and integration tests
├── target/             # Compiled JAR (after build)
├── pom.xml             # Maven dependencies
├── Dockerfile          # Docker build configuration
└── render.yaml         # Render.com deployment config
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
The JAR will be available in `target/academiq-java-api-0.0.1-SNAPSHOT.jar`.

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
This service is deployable on:
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
