package com.example.classroom.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class User {
    private UUID userId;
    private String username;

    public User(UUID userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public User() {

    }

    @JsonProperty("user_id")
    public UUID getUserId() {
        return userId;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    public void setUserId(UUID other) {
        this.userId = other;
    }

    public void setUsername(String other) {
        this.username = other;
    }
}
