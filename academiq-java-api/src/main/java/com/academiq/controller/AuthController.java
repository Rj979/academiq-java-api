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
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend
public class AuthController {

    private final AppUserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthController(AppUserRepository userRepo, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AppUser user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "username_taken"));
        }
        // encode password
        user.setPassword(encoder.encode(user.getPassword()));

        // ensure role prefix
        if (user.getRole() != null && !user.getRole().startsWith("ROLE_")) {
            user.setRole("ROLE_" + user.getRole().toUpperCase());
        }

        AppUser saved = userRepo.save(user);
        saved.setPassword(null); // never expose password
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login/student")
    public ResponseEntity<?> loginStudent(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        return userRepo.findByUsername(username)
                .filter(u -> encoder.matches(password, u.getPassword()))
                .map(u -> {
                    if (!"ROLE_STUDENT".equals(u.getRole())) {
                        return ResponseEntity.status(403).body(Map.of("message", "Not a student account"));
                    }
                    String token = jwtUtils.generateToken(u.getUsername());
                    u.setPassword(null);
                    return ResponseEntity.ok(Map.of("token", token, "user", u));
                })
                .orElse(ResponseEntity.status(403).body(Map.of("message", "Invalid credentials")));
    }

    @PostMapping("/login/staff")
    public ResponseEntity<?> loginStaff(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        return userRepo.findByUsername(username)
                .filter(u -> encoder.matches(password, u.getPassword()))
                .map(u -> {
                    if (!"ROLE_STAFF".equals(u.getRole())) {
                        return ResponseEntity.status(403).body(Map.of("message", "Not a staff account"));
                    }
                    String token = jwtUtils.generateToken(u.getUsername());
                    u.setPassword(null);
                    return ResponseEntity.ok(Map.of("token", token, "user", u));
                })
                .orElse(ResponseEntity.status(403).body(Map.of("message", "Invalid credentials")));
    }
}
