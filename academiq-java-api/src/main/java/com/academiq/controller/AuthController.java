package com.academiq.controller;

import com.academiq.model.AppUser;
import com.academiq.repository.AppUserRepository;
import com.academiq.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AppUserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthController(AppUserRepository userRepo, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }
    @PostMapping("/init-passwords/students")
    public ResponseEntity<?> initStudentPasswords(@RequestBody Map<String, String> body) {
        String newPassword = body.getOrDefault("password", "student@123");
        try {
            var users = userRepo.findAll();
            int updated = 0;
            for (var u : users) {
                if ("ROLE_STUDENT".equals(u.getRole())) {
                    u.setPassword(encoder.encode(newPassword));
                    userRepo.save(u);
                    updated++;
                }
            }
            return ResponseEntity.ok(Map.of("message", "Passwords initialized", "count", updated));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Failed: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AppUser user) {
        try {
            if (userRepo.findByUsername(user.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Username already exists"));
            }

            // Encode password
            user.setPassword(encoder.encode(user.getPassword()));

            // Ensure role prefix - handle both "STUDENT"/"STAFF" and "ROLE_STUDENT"/"ROLE_STAFF"
            String role = user.getRole();
            if (role != null) {
                if (!role.startsWith("ROLE_")) {
                    user.setRole("ROLE_" + role.toUpperCase());
                }
            } else {
                user.setRole("ROLE_STUDENT"); // Default role
            }

            AppUser saved = userRepo.save(user);
            saved.setPassword(null); // Never expose password
            
            return ResponseEntity.ok(Map.of(
                "message", "User registered successfully",
                "user", saved
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Registration failed: " + e.getMessage()));
        }
    }

    @PostMapping("/login/student")
    public ResponseEntity<?> loginStudent(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username and password are required"));
        }

        return userRepo.findByUsername(username)
                .filter(u -> encoder.matches(password, u.getPassword()))
                .map(u -> {
                    if (!"ROLE_STUDENT".equals(u.getRole())) {
                        return ResponseEntity.status(403).body(Map.of("message", "Not a student account"));
                    }
                    String token = jwtUtils.generateToken(u.getUsername());
                    u.setPassword(null);
                    return ResponseEntity.ok(Map.of(
                        "token", token, 
                        "user", u,
                        "message", "Login successful"
                    ));
                })
                .orElse(ResponseEntity.status(401).body(Map.of("message", "Invalid credentials")));
    }

    @PostMapping("/login/staff")
    public ResponseEntity<?> loginStaff(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username and password are required"));
        }

        return userRepo.findByUsername(username)
                .filter(u -> encoder.matches(password, u.getPassword()))
                .map(u -> {
                    if (!"ROLE_STAFF".equals(u.getRole()) && !"ROLE_TEACHER".equals(u.getRole())) {
                        return ResponseEntity.status(403).body(Map.of("message", "Not a staff account"));
                    }
                    String token = jwtUtils.generateToken(u.getUsername());
                    u.setPassword(null);
                    return ResponseEntity.ok(Map.of(
                        "token", token, 
                        "user", u,
                        "message", "Login successful"
                    ));
                })
                .orElse(ResponseEntity.status(401).body(Map.of("message", "Invalid credentials")));
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (jwtUtils.validateToken(token)) {
                    String username = jwtUtils.getUsernameFromToken(token);
                    return userRepo.findByUsername(username)
                            .map(user -> {
                                user.setPassword(null);
                                return ResponseEntity.ok(Map.of("valid", true, "user", user));
                            })
                            .orElse(ResponseEntity.status(401).body(Map.of("valid", false, "message", "User not found")));
                }
            }
            return ResponseEntity.status(401).body(Map.of("valid", false, "message", "Invalid token"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("valid", false, "message", "Token validation failed"));
        }
    }
}