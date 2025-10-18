package com.academiq.controller;

import com.academiq.model.AppUser;
import com.academiq.model.Student;
import com.academiq.repository.AppUserRepository;
import com.academiq.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = {"https://rj979.github.io", "http://localhost:3000", "http://localhost:5173"})
public class StudentController {
    
    private final AppUserRepository userRepository;
    private final StudentRepository studentRepository;
    
    public StudentController(AppUserRepository userRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Long id) {
        try {
            // First try to find in AppUser table
            Optional<AppUser> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                AppUser user = userOptional.get();
                if (user.getRoles() != null && user.getRoles().contains("STUDENT")) {
                    return ResponseEntity.ok(Map.of(
                        "id", user.getId(),
                        "name", user.getFirstName() + " " + user.getLastName(),
                        "username", user.getUsername(),
                        "email", user.getEmail(),
                        "role", "STUDENT"
                    ));
                }
            }
            
            // Fallback to Student table
            Optional<Student> studentOptional = studentRepository.findById(id);
            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                return ResponseEntity.ok(Map.of(
                    "id", student.getId(),
                    "name", (student.getFirstName() != null ? student.getFirstName() : "") + 
                            (student.getLastName() != null ? " " + student.getLastName() : ""),
                    "roll_no", student.getRoll_no() != null ? student.getRoll_no() : "",
                    "email", student.getEmail() != null ? student.getEmail() : "",
                    "role", "STUDENT"
                ));
            }
            
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch student: " + e.getMessage()));
        }
    }
}
