package com.academiq.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academiq.model.AppUser;
import com.academiq.repository.AppUserRepository;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"https://rj979.github.io", "http://localhost:5173"})
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

            // Enforce role-based login based on {userType} in the path
            String normalizedUserType = userType == null ? "" : userType.trim().toLowerCase();
            boolean roleAllowed;
            switch (normalizedUserType) {
                case "student":
                    roleAllowed = user.getRoles() != null && user.getRoles().contains("STUDENT");
                    break;
                case "teacher": // map teacher login route to STAFF role
                case "staff":
                    roleAllowed = user.getRoles() != null && user.getRoles().contains("STAFF");
                    break;
                case "admin":
                    roleAllowed = user.getRoles() != null && user.getRoles().contains("ADMIN");
                    break;
                default:
                    return ResponseEntity.badRequest().body(Map.of("error", "Invalid userType: " + userType));
            }

            if (!roleAllowed) {
                return ResponseEntity.status(403).body(Map.of(
                    "error", "Role mismatch: user cannot log in as " + normalizedUserType
                ));
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