package com.academiq.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/internal")
@CrossOrigin(origins = {"https://rj979.github.io", "http://localhost:3000", "http://localhost:5173"})
public class InternalController {
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentInternalMarks(@PathVariable Long studentId) {
        try {
            // Placeholder data - you'll need to implement proper internal marks logic
            List<Map<String, Object>> internalMarks = List.of(
                Map.of(
                    "paper", Map.of(
                        "id", 1,
                        "paper", "Data Structures",
                        "code", "CS201"
                    ),
                    "marks", Map.of(
                        "test", 8,
                        "seminar", 7,
                        "assignment", 9,
                        "attendance", 8
                    )
                ),
                Map.of(
                    "paper", Map.of(
                        "id", 2,
                        "paper", "Algorithms",
                        "code", "CS202"
                    ),
                    "marks", Map.of(
                        "test", 9,
                        "seminar", 8,
                        "assignment", 8,
                        "attendance", 9
                    )
                )
            );
            
            return ResponseEntity.ok(internalMarks);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch internal marks: " + e.getMessage()));
        }
    }
}
