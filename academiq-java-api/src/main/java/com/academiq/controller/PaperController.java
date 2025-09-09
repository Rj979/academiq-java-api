package com.academiq.controller;

import com.academiq.model.Paper;
import com.academiq.repository.PaperRepository;
import com.academiq.repository.CourseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class PaperController {
    
    private final PaperRepository paperRepository;
    private final CourseRepository courseRepository;
    
    public PaperController(PaperRepository paperRepository, CourseRepository courseRepository) {
        this.paperRepository = paperRepository;
        this.courseRepository = courseRepository;
    }
    
    @GetMapping("/papers")
    public List<Paper> getAllPapers() {
        return paperRepository.findAll();
    }
    
    @GetMapping("/paper/{userType}/{userId}")
    public ResponseEntity<?> getPapersByUser(@PathVariable String userType, @PathVariable Long userId) {
        try {
            List<Map<String, Object>> papers;
            
            if ("teacher".equals(userType) || "staff".equals(userType)) {
                // For teachers, get papers they are assigned to teach
                papers = courseRepository.findPapersByTeacherId(userId);
            } else if ("student".equals(userType)) {
                // For students, get all papers (they can join any paper)
                papers = paperRepository.findAllPapersWithDetails();
            } else {
                return ResponseEntity.badRequest().body(Map.of("message", "Invalid user type"));
            }
            
            return ResponseEntity.ok(papers);
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Error fetching papers: " + e.getMessage()));
        }
    }
    
    @GetMapping("/papers/{id}")
    public ResponseEntity<Paper> getPaperById(@PathVariable Long id) {
        return paperRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/papers")
    public Paper createPaper(@RequestBody Paper paper) {
        return paperRepository.save(paper);
    }
    
    @PutMapping("/papers/{id}")
    public ResponseEntity<Paper> updatePaper(@PathVariable Long id, @RequestBody Paper paperDetails) {
        return paperRepository.findById(id)
                .map(paper -> {
                    paper.setCode(paperDetails.getCode());
                    paper.setName(paperDetails.getName());
                    paper.setCategory(paperDetails.getCategory());
                    paper.setCredits(paperDetails.getCredits());
                    paper.setDepartment(paperDetails.getDepartment());
                    return ResponseEntity.ok(paperRepository.save(paper));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/papers/{id}")
    public ResponseEntity<Void> deletePaper(@PathVariable Long id) {
        paperRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
