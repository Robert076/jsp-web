package com.urlmanager.model;

import java.time.LocalDateTime;

public class Url {
    private Long id;
    private Long userId;
    private String url;
    private String title;
    private String description;
    private int visitCount;
    private LocalDateTime createdAt;

    public Url() {}

    public Url(Long userId, String url, String title, String description) {
        this.userId = userId;
        this.url = url;
        this.title = title;
        this.description = description;
        this.visitCount = 0;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
} 