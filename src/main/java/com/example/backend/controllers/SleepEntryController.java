package com.example.backend.controllers;

// SleepEntryController.java
import com.example.backend.DTO.SleepEntryUpdateRequest;
import com.example.backend.models.SleepEntry;
import com.example.backend.services.SleepEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sleep")
@CrossOrigin(origins = "*")
public class SleepEntryController {

    @Autowired
    private SleepEntryService sleepEntryService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SleepEntry>> getEntriesByUser(@PathVariable Long userId) {
        List<SleepEntry> entries = sleepEntryService.getEntriesByUserId(userId);
        return ResponseEntity.ok(entries);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEntry(@RequestBody SleepEntry entry) {
        try {
            SleepEntry saved = sleepEntryService.addEntry(entry);
            return ResponseEntity.ok("Thêm bản ghi thành công!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi máy chủ: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTimes(@PathVariable Long id, @RequestBody SleepEntryUpdateRequest request) {
        SleepEntry updatedEntry = sleepEntryService.updateTimes(id, request);
        return ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSleepEntry(@PathVariable Long id) {
        sleepEntryService.deleteSleepEntry(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/week")
    public ResponseEntity<List<SleepEntry>> getSleepByWeek(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        List<SleepEntry> entries = sleepEntryService.getEntriesByUserIdAndWeek(userId, start, end);
        return ResponseEntity.ok(entries);
    }
}


