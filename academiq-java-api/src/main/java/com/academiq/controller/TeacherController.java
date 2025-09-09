package com.academiq.controller;

import com.academiq.model.Teacher;
import com.academiq.repository.TeacherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@CrossOrigin(origins = "http://localhost:3000")
public class TeacherController {
    private final TeacherRepository repo;
    public TeacherController(TeacherRepository repo){this.repo=repo;}

    @GetMapping
    public List<Teacher> all(){ return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> get(@PathVariable Long id){ return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }

    @PostMapping
    public Teacher create(@RequestBody Teacher t){ return repo.save(t); }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> update(@PathVariable Long id,@RequestBody Teacher upd){
        return repo.findById(id).map(existing->{ 
            existing.setName(upd.getName()); 
            existing.setEmail(upd.getEmail()); 
            existing.setDepartment(upd.getDepartment()); 
            // Username and role removed - handled by AppUser model
            return ResponseEntity.ok(repo.save(existing)); 
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){ repo.deleteById(id); return ResponseEntity.noContent().build(); }
}
