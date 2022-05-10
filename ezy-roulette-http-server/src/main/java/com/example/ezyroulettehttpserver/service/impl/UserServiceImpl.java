package com.example.ezyroulettehttpserver.service.impl;

import com.example.ezyroulettehttpserver.entity.User;
import com.example.ezyroulettehttpserver.repo.UserRepo;
import com.example.ezyroulettehttpserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public User getUser(String username) {
        return userRepo.findUserByUsername(username);
    }

    @Override
    public User updateUser(String username, String loginToken, float addedAmount) {
        User user = userRepo.findUserByUsername(username);
        if(user==null) return null;
        user.setLoginToken(loginToken);
        user.setBalance(user.getBalance()+addedAmount);
        userRepo.save(user);
        return user;
    }

    @Override
    public long countDocuments() {
        return userRepo.count();
    }


}
