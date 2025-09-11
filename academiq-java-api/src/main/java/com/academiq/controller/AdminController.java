package com.academiq.controller;

import com.academiq.model.AppUser;
import com.academiq.model.Student;
import com.academiq.repository.AppUserRepository;
import com.academiq.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    private final JdbcTemplate jdbcTemplate;
    private final StudentRepository studentRepository;
    private final AppUserRepository appUserRepository;

    public AdminController(JdbcTemplate jdbcTemplate,
                           StudentRepository studentRepository,
                           AppUserRepository appUserRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentRepository = studentRepository;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping("/cleanup-legacy-tables")
    public ResponseEntity<?> cleanupLegacyTables() {
        // Only drop legacy/duplicate tables that are NOT used by current JPA mappings
        // Current entities use: APP_USER, student, teachers, courses, papers, subject
        // Drop: app_users, course, teacher (if exist)
        String[] legacy = new String[] {"app_users", "course", "teacher"};
        Map<String, Object> result = new HashMap<>();
        for (String table : legacy) {
            try {
                jdbcTemplate.execute("DROP TABLE IF EXISTS " + table);
                result.put(table, "dropped_if_existed");
            } catch (Exception e) {
                result.put(table, "error: " + e.getMessage());
            }
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/provision-student-users")
    public ResponseEntity<?> provisionStudentUsers(@RequestBody(required = false) Map<String, String> body) {
        String defaultPassword = body != null && body.get("password") != null ? body.get("password") : "student@123";
        List<Student> students = studentRepository.findAll();
        int created = 0;
        for (Student s : students) {
            String candidateUsername = s.getRoll_no();
            if (candidateUsername == null || candidateUsername.isBlank()) {
                candidateUsername = s.getEmail() != null && s.getEmail().contains("@")
                        ? s.getEmail().substring(0, s.getEmail().indexOf('@'))
                        : ("student" + s.getId());
            }
            final String username = candidateUsername;
            boolean exists = appUserRepository.findByUsername(username).isPresent();
            if (!exists) {
                AppUser user = AppUser.builder()
                        .username(username)
                        .email(s.getEmail() != null ? s.getEmail() : username + "@academiq.com")
                        .password(defaultPassword) // Plain text password
                        .role("ROLE_STUDENT")
                        .build();
                appUserRepository.save(user);
                created++;
            }
        }
        return ResponseEntity.ok(Map.of(
                "message", "Provisioned student users",
                "created", created,
                "totalStudents", students.size()
        ));
    }
}


