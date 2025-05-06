package com.example.backend.services;

// SleepEntryService.java
import com.example.backend.DTO.SleepEntryUpdateRequest;
import com.example.backend.models.SleepEntry;
import com.example.backend.repositories.SleepEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SleepEntryService {

    @Autowired
    private SleepEntryRepository sleepEntryRepository;

    public SleepEntry addEntry(SleepEntry entry) {
        LocalDate date = entry.getDate();
        Long userId = entry.getUser().getId();

        // ✅ Kiểm tra ngày null
        if (date == null) {
            throw new IllegalArgumentException("Ngày không được để trống.");
        }

        // ✅ Kiểm tra ngày trong tương lai
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Không được chọn ngày trong tương lai.");
        }

        // ✅ Kiểm tra trùng ngày
        Optional<SleepEntry> existing = sleepEntryRepository.findByUserIdAndDate(userId, date);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Bạn đã có bản ghi cho ngày này rồi.");
        }

        return sleepEntryRepository.save(entry);
    }

    public List<SleepEntry> getEntriesByUserId(Long userId) {
        return sleepEntryRepository.findByUserId(userId);
    }

    public SleepEntry updateTimes(Long id, SleepEntryUpdateRequest request) {
        SleepEntry entry = sleepEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản ghi"));

        entry.setSleepTime(request.getSleepTime());
        entry.setWakeTime(request.getWakeTime());
        entry.setSleepDuration(entry.calculateDuration()); // nếu có

        return sleepEntryRepository.save(entry);
    }

    public void deleteSleepEntry(Long id) {
        if (!sleepEntryRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy bản ghi để xóa");
        }
        sleepEntryRepository.deleteById(id);
    }

    public List<SleepEntry> getEntriesByUserIdAndWeek(Long userId, LocalDate start, LocalDate end) {
        return sleepEntryRepository.findByUserIdAndDateBetween(userId, start, end);
    }

}

