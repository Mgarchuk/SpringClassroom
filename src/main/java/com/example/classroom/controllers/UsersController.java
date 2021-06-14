package com.example.classroom.controllers;

import com.example.classroom.models.User;
import com.example.classroom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UsersController {

    private UserService userService;

    @GetMapping("users/{id}")
    public User getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @GetMapping("room/users/{roomId}")
    public List<User> getUsersByRoomId(@PathVariable UUID roomId) {
        return userService.getUsersByRoomId(roomId);
    }

    @PutMapping("users/add")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }



}
