package com.example.classroom.services;

import com.example.classroom.models.User;
import com.example.classroom.models.UserAction;
import com.example.classroom.models.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private ServiceHelper serviceHelper;
    private final String usersKey = "users";
    private final String userMessagesKey = "messages";

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

    public UserMessage createMessage(User user) {
        UserMessage message = new UserMessage(UUID.randomUUID(), user.getUserId(),
                user.getUsername(), UserAction.LOGIN);

        serviceHelper.redisson.getMap(userMessagesKey).put(message.getId(), message);
        return message;
    }

    public boolean addUser(User user) {
        if (serviceHelper.redisson.getMap(usersKey).containsKey(user.getUsername())) {
            return false;
        }

        user.setUserId(UUID.randomUUID());
        serviceHelper.redisson.getMap(usersKey).put(user.getUsername(), user);
        return true;
    }

    private void removeUser(User user) {

        serviceHelper.redisson.getMap(usersKey).remove(user.getUsername());
    }

    @Autowired
    public void setServiceHelper(ServiceHelper serviceHelper) {
        this.serviceHelper = serviceHelper;
    }
}
