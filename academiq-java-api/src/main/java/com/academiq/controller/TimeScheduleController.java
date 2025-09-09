package com.academiq.controller;

import com.academiq.model.TimeSchedule;
import com.academiq.repository.TimeScheduleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/time_schedule")
@CrossOrigin(origins = "http://localhost:3000")
public class TimeScheduleController {

    private final TimeScheduleRepository repo;

    public TimeScheduleController(TimeScheduleRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getByUser(@PathVariable Long userId) {
        return repo.findByUserId(userId)
                .map(ts -> ResponseEntity.ok(Map.of(
                        "id", ts.getId(),
                        "userId", ts.getUserId(),
                        "schedule", ts.getScheduleJson()
                )))
                .orElse(ResponseEntity.status(404).body(Map.of("message", "Not found")));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> createOrConflict(@PathVariable Long userId, @RequestBody Map<String, Object> body) {
        if (repo.findByUserId(userId).isPresent()) {
            return ResponseEntity.status(409).body(Map.of("message", "Already exists"));
        }
        String scheduleJson = (String) body.get("scheduleJson");
        if (scheduleJson == null && body.get("schedule") != null) {
            scheduleJson = com.fasterxml.jackson.databind.json.JsonMapper.builder().build().createObjectNode().toString();
        }
        TimeSchedule ts = new TimeSchedule(userId, scheduleJson);
        ts = repo.save(ts);
        return ResponseEntity.ok(Map.of("message", "Created", "id", ts.getId()));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> update(@PathVariable Long userId, @RequestBody Map<String, Object> body) {
        return repo.findByUserId(userId)
                .map(existing -> {
                    String scheduleJson = (String) body.get("scheduleJson");
                    if (scheduleJson == null && body.get("schedule") != null) {
                        try {
                            scheduleJson = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(body.get("schedule"));
                        } catch (Exception e) {
                            return ResponseEntity.badRequest().body(Map.of("message", "Invalid schedule"));
                        }
                    }
                    existing.setScheduleJson(scheduleJson);
                    repo.save(existing);
                    return ResponseEntity.ok(Map.of("message", "Updated"));
                })
                .orElse(ResponseEntity.status(404).body(Map.of("message", "Not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Deleted"));
    }
}


