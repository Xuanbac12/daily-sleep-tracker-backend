package com.example.backend.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    private boolean firstLogin = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SleepEntry> sleepEntries;

    // ✅ Constructor không tham số
    public User() {
    }

    // ✅ Constructor có tham số (nếu cần)
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // ✅ Getter và Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public List<SleepEntry> getSleepEntries() {
        return sleepEntries;
    }

    public void setSleepEntries(List<SleepEntry> sleepEntries) {
        this.sleepEntries = sleepEntries;
    }
}
