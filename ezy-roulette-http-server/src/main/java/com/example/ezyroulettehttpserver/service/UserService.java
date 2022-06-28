package com.example.ezyroulettehttpserver.service;

import com.example.ezyroulettehttpserver.entity.User;
import org.springframework.stereotype.Service;


public interface UserService {
    User getUser(String username);
    User createUser(String username, String password, String loginToken);
    User updateUser(String username, String loginToken, float addedAmount);
    long countDocuments();
}
