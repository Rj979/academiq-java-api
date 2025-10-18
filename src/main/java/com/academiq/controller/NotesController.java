package com.academiq.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = {"https://rj979.github.io", "http://localhost:3000", "http://localhost:5173"})
public class NotesController {
    
    @GetMapping("/paper/{paperId}")
    public ResponseEntity<?> getNotesByPaper(@PathVariable Long paperId) {
        try {
            // Placeholder - return empty list for now
            return ResponseEntity.ok(List.of());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch notes: " + e.getMessage()));
        }
    }
    
    @PostMapping("/paper/{paperId}")
    public ResponseEntity<?> createNote(@PathVariable Long paperId, @RequestBody Map<String, Object> noteData) {
        try {
            // Placeholder - create note logic
            return ResponseEntity.ok(Map.of(
                "message", "Note created successfully",
                "id", System.currentTimeMillis() // Temporary ID
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to create note: " + e.getMessage()));
        }
    }
    
    @PatchMapping("/{noteId}")
    public ResponseEntity<?> updateNote(@PathVariable Long noteId, @RequestBody Map<String, Object> noteData) {
        try {
            // Placeholder - update note logic
            return ResponseEntity.ok(Map.of("message", "Note updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to update note: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{noteId}")
    public ResponseEntity<?> deleteNote(@PathVariable Long noteId) {
        try {
            // Placeholder - delete note logic
            return ResponseEntity.ok(Map.of("message", "Note deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to delete note: " + e.getMessage()));
        }
    }
}
