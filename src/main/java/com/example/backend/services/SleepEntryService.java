package com.example.backend.services;

import com.example.backend.models.SleepEntry;
import com.example.backend.repositories.SleepEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SleepEntryService {

    @Autowired
    private SleepEntryRepository sleepEntryRepository;

    public SleepEntry addEntry(SleepEntry entry) {
        return sleepEntryRepository.save(entry);
    }

    public List<SleepEntry> getEntriesByUserId(Long userId) {
        return sleepEntryRepository.findByUserId(userId);
    }
}
