package com.example.classroom.controllers;

import com.example.classroom.models.User;
import com.example.classroom.models.UserMessage;
import com.example.classroom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    private UserService userService;


    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/room")
    public void processMessage(@Payload UserMessage userMessage) {

        UserMessage saved = userService.processUserMessage(userMessage);
        messagingTemplate.convertAndSend("queue/actions", saved);
    }

    @PutMapping("users/add")
    public void addUser(@RequestBody User user) {
        boolean added = userService.addUser(user);
        if (added) {
            UserMessage saved = userService.createMessage(user);
            messagingTemplate.convertAndSend("queue/actions", saved);
        }
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
