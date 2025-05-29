package com.urlmanager.model;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdAt;
    private int maxPopularUrls;

    public User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.maxPopularUrls = 10;
    }

    // Getters and Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getMaxPopularUrls() {
        return maxPopularUrls;
    }

    public void setMaxPopularUrls(int maxPopularUrls) {
        this.maxPopularUrls = maxPopularUrls;
    }
} 