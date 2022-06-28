package com.example.ezyroulettehttpserver.controller;

import com.example.ezyroulettehttpserver.encryption.EzySHA256;
import com.example.ezyroulettehttpserver.entity.User;
import com.example.ezyroulettehttpserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class GoogleController {

    @Autowired
    UserService userService;

    private String loginToken="";

    @GetMapping("/")
    @ResponseBody
    public String user(Principal principal) {
        System.out.println("username : " + principal.getName());
        String googleLoginToken = principal.getName();
        String username = "user" + googleLoginToken.substring(0,6);
        User user = userService.getUser(username);
        loginToken = googleLoginToken;
        if(user == null){
            userService.createUser(username,loginToken, loginToken);
        }
        else{
            userService.updateUser(user.getUsername(),loginToken,0);
        }

        return "Login successfully! Please return to game.";
    }

    @GetMapping("/getGoogleLoginToken")
    @ResponseBody
    public String getGoogleLoginToken() {
        String token = loginToken;
        loginToken ="";
        return token;
    }

    private String encodePassword(String password) {
        return EzySHA256.cryptUtfToLowercase(password);
    }
}
