package com.example.ezyroulettehttpserver.controller;

import com.example.ezyroulettehttpserver.encryption.EzySHA256;
import com.example.ezyroulettehttpserver.entity.GiftCard;
import com.example.ezyroulettehttpserver.entity.User;
import com.example.ezyroulettehttpserver.request.GiftCardRequest;
import com.example.ezyroulettehttpserver.request.UserRequest;
import com.example.ezyroulettehttpserver.service.GiftCardService;
import com.example.ezyroulettehttpserver.service.UserService;
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

    @Autowired
    GiftCardService giftCardService;

    @PostMapping("/accountLogin")
    @ResponseBody
    public String login(@RequestBody UserRequest request) {
        User user = userService.getUser(request.getUsername());
        if(user == null){
            return "Invalid Username!";
        }
        if(!user.getPassword().equals(encodePassword(request.getPassword()))){
            return "Password is incorrect!";
        }
        String loginToken = encodePassword(user.getUsername()+ LocalDateTime.now().toString());
        userService.updateUser(user.getUsername(),loginToken,0);
        System.out.println(loginToken);
        return loginToken;
    }

    @PostMapping("/accountRegistry")
    @ResponseBody
    public String registry(@RequestBody UserRequest request) {
        User user = userService.getUser(request.getUsername());
        if(user != null){
            return "Invalid Username!";
        }
        String loginToken = encodePassword(request.getUsername()+ LocalDateTime.now().toString());
        userService.createUser(request.getUsername(),request.getPassword(), loginToken);
        return loginToken;
    }

    @PostMapping("/addGiftCard")
    @ResponseBody
    public String addGiftCard(@RequestBody GiftCardRequest request) {
        User user = userService.getUser(request.getUsername());
        GiftCard giftCard = giftCardService.getGiftCard(request.getSerial());
        if(user == null){
            return "0";
        }
        if(giftCard == null){
            return "0";
        }
        if(!giftCard.getCode().equals(request.getCode())){
            return "0";
        }
        if(giftCard.isActivated()){
            return "0";
        }
        userService.updateUser(user.getUsername(),null,giftCard.getAmount());
        giftCard.setActivated(true);
        giftCardService.updateGiftCard(giftCard);
        return String.valueOf(giftCard.getAmount());
    }

    private String encodePassword(String password) {
        return EzySHA256.cryptUtfToLowercase(password);
    }
}
