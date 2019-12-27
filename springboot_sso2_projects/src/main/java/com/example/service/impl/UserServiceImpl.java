package com.example.service.impl;

import com.example.domain.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserByUsername(String username) {
        User userFromDB = userMapper.getUserByUsername(username);
        System.out.print("程序到达service-->");
        return userFromDB;
    }

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }
}
