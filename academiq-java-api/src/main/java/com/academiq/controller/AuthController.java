package com.academiq.controller;

import com.academiq.model.AppUser;
import com.academiq.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class AuthController {

    @Autowired
    private AppUserRepository userRepository;

    @PostMapping("/login/{userType}")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, @PathVariable String userType) {
        try {
            // Simple database lookup
            Optional<AppUser> userOptional = userRepository.findByUsername(loginRequest.getUsername());
            
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(401).body(Map.of("error", "User not found"));
            }
            
            AppUser user = userOptional.get();
            
            // Simple password check (plain text comparison)
            if (!user.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid password"));
            }
            
            // Create simple response
            Map<String, Object> response = new HashMap<>();
            response.put("token", "simple-token-" + user.getId()); // Simple token
            response.put("user", Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "firstName", user.getFirstName() != null ? user.getFirstName() : "",
                "lastName", user.getLastName() != null ? user.getLastName() : "",
                "roles", user.getRoles()
            ));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Login failed: " + e.getMessage()));
        }
    }
    
    public static class LoginRequest {
        private String username;
        private String password;
        
        public String getUsername() {
            return username;
        }
        
        public void setUsername(String username) {
            this.username = username;
        }
        
        public String getPassword() {
            return password;
        }
        
        public void setPassword(String password) {
            this.password = password;
        }
    }
}