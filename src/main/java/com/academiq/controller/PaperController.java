package com.academiq.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academiq.model.SimpleCourse;
import com.academiq.repository.SimpleCourseRepository;

@RestController
@RequestMapping("/api/paper")
@CrossOrigin(origins = {"https://rj979.github.io", "http://localhost:3000", "http://localhost:5173"})
public class PaperController {
    
    private final SimpleCourseRepository courseRepository;
    
    public PaperController(SimpleCourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    @GetMapping("/{userType}/{userId}")
    public ResponseEntity<?> getPapersByUser(@PathVariable String userType, @PathVariable Long userId) {
        try {
            List<SimpleCourse> papers = courseRepository.findAll();
            
            // For both students and staff, return the same papers
            // In a real implementation, you'd filter based on enrollment/department
            if ("student".equals(userType) || "staff".equals(userType)) {
                return ResponseEntity.ok(papers);
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid user type"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch papers: " + e.getMessage()));
        }
    }
    
    @GetMapping("/students/{paperId}")
    public ResponseEntity<?> getStudentsByPaper(@PathVariable Long paperId) {
        try {
            // This is a placeholder - you'll need to implement student-paper relationships
            return ResponseEntity.ok(List.of(
                Map.of("name", "Sample Student 1", "id", 1),
                Map.of("name", "Sample Student 2", "id", 2)
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch students: " + e.getMessage()));
        }
    }
    
    @GetMapping("/manage/{userId}")
    public ResponseEntity<?> getManagedPapers(@PathVariable Long userId) {
        try {
            // Placeholder for papers managed by a user
            return ResponseEntity.ok(List.of());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch managed papers: " + e.getMessage()));
        }
    }
    
    @GetMapping("/student/{userId}")
    public ResponseEntity<?> getStudentPapers(@PathVariable Long userId) {
        try {
            // Placeholder for papers a student is enrolled in
            return ResponseEntity.ok(List.of());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch student papers: " + e.getMessage()));
        }
    }
    
    @PatchMapping("/{paperId}")
    public ResponseEntity<?> updatePaper(@PathVariable Long paperId, @RequestBody Map<String, Object> updates) {
        try {
            // Placeholder for updating paper enrollment
            return ResponseEntity.ok(Map.of("message", "Paper updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to update paper: " + e.getMessage()));
        }
    }
}
