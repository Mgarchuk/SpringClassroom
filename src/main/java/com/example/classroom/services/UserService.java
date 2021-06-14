package com.example.classroom.services;

import com.example.classroom.models.User;
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

    public List<User> getUsersByRoomId(UUID roomId) {
        return null;
    }

    public void addUser(User user) {
        serviceHelper.redisson.getMap(usersKey).put(user.getUserId(), user);
    }

    @Autowired
    public void setServiceHelper(ServiceHelper serviceHelper) {
        this.serviceHelper = serviceHelper;
    }
}
