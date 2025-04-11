package com.example.backend.repositories;

import com.example.backend.models.SleepEntry;
import com.example.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SleepEntryRepository extends JpaRepository<SleepEntry, Long> {
    List<SleepEntry> findByUserAndDateAfter(User user, LocalDate fromDate);
    List<SleepEntry> findByUserId(Long userId);

}
