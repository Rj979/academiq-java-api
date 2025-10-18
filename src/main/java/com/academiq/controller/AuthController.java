package com.academiq.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            // Check if username already exists
            if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
                return ResponseEntity.status(409).body(Map.of("error", "Username already exists"));
            }
            
            // Create new user
            AppUser newUser = new AppUser();
            newUser.setUsername(registerRequest.getUsername());
            newUser.setPassword(registerRequest.getPassword()); // Plain text for now
            newUser.setEmail(registerRequest.getEmail() != null ? registerRequest.getEmail() : "");
            newUser.setFirstName(registerRequest.getFirstName() != null ? registerRequest.getFirstName() : "");
            newUser.setLastName(registerRequest.getLastName() != null ? registerRequest.getLastName() : "");
            Set<String> roles = new HashSet<>();
            roles.add(registerRequest.getRole() != null ? registerRequest.getRole() : "ROLE_STUDENT");
            newUser.setRoles(roles);
            
            AppUser savedUser = userRepository.save(newUser);
            
            return ResponseEntity.ok(Map.of(
                "message", "User registered successfully",
                "user", Map.of(
                    "id", savedUser.getId(),
                    "username", savedUser.getUsername(),
                    "email", savedUser.getEmail(),
                    "role", savedUser.getRoles().iterator().next() // Get first role
                )
            ));
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Registration failed: " + e.getMessage()));
        }
    }

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
            if ("student".equals(normalizedUserType)) {
                roleAllowed = user.getRoles() != null && user.getRoles().contains("STUDENT");
            } else if ("teacher".equals(normalizedUserType) || "staff".equals(normalizedUserType)) {
                roleAllowed = user.getRoles() != null && user.getRoles().contains("STAFF");
            } else if ("admin".equals(normalizedUserType)) {
                roleAllowed = user.getRoles() != null && user.getRoles().contains("ADMIN");
            } else {
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
    
    public static class RegisterRequest {
        private String username;
        private String password;
        private String email;
        private String firstName;
        private String lastName;
        private String role;
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}