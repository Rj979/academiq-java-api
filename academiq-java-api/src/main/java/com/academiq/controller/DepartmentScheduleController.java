package com.academiq.controller;

import com.academiq.model.DepartmentSchedule;
import com.academiq.repository.DepartmentScheduleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/department_schedule")
@CrossOrigin(origins = "http://localhost:3000")
public class DepartmentScheduleController {
    private final DepartmentScheduleRepository repo;

    public DepartmentScheduleController(DepartmentScheduleRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{department}")
    public ResponseEntity<?> get(@PathVariable String department) {
        return repo.findByDepartment(department.toUpperCase())
                .map(ds -> ResponseEntity.ok(Map.of(
                        "id", ds.getId(),
                        "department", ds.getDepartment(),
                        "schedule", ds.getScheduleJson()
                )))
                .orElse(ResponseEntity.status(404).body(Map.of("message", "Not found")));
    }

    @PostMapping("/{department}")
    public ResponseEntity<?> create(@PathVariable String department, @RequestBody Map<String, Object> body) {
        if (repo.findByDepartment(department.toUpperCase()).isPresent()) {
            return ResponseEntity.status(409).body(Map.of("message", "Already exists"));
        }
        String scheduleJson;
        try {
            scheduleJson = body.get("schedule") instanceof String
                    ? (String) body.get("schedule")
                    : new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(body.get("schedule"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid schedule"));
        }
        DepartmentSchedule ds = new DepartmentSchedule(department.toUpperCase(), scheduleJson);
        ds = repo.save(ds);
        return ResponseEntity.ok(Map.of("message", "Created", "id", ds.getId()));
    }

    @PatchMapping("/{department}")
    public ResponseEntity<?> update(@PathVariable String department, @RequestBody Map<String, Object> body) {
        return repo.findByDepartment(department.toUpperCase())
                .map(existing -> {
                    try {
                        String scheduleJson = body.get("schedule") instanceof String
                                ? (String) body.get("schedule")
                                : new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(body.get("schedule"));
                        existing.setScheduleJson(scheduleJson);
                        repo.save(existing);
                        return ResponseEntity.ok(Map.of("message", "Updated"));
                    } catch (Exception e) {
                        return ResponseEntity.badRequest().body(Map.of("message", "Invalid schedule"));
                    }
                })
                .orElse(ResponseEntity.status(404).body(Map.of("message", "Not found")));
    }
}


