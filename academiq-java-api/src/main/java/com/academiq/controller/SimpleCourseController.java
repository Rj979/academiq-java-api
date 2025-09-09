package com.academiq.controller;

import com.academiq.model.SimpleCourse;
import com.academiq.repository.SimpleCourseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/simple-courses")
@CrossOrigin(origins = "http://localhost:3000")
public class SimpleCourseController {
    private final SimpleCourseRepository repo;

    public SimpleCourseController(SimpleCourseRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<SimpleCourse> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<SimpleCourse> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/department/{dept}")
    public List<SimpleCourse> byDept(@PathVariable String dept) { return repo.findByDepartment(dept); }

    @PostMapping
    public SimpleCourse create(@RequestBody SimpleCourse c) { return repo.save(c); }

    @PutMapping("/{id}")
    public ResponseEntity<SimpleCourse> update(@PathVariable Long id, @RequestBody SimpleCourse upd) {
        return repo.findById(id).map(existing -> {
            existing.setCode(upd.getCode());
            existing.setName(upd.getName());
            existing.setDescription(upd.getDescription());
            existing.setCredits(upd.getCredits());
            existing.setDepartment(upd.getDepartment());
            existing.setSemester(upd.getSemester());
            existing.setAcademicYear(upd.getAcademicYear());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


