package com.academiq.controller;

import com.academiq.model.SimpleCourse;
import com.academiq.repository.SimpleCourseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/papers")
@CrossOrigin(origins = {"https://rj979.github.io", "http://localhost:3000", "http://localhost:5173"})
public class PapersController {
    
    private final SimpleCourseRepository courseRepository;
    
    public PapersController(SimpleCourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getPaper(@PathVariable Long id) {
        try {
            Optional<SimpleCourse> courseOptional = courseRepository.findById(id);
            if (courseOptional.isPresent()) {
                SimpleCourse course = courseOptional.get();
                return ResponseEntity.ok(Map.of(
                    "id", course.getId(),
                    "name", course.getName(),
                    "paper", course.getName(), // Alias for compatibility
                    "code", course.getCode(),
                    "department", course.getDepartment(),
                    "credits", course.getCredits(),
                    "description", course.getDescription(),
                    "semester", course.getSemester(),
                    "academicYear", course.getAcademicYear()
                ));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch paper: " + e.getMessage()));
        }
    }
}
