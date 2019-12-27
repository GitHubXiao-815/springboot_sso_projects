package com.example.service;

import com.example.domain.User;
import com.example.domain.UserExample;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User getUserByUsername(String  username);

    void addUser(User user);
}
