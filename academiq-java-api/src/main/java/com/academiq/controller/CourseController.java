package com.academiq.controller;

import com.academiq.model.Course;
import com.academiq.repository.CourseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {
    private final CourseRepository repo;
    public CourseController(CourseRepository repo){this.repo=repo;}

    @GetMapping
    public List<Course> all(){ return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Course> get(@PathVariable Long id){ return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }

    @PostMapping
    public Course create(@RequestBody Course c){ return repo.save(c); }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id, @RequestBody Course upd){
        return repo.findById(id).map(existing->{ 
            existing.setTeacher(upd.getTeacher()); 
            existing.setPaper(upd.getPaper()); 
            existing.setSemester(upd.getSemester()); 
            existing.setAcademicYear(upd.getAcademicYear()); 
            return ResponseEntity.ok(repo.save(existing)); 
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){ repo.deleteById(id); return ResponseEntity.noContent().build(); }
}
