package com.academiq.controller;

import com.academiq.model.Subject;
import com.academiq.repository.SubjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    private final SubjectRepository repo;
    public SubjectController(SubjectRepository repo){this.repo=repo;}

    @GetMapping
    public List<Subject> all(){ return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> get(@PathVariable Long id){ return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }

    @PostMapping
    public Subject create(@RequestBody Subject s){ return repo.save(s); }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> update(@PathVariable Long id,@RequestBody Subject upd){
        return repo.findById(id).map(existing->{ existing.setCredits(upd.getCredits()); existing.setSubject_code(upd.getSubject_code()); existing.setSubject_name(upd.getSubject_name()); return ResponseEntity.ok(repo.save(existing)); }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){ repo.deleteById(id); return ResponseEntity.noContent().build(); }
}
