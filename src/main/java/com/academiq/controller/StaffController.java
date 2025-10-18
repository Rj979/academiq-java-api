package com.academiq.controller;

import com.academiq.model.AppUser;
import com.academiq.repository.AppUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/staff")
@CrossOrigin(origins = {"https://rj979.github.io", "http://localhost:3000", "http://localhost:5173"})
public class StaffController {
    
    private final AppUserRepository userRepository;
    
    public StaffController(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @GetMapping("/approve/{department}")
    public ResponseEntity<?> getStaffForApproval(@PathVariable String department) {
        try {
            // Get all staff with pending approval status
            List<AppUser> staff = userRepository.findByRolesContaining("STAFF");
            return ResponseEntity.ok(staff.stream()
                .filter(user -> user.getRoles() != null && user.getRoles().contains("STAFF"))
                .map(user -> Map.of(
                    "id", user.getId(),
                    "name", user.getFirstName() + " " + user.getLastName(),
                    "username", user.getUsername(),
                    "email", user.getEmail(),
                    "department", department
                ))
                .toList());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch staff: " + e.getMessage()));
        }
    }
    
    @GetMapping("/list/{department}")
    public ResponseEntity<?> getStaffByDepartment(@PathVariable String department) {
        try {
            // Get staff by department
            List<AppUser> staff = userRepository.findByRolesContaining("STAFF");
            return ResponseEntity.ok(staff.stream()
                .filter(user -> user.getRoles() != null && user.getRoles().contains("STAFF"))
                .map(user -> Map.of(
                    "id", user.getId(),
                    "name", user.getFirstName() + " " + user.getLastName(),
                    "username", user.getUsername(),
                    "email", user.getEmail()
                ))
                .toList());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch staff: " + e.getMessage()));
        }
    }
    
    @PatchMapping("/{staffId}")
    public ResponseEntity<?> updateStaff(@PathVariable Long staffId, @RequestBody Map<String, Object> updates) {
        try {
            Optional<AppUser> userOptional = userRepository.findById(staffId);
            if (userOptional.isPresent()) {
                AppUser user = userOptional.get();
                // Update user fields based on the updates map
                if (updates.containsKey("name")) {
                    String name = (String) updates.get("name");
                    String[] nameParts = name.split(" ", 2);
                    user.setFirstName(nameParts[0]);
                    if (nameParts.length > 1) {
                        user.setLastName(nameParts[1]);
                    }
                }
                if (updates.containsKey("email")) {
                    user.setEmail((String) updates.get("email"));
                }
                userRepository.save(user);
                return ResponseEntity.ok(Map.of("message", "Staff updated successfully"));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to update staff: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{staffId}")
    public ResponseEntity<?> deleteStaff(@PathVariable Long staffId) {
        try {
            if (userRepository.existsById(staffId)) {
                userRepository.deleteById(staffId);
                return ResponseEntity.ok(Map.of("message", "Staff deleted successfully"));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to delete staff: " + e.getMessage()));
        }
    }
}
