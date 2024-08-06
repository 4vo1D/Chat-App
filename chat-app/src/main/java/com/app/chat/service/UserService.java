package com.app.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.chat.model.User;
import com.app.chat.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User savUser(User user)
    {
        return userRepository.save(user);
    }
    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }
    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

}
