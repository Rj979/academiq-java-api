package com.academiq.controller;

import com.academiq.model.AppUser;
import com.academiq.repository.AppUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/teachers")
@CrossOrigin(origins = {"https://rj979.github.io", "http://localhost:3000", "http://localhost:5173"})
public class TeacherController {
    
    private final AppUserRepository userRepository;
    
    public TeacherController(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacher(@PathVariable Long id) {
        try {
            Optional<AppUser> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                AppUser user = userOptional.get();
                if (user.getRoles() != null && (user.getRoles().contains("STAFF") || user.getRoles().contains("ADMIN"))) {
                    return ResponseEntity.ok(Map.of(
                        "id", user.getId(),
                        "name", user.getFirstName() + " " + user.getLastName(),
                        "username", user.getUsername(),
                        "email", user.getEmail(),
                        "role", user.getRoles().contains("ADMIN") ? "ADMIN" : "STAFF"
                    ));
                }
            }
            
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch teacher: " + e.getMessage()));
        }
    }
}
