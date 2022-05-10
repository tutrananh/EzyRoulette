package com.example.ezyroulettehttpserver.controller;

import com.example.ezyroulettehttpserver.entity.User;
import com.example.ezyroulettehttpserver.request.LoginRequest;
import com.example.ezyroulettehttpserver.service.UserService;
import com.tvd12.ezyfox.sercurity.EzySHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import java.time.LocalDateTime;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/accountLogin")
    @ResponseBody
    public String login(@RequestBody LoginRequest request) {
        User user = userService.getUser(request.getUsername());
        if(user == null){
            return "Invalid Username!";
        }
        if(!user.getPassword().equals(encodeString(request.getPassword()))){
            return "Password is incorrect!";
        }
        String loginToken = encodeString(user.getUsername()+ LocalDateTime.now().toString());
        userService.updateUser(user.getUsername(),loginToken,0);
        System.out.println(loginToken);
        return loginToken;
    }
    private String encodeString(String password) {
        return EzySHA256.cryptUtfToLowercase(password);
    }
}
