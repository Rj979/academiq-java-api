package com.academiq.controller;

import com.academiq.model.Mark;
import com.academiq.repository.MarkRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marks")
public class MarkController {
    private final MarkRepository repo;
    public MarkController(MarkRepository repo){this.repo=repo;}

    @GetMapping
    public List<Mark> all(){ return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Mark> get(@PathVariable Long id){ return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }

    @GetMapping("/student/{studentId}")
    public List<Mark> byStudent(@PathVariable Long studentId){ return repo.findByStudentId(studentId); }

    @GetMapping("/subject/{subjectId}")
    public List<Mark> bySubject(@PathVariable Long subjectId){ return repo.findBySubjectId(subjectId); }

    @PostMapping
    public Mark create(@RequestBody Mark m){ return repo.save(m); }

    @PutMapping("/{id}")
    public ResponseEntity<Mark> update(@PathVariable Long id,@RequestBody Mark upd){
        return repo.findById(id).map(existing->{ existing.setInternal_marks(upd.getInternal_marks()); existing.setSemester(upd.getSemester()); existing.setStudent(upd.getStudent()); existing.setSubject(upd.getSubject()); return ResponseEntity.ok(repo.save(existing)); }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){ repo.deleteById(id); return ResponseEntity.noContent().build(); }
}
