🎓 AcademiQ
> A modern academic management platform for students, teachers & administrators 🚀
![AcademiQ Banner](https://img.shields.io/badge/AcademiQ-College%20Management-blueviolet?style=for-the-badge)

[![Build](https://img.shields.io/github/actions/workflow/status/Rj979/AcademiQ/maven.yml?style=flat-square)](https://github.com/Rj979/AcademiQ/actions)
![License](https://img.shields.io/github/license/Rj979/AcademiQ?style=flat-square)
![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen?style=flat-square&logo=springboot)
![MariaDB](https://img.shields.io/badge/MariaDB-Latest-blue?style=flat-square&logo=mariadb)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=flat-square&logo=docker)

📖 About
AcademiQ is a full-stack college management system designed to streamline academic operations for institutions.

- Manage student information, enrollment & profiles
- Organize courses, subjects & department timetables
- Handle marks, grades & paper submissions
- Manage teachers, staff roles & permissions
- Secure authentication & authorization using JWT
- Expose REST APIs for frontend integration
✨ Features
- Secure Login with JWT & role-based access
- Student Enrollment & course assignment
- Subject, Paper & Marks Management
- Teacher & Admin Dashboards
- Department Timetables & Scheduling
- REST API powered by Spring Boot
- Dockerized for seamless deployment
🛠️ Tech Stack
- Backend: Java 17+, Spring Boot 3.x, Hibernate (JPA)
- Database: MariaDB
- Authentication: JWT (JSON Web Tokens)
- Deployment: Docker, Render, or any PaaS
- Frontend (planned): React or Vue.js
⚙️ How AcademiQ Works
1. User Authentication & Authorization
   - Users (Students, Staff, Admins) login with username/password.
   - JWT generated on login and sent with every API request.
   - Roles: ADMIN (full access), STAFF (marks/courses), STUDENT (profile/grades).

2. Student & Staff Management
   - Admins can create, update, delete users.
   - Profiles stored in MariaDB via JPA/Hibernate.

3. Course & Enrollment Management
   - Courses assigned to staff, students enroll in multiple.
   - Many-to-many relationships managed via Enrollment table.

4. Marks & Papers Management
   - Teachers enter marks via API.
   - Linked to student and course entities.

5. RESTful API Layer
   - Controllers handle HTTP requests.
   - Services implement business logic.
   - Repositories handle DB operations via JPA.

6. Database Architecture
   - Entities: Users, Students, Staff, Courses, Enrollments, Marks.
   - Relationships: Staff→Courses (One-to-Many), Students↔Courses (Many-to-Many).

7. Security & Configuration
   - JWT Tokens, CORS, and environment-based configs.

8. Deployment & Scaling
   - Dockerized, cloud/VPS ready, horizontally scalable.

9. Data Flow Summary
   - Request → Controller → Service → Repository → DB → Response JSON.
🚀 Getting Started
### Prerequisites
- Java 17+
- Maven 3+
- MariaDB

### Clone & Build
git clone https://github.com/Rj979/AcademiQ.git
cd AcademiQ/academiq-java-api
./mvnw clean package -DskipTests
./mvnw spring-boot:run

### Run with Docker
docker build -t academiq-api .
docker run -p 8080:8080 --env-file .env academiq-api
🌐 API Endpoints (Examples)
- POST /api/auth/login → User login & get JWT
- POST /api/auth/register → Register student/staff
- GET /api/students → List all students (admin only)
- POST /api/courses → Add a new course
👨‍💻 Contributing
1. Fork the repository
2. Create a feature branch: git checkout -b feature-x
3. Commit changes: git commit -m "Add feature x"
4. Push & create a Pull Request
📜 License
MIT License © 2025 Rj979 (https://github.com/Rj979)
