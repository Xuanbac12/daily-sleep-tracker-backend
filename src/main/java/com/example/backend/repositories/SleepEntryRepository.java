package com.example.backend.repositories;

import com.example.backend.models.SleepEntry;
import com.example.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SleepEntryRepository extends JpaRepository<SleepEntry, Long> {
    List<SleepEntry> findByUserAndDateAfter(User user, LocalDate fromDate);
    List<SleepEntry> findByUserId(Long userId);
    Optional<SleepEntry> findByUserIdAndDate(Long userId, LocalDate date);
    List<SleepEntry> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);



}
