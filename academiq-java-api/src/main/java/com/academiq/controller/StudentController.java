package com.academiq.controller;

import com.academiq.model.Student;
import com.academiq.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentRepository repo;
    public StudentController(StudentRepository repo){this.repo=repo;}

    @GetMapping
    public List<Student> all(){ return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Student> get(@PathVariable Long id){ return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }

    @GetMapping("/course/{courseId}")
    public List<Student> byCourse(@PathVariable Long courseId){ return repo.findByCourseId(courseId); }

    @PostMapping
    public Student create(@RequestBody Student s){ return repo.save(s); }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Long id,@RequestBody Student upd){
        return repo.findById(id).map(existing->{ existing.setName(upd.getName()); existing.setEmail(upd.getEmail()); existing.setRoll_no(upd.getRoll_no()); existing.setCourse(upd.getCourse()); return ResponseEntity.ok(repo.save(existing)); }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){ repo.deleteById(id); return ResponseEntity.noContent().build(); }
}
