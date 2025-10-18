package com.academiq.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendance")
@CrossOrigin(origins = {"https://rj979.github.io", "http://localhost:3000", "http://localhost:5173"})
public class AttendanceController {
    
    @GetMapping("/student/{studentId}/{date}")
    public ResponseEntity<?> getStudentAttendance(@PathVariable Long studentId, @PathVariable String date) {
        try {
            // Placeholder data - you'll need to implement proper attendance logic
            List<Map<String, Object>> attendance = List.of(
                Map.of(
                    "hour", "1st Hour",
                    "paper", Map.of(
                        "id", 1,
                        "paper", "Data Structures"
                    ),
                    "attendance", Map.of(
                        "present", true
                    )
                ),
                Map.of(
                    "hour", "2nd Hour", 
                    "paper", Map.of(
                        "id", 2,
                        "paper", "Algorithms"
                    ),
                    "attendance", Map.of(
                        "present", false
                    )
                )
            );
            
            return ResponseEntity.ok(attendance);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch attendance: " + e.getMessage()));
        }
    }
}
