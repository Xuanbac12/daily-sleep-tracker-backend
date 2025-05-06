package com.example.backend.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public class SleepEntryUpdateRequest {
    @JsonFormat(pattern = "HH:mm")
    private LocalTime sleepTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime wakeTime;

    public LocalTime getWakeTime() {
        return wakeTime;
    }

    public void setWakeTime(LocalTime wakeTime) {
        this.wakeTime = wakeTime;
    }

    public LocalTime getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(LocalTime sleepTime) {
        this.sleepTime = sleepTime;
    }
}
