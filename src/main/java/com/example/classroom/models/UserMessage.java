package com.example.classroom.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UserMessage {
    private UUID id;
    private UUID userId;
    private String username;
    private UserAction userAction;

    public UserMessage(UUID id, UUID userId, String username, UserAction action) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.userAction = action;
    }

    public UserMessage clone() {
        return new UserMessage(id, userId, username, userAction);
    }

    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    @JsonProperty("user_id")
    public UUID getUserId() {
        return userId;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("user_action")
    public UserAction getUserAction() {
        return userAction;
    }

    public void setUserId(UUID other) {
        this.userId = other;
    }

    public void setUsername(String other) {
        this.username = other;
    }

    public void setUserAction(UserAction userAction) {
        this.userAction = userAction;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
