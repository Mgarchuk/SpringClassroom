package com.example.classroom.controllers;

import com.example.classroom.models.User;
import com.example.classroom.models.UserMessage;
import com.example.classroom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UsersController {

    private UserService userService;


    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @CrossOrigin
    @MessageMapping("/broadcast")
    public void processMessage(@Payload UserMessage userMessage) {

        UserMessage saved = userService.processUserMessage(userMessage);
        messagingTemplate.convertAndSend("queue/actions", saved);
    }

    @CrossOrigin
    @PutMapping("users/add")
    public ResponseEntity<?> addUser(@RequestBody String username) {
        User user = new User(UUID.randomUUID(), username);

        boolean added = userService.addUser(user);
        if (added) {
            UserMessage saved = userService.createMessage(user);
            messagingTemplate.convertAndSend("queue/actions", saved);
            return new ResponseEntity<>("{connectStatus: true}", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{connectStatus: false}", HttpStatus.BAD_REQUEST);
        }
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
