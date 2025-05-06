package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class SleepEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private LocalTime sleepTime;
    private LocalTime wakeTime;

    private double duration;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference

    private User user;

    // ✅ Constructor mặc định
    public SleepEntry() {
    }

    // ✅ Constructor có tham số
    public SleepEntry(LocalDate date, LocalTime sleepTime, LocalTime wakeTime, double duration, User user) {
        this.date = date;
        this.sleepTime = sleepTime;
        this.wakeTime = wakeTime;
        this.duration = duration;
        this.user = user;
    }

    // ✅ Tính thời lượng ngủ (có xử lý qua nửa đêm)
    public double calculateDuration() {
        if (sleepTime == null || wakeTime == null) return 0;

        long minutes = Duration.between(sleepTime, wakeTime).toMinutes();
        if (minutes < 0) minutes += 24 * 60;

        return Math.round((minutes / 60.0) * 10.0) / 10.0; // làm tròn 1 chữ số
    }

    // ✅ Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(LocalTime sleepTime) {
        this.sleepTime = sleepTime;
    }

    public LocalTime getWakeTime() {
        return wakeTime;
    }

    public void setWakeTime(LocalTime wakeTime) {
        this.wakeTime = wakeTime;
    }

    public double getDuration() {
        return duration;
    }

    public void setSleepDuration(double duration) {
        this.duration = duration;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
