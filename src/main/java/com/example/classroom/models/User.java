package com.example.classroom.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class User {
    private UUID userId;
    private String username;
    private UUID roomId;

    @JsonProperty("user_id")
    public UUID getUserId() {
        return userId;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("room_id")
    public UUID getRoomId() {
        return roomId;
    }

    public void setUserId(UUID other) {
        this.userId = other;
    }

    public void setUsername(String other) {
        this.username = other;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }
}
