package com.example.backend.controllers;

import com.example.backend.models.SleepEntry;
import com.example.backend.services.SleepEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        sleepEntryService.addEntry(entry);
        return ResponseEntity.ok("Entry saved");
    }

}

