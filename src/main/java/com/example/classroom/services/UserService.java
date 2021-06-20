package com.example.classroom.services;

import com.example.classroom.models.User;
import com.example.classroom.models.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private ServiceHelper serviceHelper;
    private final String usersKey = "users";

    public User getUserById(UUID id) {
        return (User)serviceHelper.redisson.getMap(usersKey).get(id);
    }

    public UserMessage processUserMessage(UserMessage userMessage) {
        switch (userMessage.getUserAction()) {
            case LOGIN:
                addUser(new User(userMessage.getUserId(), userMessage.getUsername()));
                break;
            case LOGOUT:
                removeUser(new User(userMessage.getUserId(), userMessage.getUsername()));
                break;
            default:
                break;
        }

        
        return userMessage.clone();
    }

    private void addUser(User user) {

        serviceHelper.redisson.getMap(usersKey).put(user.getUserId(), user);
    }

    private void removeUser(User user) {

        serviceHelper.redisson.getMap(usersKey).remove(user.getUserId());
    }

    @Autowired
    public void setServiceHelper(ServiceHelper serviceHelper) {
        this.serviceHelper = serviceHelper;
    }
}
