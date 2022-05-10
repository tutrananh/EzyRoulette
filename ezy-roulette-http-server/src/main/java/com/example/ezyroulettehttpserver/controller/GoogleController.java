package com.example.ezyroulettehttpserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class GoogleController {

    private String googleLoginToken="";
    @GetMapping("/")
    @ResponseBody
    public String user(Principal principal) {
        System.out.println("username : " + principal.getName());
        googleLoginToken = principal.getName();
        return "Login successfully! Please return to game.";
    }

    @GetMapping("/getGoogleLoginToken")
    @ResponseBody
    public String getGoogleLoginToken() {
        String token = googleLoginToken;
        googleLoginToken ="";
        return token;
    }
}
